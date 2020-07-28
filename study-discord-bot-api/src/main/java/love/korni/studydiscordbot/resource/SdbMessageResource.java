package love.korni.studydiscordbot.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import love.korni.studydiscordbot.dto.MessageDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/sbd/message", produces = MediaType.APPLICATION_JSON_VALUE)
public interface SdbMessageResource {

    @ApiOperation("Получить последнее сообщение из канала")
    @GetMapping("/{channelId}/last")
    MessageDto getLastMessage(@ApiParam("Id канала") @PathVariable String channelId);

}
