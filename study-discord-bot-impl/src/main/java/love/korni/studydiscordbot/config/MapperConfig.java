package love.korni.studydiscordbot.config;

import love.korni.studydiscordbot.dto.ChannelDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(GuildChannel.class, ChannelDto.class)
                .customize(new ChannelMapper())
                .register();
    }

    private static class ChannelMapper extends CustomMapper<GuildChannel, ChannelDto> {
        @Override
        public void mapAtoB(GuildChannel guildChannel, ChannelDto channelDto, MappingContext context) {
            channelDto.setChannelId(guildChannel.getId());
            channelDto.setName(guildChannel.getName());
            channelDto.setIsText(guildChannel.getType().equals(ChannelType.TEXT));
        }
    }

}
