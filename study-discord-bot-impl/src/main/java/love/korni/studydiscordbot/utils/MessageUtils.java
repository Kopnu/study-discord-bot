package love.korni.studydiscordbot.utils;

import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;

@UtilityClass
public class MessageUtils {
    public String getClearMessageContent(Message message) {
        JDA jda = message.getJDA();
        String nickname = message.getGuild().getMember(jda.getSelfUser()).getNickname();
        nickname = Objects.nonNull(nickname) ? nickname : jda.getSelfUser().getName();
        String s = StringUtils.removeStart(message.getContentStripped(), "@" + nickname);
        return StringUtils.strip(s);
    }
}
