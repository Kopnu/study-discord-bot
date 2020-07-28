package love.korni.studydiscordbot.listeners;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Slf4j
@Component
public class ReadyListener extends ListenerAdapter {

    private static final String CATEGORY_NAME = "test_category";

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        log.info("Готов к работе с {} сервером(-ами)", event.getGuildAvailableCount());
        log.debug("Подключенные сервера: {}", event.getJDA().getGuilds());
    }
}
