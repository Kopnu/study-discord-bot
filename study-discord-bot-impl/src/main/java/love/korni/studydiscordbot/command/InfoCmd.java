package love.korni.studydiscordbot.command;

import love.korni.studydiscordbot.command.util.CmdHandler;
import love.korni.studydiscordbot.command.util.Embed;
import love.korni.studydiscordbot.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Set;

@Component
public class InfoCmd implements CmdHandler {

    public static final Set<String> INFO_CMD = Set.of("info");
    public static final String DESCRIPTION = "выдать сообщние от имени бота в чат, в котором вызвана команда. ```@Bot_Dev info Просто текст.```";

    @Override
    public String getDesc() {
        return DESCRIPTION;
    }

    @Override
    public Set<String> getCommandAliases() {
        return INFO_CMD;
    }

    @Override
    public void handle(Message message) {
        String clearMessageContent = MessageUtils.getClearMessageContent(message);
        clearMessageContent = StringUtils.removeStart(clearMessageContent, "info");
        MessageAction messageAction = message.getChannel().sendMessage(new InfoEmbed(message, clearMessageContent).get());
        messageAction.queue();
    }

    private static class InfoEmbed extends Embed {

        private final String desc;

        public InfoEmbed(Message message, String desc) {
            super(message);
            this.desc = desc;
        }

        @Override
        protected Color getColor() {
            return new Color(7039851);
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getDescription() {
            return desc;
        }

        @Override
        protected String getThumbnail() {
            return null;
        }

        @Override
        protected String getFooterText() {
            return null;
        }

        @Override
        protected String getFooterIconURL() {
            return null;
        }
    }
}
