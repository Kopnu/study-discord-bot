package love.korni.studydiscordbot.resource.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.config.converter.Converter;
import love.korni.studydiscordbot.config.converter.MessageConverter;
import love.korni.studydiscordbot.dto.ChannelDto;
import love.korni.studydiscordbot.dto.MessageDto;
import love.korni.studydiscordbot.resource.SbdChannelResource;
import love.korni.studydiscordbot.service.ChannelService;
import ma.glasnost.orika.MapperFacade;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SbdChannelResourceImpl implements SbdChannelResource {

    private final MapperFacade mapperFacade;
    private final ChannelService channelService;
    private final MessageConverter messageConverter;

    @Override
    public List<ChannelDto> getTextChannels(String guildId) {
        log.info("getTextChannels() - start, guildId = {}", guildId);
        List<ChannelDto> channelDtos = mapperFacade.mapAsList(channelService.getTextChannels(guildId), ChannelDto.class);
        log.debug("getTextChannels() - end, channelDtoList = {}", channelDtos);
        return channelDtos;
    }

    @Override
    public void sendMessageToChannel(String channelId, MessageDto messageDto) {
        log.info("sendMessageToChannel() - start, channelId = {}, messageDto = {}", channelId, messageDto);
        Message message = messageConverter.convertFrom(messageDto);
        channelService.sendMessageToChannel(channelId, message);
        log.debug("sendMessageToChannel() - end");
    }
}
