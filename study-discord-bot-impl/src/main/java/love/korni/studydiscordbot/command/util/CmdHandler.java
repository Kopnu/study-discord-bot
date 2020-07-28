package love.korni.studydiscordbot.command.util;

import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public interface CmdHandler {

    String getDesc();

    Set<String> getCommandAliases();

    default boolean shouldHandle(String message) {
        Set<String> commandAliases = getCommandAliases();
        if (Objects.isNull(commandAliases)) {
            return false;
        }
        String[] words = StringUtils.split(message);
        return Arrays.stream(words).anyMatch(commandAliases::contains);
    }

    void handle(Message message);

}
