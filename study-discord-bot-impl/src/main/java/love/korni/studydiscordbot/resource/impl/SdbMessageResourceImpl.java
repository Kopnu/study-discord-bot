package love.korni.studydiscordbot.resource.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.dto.MessageDto;
import love.korni.studydiscordbot.resource.SdbMessageResource;
import ma.glasnost.orika.MapperFacade;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageHistory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SdbMessageResourceImpl implements SdbMessageResource {

    private final JDA jda;
    private final MapperFacade mapperFacade;

    @Override
    public MessageDto getLastMessage(String channelId) {
        log.debug("getLastMessage() - start, channelId = {}", channelId);
        GuildChannel channel = jda.getGuildChannelById(channelId);
        if (Objects.nonNull(channel) && channel.getType().equals(ChannelType.TEXT)) {
            MessageChannel messageChannel = (MessageChannel) channel;
            MessageHistory complete = messageChannel.getHistoryFromBeginning(1).complete();
            List<Message> retrievedHistory = complete.getRetrievedHistory();
            if (retrievedHistory.size() > 0) {
                Message message = retrievedHistory.get(0);
                MessageDto messageDto = mapperFacade.map(message, MessageDto.class);
                log.debug("getLastMessage() - end, message = {}", messageDto);
                return messageDto;
            }
        }
        log.debug("getLastMessage() - end");
        return null;
    }

}
