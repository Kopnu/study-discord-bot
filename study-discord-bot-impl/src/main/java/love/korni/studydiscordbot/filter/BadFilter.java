package love.korni.studydiscordbot.filter;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BadFilter implements Filter {

    private static final List<String> BAD_WORDS = List.of("Говнокод");

    @Override
    public void handle(Message message) {
        String contentStripped = message.getContentStripped();
        boolean contains = contentStripped.contains(BAD_WORDS.get(0));
        if (contains) {
        }
    }
}
