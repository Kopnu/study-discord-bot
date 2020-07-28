package love.korni.studydiscordbot.config;

import love.korni.studydiscordbot.dto.ChannelDto;
import love.korni.studydiscordbot.dto.MessageDto;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.metadata.Type;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.internal.entities.ReceivedMessage;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(GuildChannel.class, ChannelDto.class)
                .customize(new ChannelMapper())
                .register();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new MessageMapper());
    }

    private static class ChannelMapper extends CustomMapper<GuildChannel, ChannelDto> {
        @Override
        public void mapAtoB(GuildChannel guildChannel, ChannelDto channelDto, MappingContext context) {
            channelDto.setChannelId(guildChannel.getId());
            channelDto.setName(guildChannel.getName());
            channelDto.setIsText(guildChannel.getType().equals(ChannelType.TEXT));
        }
    }

    private static class MessageMapper extends BidirectionalConverter<Message, MessageDto> {

        @Override
        public boolean canConvert(Type<?> sourceType, Type<?> destinationType) {
            return sourceType.isAssignableFrom(ReceivedMessage.class) || destinationType.isAssignableFrom(ReceivedMessage.class);
        }

        @Override
        public MessageDto convertTo(Message message, Type<MessageDto> type, MappingContext mappingContext) {
            MessageDto messageDto = new MessageDto()
                    .setMessageId(message.getId())
                    .setContent(message.getContentRaw());
            return messageDto;
        }

        @Override
        public Message convertFrom(MessageDto messageDto, Type<Message> type, MappingContext mappingContext) {
            Message message = new MessageBuilder()
                    .setContent(messageDto.getContent())
                    .build();
            return message;
        }
    }

}
