package love.korni.studydiscordbot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChannelDto {

    @ApiModelProperty(value = "Id канала", example = "731154486176907289")
    private String channelId;

    @ApiModelProperty(value = "Наименование канала", example = "Test channel")
    private String name;

    @ApiModelProperty(value = "Признак текстового канала", example = "true")
    private Boolean isText;

}
