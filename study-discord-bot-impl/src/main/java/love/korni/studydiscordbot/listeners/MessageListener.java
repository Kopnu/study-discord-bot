package love.korni.studydiscordbot.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.command.util.CmdHandler;
import love.korni.studydiscordbot.filter.Filter;
import love.korni.studydiscordbot.utils.MessageUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener extends ListenerAdapter {

    private final List<CmdHandler> cmdHandlers;
    private final List<Filter> filters;

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!event.getChannel().getName().equals("dev-s") || event.getAuthor().isBot()) {
            return;
        }

        Message message = event.getMessage();
        JDA jda = message.getJDA();
        log.debug("onMessageReceived() - {}", message);

        filters.forEach(filter -> filter.handle(message));

        if (message.isMentioned(jda.getSelfUser(), Message.MentionType.USER)) {
            String messageContent = MessageUtils.getClearMessageContent(message);
            log.trace("onMessageReceived() - messageContent = {}", messageContent);
            cmdHandlers.forEach(handler -> {
                if (handler.shouldHandle(messageContent)) {
                    handler.handle(message);
                }
            });
        }
    }
}
