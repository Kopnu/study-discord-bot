package love.korni.studydiscordbot.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import love.korni.studydiscordbot.dto.ChannelDto;
import love.korni.studydiscordbot.dto.MessageDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/sbd/channel", produces = MediaType.APPLICATION_JSON_VALUE)
public interface SbdChannelResource {

    @ApiOperation("Получить список текстовых каналов на сервере")
    @GetMapping("/text/{guildId}")
    List<ChannelDto> getTextChannels(@ApiParam("Id сервера") @PathVariable String guildId);

    @ApiOperation("Отправить сообщение в определенный канал")
    @PostMapping("/text/send/{channelId}")
    void sendMessageToChannel(@ApiParam("Id канала") @PathVariable String channelId,
                              @ApiParam("Сообщение") @RequestBody MessageDto messageDto);

}
