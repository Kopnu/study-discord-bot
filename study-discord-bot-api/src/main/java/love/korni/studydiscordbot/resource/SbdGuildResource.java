package love.korni.studydiscordbot.resource;

import io.swagger.annotations.ApiOperation;
import love.korni.studydiscordbot.dto.GuildDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/sbd/guild", produces = MediaType.APPLICATION_JSON_VALUE)
public interface SbdGuildResource {

    @ApiOperation("Получить список серверов в дискорде, к которым подключен бот")
    @GetMapping
    List<GuildDto> getGuilds();

}
