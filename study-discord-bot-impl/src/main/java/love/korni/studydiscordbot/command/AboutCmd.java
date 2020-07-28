package love.korni.studydiscordbot.command;

import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.command.util.CmdHandler;
import love.korni.studydiscordbot.command.util.Embed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.util.Set;

@Slf4j
@Component
public class AboutCmd implements CmdHandler {

    public static final Set<String> ABOUT_CMD = Set.of("about");
    public static final String DESCRIPTION = "выводит информацию о боте.";

    @Override
    public String getDesc() {
        return DESCRIPTION;
    }

    @Override
    public Set<String> getCommandAliases() {
        return ABOUT_CMD;
    }

    @Override
    public void handle(Message message) {
        MessageAction messageAction = message.getChannel().sendMessage(new AboutEmbed(message).get());
        messageAction.queue();
    }

    public static class AboutEmbed extends Embed {

        public AboutEmbed(Message message) {
            super(message);
        }

        @Override
        protected Color getColor() {
            return new Color(7039851);
        }

        @Override
        protected String getTitle() {
            return "Информационная панель";
        }

        @Override
        protected String getDescription() {
            return "Бот " + selfUser.getName() + " служит дискорд-серверу: " + message.getGuild().getName() + ".\n" +
                    "Необходим для поддержания правопорядка на сервере.\n\n" +
                    "Версия: 0.0.1\n" +
                    "Бот еще в разработке и может содержать тучу багов. Поэтому прошу прощения :yum:";
        }

        @Override
        protected String getThumbnail() {
            return selfUser.getAvatarUrl();
        }

        @Override
        protected String getFooterText() {
            return "Автор бота: " + author.getName() + "#" + author.getDiscriminator();
        }

        @Override
        protected String getFooterIconURL() {
            return author.getAvatarUrl();
        }
    }
}
