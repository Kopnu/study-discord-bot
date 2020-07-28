package love.korni.studydiscordbot.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.command.util.CmdHandler;
import love.korni.studydiscordbot.command.util.Embed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelpCmd implements CmdHandler {

    public static final Set<String> HELP_CMD = Set.of("help");
    public static final String DESCRIPTION = "список команд.";
    public final List<CmdHandler> cmdHandlers;

    @Override
    public String getDesc() {
        return DESCRIPTION;
    }

    @Override
    public Set<String> getCommandAliases() {
        return HELP_CMD;
    }

    @Override
    public void handle(Message message) {
        log.debug("handle() - message = {}", message);
        MessageAction messageAction = message.getChannel().sendMessage(new HelpEmbed(message).get());
        messageAction.queue();
    }

    public class HelpEmbed extends Embed {

        public HelpEmbed(Message message) {
            super(message);
        }

        @Override
        protected Color getColor() {
            return new Color(7039851);
        }

        @Override
        protected String getTitle() {
            return "Список команд:";
        }

        @Override
        protected String getThumbnail() {
            return null;
        }

        /*
        "**help** - список команд.\n" +
        "**about** - выводит информацию о боте.\n" +
        "**setting {cmd}** - команда для настройки бота. Введите help setting, чтобы узнать подробнее.\n" +
        "**info <текст>** - выдать сообщние от имени бота в чат, в котором вызвана команда. ```@Vi info Просто текст.```\n" +
        "**warn <@user> [reason]** - выдать предупреждение пользователю. Можно указать причину.```@Vi warn @user Просто причина.```\n" +
        "**mute list** - список пользователей, находящихся в муте. ```@Vi mute list```\n" +
        "**mute <modification> <@user> [reason]** - дать мут чата <@user> на <modification> времени. Modification составляет собой пару числа и постфикса(s - секунды; m - минуты; h - часы; d - дни). __Команда не работает, если не задать **muterole**.__ Можно указать причину.```@Vi mute 5m @user\n@Vi mute 120h @user\n@Vi mute 120h @user Просто причина!```\n" +
        "**unmute <@user>** - снять мут чата <@user>. ```@Vi unmute @user```\n" +
        "**kick <@user> [reason]** - кик <@user> с сервера. Можно указать причину. ```@Vi kick @user\n@Vi kick @user Просто причина, ничего более.```\n" +
        "**ban <@user> [reason]** - бан <@user> на сервере. Можно указать причину. ```@Vi ban @user\n@Vi ban @user Просто причина, ничего более.```\n\n" +
        "__Внимание__, если вы ввели команду, а она не сработала, то скорее всего, команда ошибочна. Если считаете, что это баг, то пишите автору бота. Собственно, обо всех багах, пишите автору бота.";
        */
        @Override
        protected String getDescription() {
            StringBuilder sb = new StringBuilder();

            getCommandAliases().forEach(cmd -> sb.append("**").append(cmd).append("** "));
            sb.append(" - ").append(getDesc()).append("\n");

            cmdHandlers.forEach(cmdHandler -> {
                cmdHandler.getCommandAliases().forEach(cmd -> sb.append("**").append(cmd).append("** "));
                sb.append(" - ").append(cmdHandler.getDesc()).append("\n");
            });

            return sb.toString();
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
