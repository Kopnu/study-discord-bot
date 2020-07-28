package love.korni.studydiscordbot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GuildDto {

    @ApiModelProperty(value = "Id сервера", example = "731154486176907289")
    private String guildId;

}
