package love.korni.studydiscordbot.service.impl;

import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.exception.UnsupportedTypeOfChannelException;
import love.korni.studydiscordbot.service.ChannelService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Channel service implementation.
 */
@Slf4j
@Service
public class ChannelServiceImpl implements ChannelService {

    private final JDA jda;

    public ChannelServiceImpl(@Lazy JDA jda) {
        this.jda = jda;
    }

    @Override
    public GuildChannel createChannel(Guild guild, String name, ChannelType channelType) throws UnsupportedTypeOfChannelException {
        return createChannel(guild, null, name, channelType);
    }

    @Override
    public GuildChannel createChannel(Category category, String name, ChannelType channelType) throws UnsupportedTypeOfChannelException {
        return createChannel(category.getGuild(), category, name, channelType);
    }

    private GuildChannel createChannel(Guild guild, Category category, String name, ChannelType channelType) throws UnsupportedTypeOfChannelException {
        if (channelType.equals(ChannelType.TEXT)) {
            ChannelAction<VoiceChannel> textChannel;
            if (Objects.isNull(category)) {
                textChannel = guild.createVoiceChannel(name);
            } else {
                textChannel = category.createVoiceChannel(name);
            }
            return textChannel.complete();
        } else if (channelType.equals(ChannelType.VOICE)) {
            ChannelAction<VoiceChannel> voiceChannel;
            if (Objects.isNull(category)) {
                voiceChannel = guild.createVoiceChannel(name);
            } else {
                voiceChannel = category.createVoiceChannel(name);
            }
            return voiceChannel.complete();
        } else {
            throw new UnsupportedTypeOfChannelException(String.format("Error while creating a channel. Unsupported type - %s", channelType));
        }
    }

    @Override
    public void deleteChannel(Guild guild, Long id) {
        GuildChannel guildChannelById = guild.getGuildChannelById(id);
        if (Objects.nonNull(guildChannelById)) {
            guildChannelById.delete().complete();
        }
    }

    @Override
    public List<GuildChannel> getTextChannels(String guildId) {
        Guild guildById = jda.getGuildById(guildId);
        List<GuildChannel> channels = List.of();
        if (Objects.nonNull(guildById)) {
            channels = guildById.getChannels().stream()
                    .filter(chan -> chan.getType().equals(ChannelType.TEXT))
                    .collect(Collectors.toList());
        }
        return channels;
    }

    @Override
    public void sendMessageToChannel(String channelId, Message message) {
        GuildChannel channel = jda.getGuildChannelById(channelId);
        if (Objects.nonNull(channel) && channel.getType().equals(ChannelType.TEXT)) {
            MessageChannel messageChannel = (MessageChannel) channel;
            MessageAction messageAction = messageChannel.sendMessage(message);
            messageAction.queue();
            log.trace("sendMessageToChannel() - message {} sent", message);
        }
    }
}
