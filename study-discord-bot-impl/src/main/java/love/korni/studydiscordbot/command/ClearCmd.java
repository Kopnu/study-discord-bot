package love.korni.studydiscordbot.command;

import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.command.util.CmdHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class ClearCmd implements CmdHandler {

    public static final Set<String> CLEAR_CMD = Set.of("clear");
    public static final String DESCRIPTION = "полностью чистит канал от сообщений.";

    @Override
    public String getDesc() {
        return DESCRIPTION;
    }

    @Override
    public Set<String> getCommandAliases() {
        return CLEAR_CMD;
    }

    @Override
    public void handle(Message message) {
        message.getChannel().getIterableHistory().forEach(message1 -> {
            AuditableRestAction<Void> delete = message1.delete();
            delete.complete();
        });
    }
}
