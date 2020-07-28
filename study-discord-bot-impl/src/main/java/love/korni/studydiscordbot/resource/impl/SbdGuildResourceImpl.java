package love.korni.studydiscordbot.resource.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.dto.GuildDto;
import love.korni.studydiscordbot.resource.SbdGuildResource;
import net.dv8tion.jda.api.JDA;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SbdGuildResourceImpl implements SbdGuildResource {

    private final JDA jda;

    @Override
    public List<GuildDto> getGuilds() {
        log.debug("getGuilds() - start");
        List<GuildDto> guilds = jda.getGuilds()
                .stream()
                .map(guild -> new GuildDto().setGuildId(guild.getId()))
                .collect(Collectors.toList());
        log.debug("getGuilds() - end, guilds = {}", guilds);
        return guilds;
    }
}
