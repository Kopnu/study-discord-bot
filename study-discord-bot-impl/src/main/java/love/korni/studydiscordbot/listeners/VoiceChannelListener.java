package love.korni.studydiscordbot.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.service.CategoryService;
import love.korni.studydiscordbot.service.ChannelService;
import love.korni.studydiscordbot.utils.DateUtils;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoiceChannelListener extends ListenerAdapter {

    private static final Map<VoiceChannel, ScheduledFuture<?>> VOICE_CHANNELS_TO_DELETE = new HashMap<>();

    private final ChannelService channelService;
    private final CategoryService categoryService;
    private final TaskScheduler taskScheduler;

    @Value("${server.config.category}")
    private String categoryName;

    @Value("${server.config.channel}")
    private String channelName;

    @Value("${server.config.channelLivenessSec}")
    private Long channelLivenessSec;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        log.debug("onReady() - start");
        event.getJDA().getGuilds().forEach(guild -> {
            boolean result = guild.getCategories().stream().anyMatch(category -> category.getName().equals(categoryName));
            Category category;
            if (result) {
                log.trace("onReady() - категория найдена");
                category = guild.getCategoriesByName(categoryName, true).get(0);
                List<VoiceChannel> voiceChannels = category.getVoiceChannels();
                if (voiceChannels.size() == 0) {
                    GuildChannel channel = channelService.createChannel(category, channelName + " 1", ChannelType.VOICE);
                    log.trace("onReady() - создание канала в категории = {}", channel.getId());
                }
            } else {
                category = categoryService.createCategory(guild, categoryName);
                GuildChannel channel = channelService.createChannel(category, channelName + " 1", ChannelType.VOICE);
                log.trace("onReady() - создание категории = {} и канала в категории = {}", category.getId(), channel.getId());
            }
        });
        log.debug("onReady() - end");
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        log.debug("onGuildVoiceJoin() - start");
        VoiceChannel channelJoined = event.getChannelJoined();
        userChannelJoinOrMove(channelJoined);
        log.debug("onGuildVoiceJoin() - end");
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        log.debug("onGuildVoiceLeave() - start");
        VoiceChannel channelLeft = event.getChannelLeft();
        userChannelLeftOrMove(channelLeft);
        log.debug("onGuildVoiceLeave() - end");
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        log.debug("onGuildVoiceMove() - start");
        userChannelLeftOrMove(event.getChannelLeft());
        userChannelJoinOrMove(event.getChannelJoined());
        log.debug("onGuildVoiceMove() - end");
    }

    private void userChannelJoinOrMove(VoiceChannel channelJoined) {
        Category category = channelJoined.getParent();
        if (category.getName().equals(categoryName)) {
            log.trace("userChannelJoinOrMove() - канал {} принадлежит нашей категории", channelJoined);
            // Убрать из списка "На удаление" подключенный канал т.к. мы к нему подключились
            if (VOICE_CHANNELS_TO_DELETE.containsKey(channelJoined)) {
                log.trace("userChannelJoinOrMove() - убираем из списка \"На удаление\" канал = {}", channelJoined);
                VOICE_CHANNELS_TO_DELETE.get(channelJoined).cancel(true);
                VOICE_CHANNELS_TO_DELETE.remove(channelJoined);
            }
            // Проверить наличие пустого канала
            List<VoiceChannel> voiceChannels = category.getVoiceChannels();
            boolean hasEmptyChannel = voiceChannels.stream()
                    .anyMatch(channel -> channel.getMembers().size() == 0);
            // Если канала нет, то создать новый
            if (!hasEmptyChannel) {
                String name = constructChannelName(channelName, voiceChannels.size(), voiceChannels);
                GuildChannel channel = channelService.createChannel(category, name, ChannelType.VOICE);
                log.trace("userChannelJoinOrMove() - создание нового канала = {}", channel);
            }
        }
    }

    private void userChannelLeftOrMove(VoiceChannel channelLeft) {
        Category parent = channelLeft.getParent();
        if (parent.getName().equals(categoryName)) {
            log.trace("userChannelLeftOrMove() - канал {} принадлежит нашей категории", channelLeft);
            // Проверить, что мы были последние в канале
            if (channelLeft.getMembers().size() == 0) {
                log.trace("userChannelLeftOrMove() - юзер вышел последним");
                // Проверить, что это не последний канал
                List<VoiceChannel> emptyChannels = parent.getVoiceChannels().stream()
                        .filter(channel -> channel.getMembers().size() == 0)
                        .collect(Collectors.toList());
                if (parent.getVoiceChannels().size() != 1 && emptyChannels.size() > 1) {
                    log.trace("userChannelLeftOrMove() - канал {} не последний", channelLeft);
                    // Добавить канал в список "На удаление"
                    Date dateToDelete = DateUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(channelLivenessSec));
                    Runnable runnable = () -> {
                        try {
                            channelService.deleteChannel(channelLeft.getGuild(), channelLeft.getIdLong());
                            log.trace("userChannelLeftOrMove() - канал {} удалён", channelLeft);
                        } catch (Exception e) {
                            log.error("userChannelLeftOrMove() - каналы \"На удаление\" = {}", VOICE_CHANNELS_TO_DELETE.keySet());
                            log.error("Попытка удалить канал, которого нет - {}", e.getMessage());
                        } finally {
                            VOICE_CHANNELS_TO_DELETE.remove(channelLeft);
                        }
                    };
                    ScheduledFuture<?> schedule = taskScheduler.schedule(runnable, dateToDelete);
                    VOICE_CHANNELS_TO_DELETE.put(channelLeft, schedule);
                    log.trace("userChannelLeftOrMove() - каналы \"На удаление\" = {}", VOICE_CHANNELS_TO_DELETE.keySet());
                }
            }
        }
    }

    private String constructChannelName(String name, Integer size, List<VoiceChannel> voiceChannels) {
        int localSize = size;
        boolean result = voiceChannels.stream()
                .anyMatch(channel -> channel.getName().equals(name + " " + localSize));
        return result ? constructChannelName(name, ++size, voiceChannels) : name + " " + size;
    }
}
