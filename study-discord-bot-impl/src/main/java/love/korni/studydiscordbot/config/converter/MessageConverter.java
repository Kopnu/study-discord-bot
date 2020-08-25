package love.korni.studydiscordbot.config.converter;

import love.korni.studydiscordbot.dto.MessageDto;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter implements Converter<Message, MessageDto> {
    @Override
    public MessageDto convertTo(Message message) {
        return new MessageDto()
                .setMessageId(message.getId())
                .setContent(message.getContentRaw());
    }

    @Override
    public Message convertFrom(MessageDto messageDto) {
        return new MessageBuilder()
                .setContent(messageDto.getContent())
                .build();
    }
}
