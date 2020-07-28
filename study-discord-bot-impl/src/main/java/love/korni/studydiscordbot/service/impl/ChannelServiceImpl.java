package love.korni.studydiscordbot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.dto.ChannelDto;
import love.korni.studydiscordbot.service.ChannelService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final JDA jda;

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
