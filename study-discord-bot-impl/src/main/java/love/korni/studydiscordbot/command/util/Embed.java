package love.korni.studydiscordbot.command.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public abstract class Embed {

    protected Message message;
    protected SelfUser selfUser;
    protected User author;

    public Embed(Message message) {
        this.message = message;
        this.selfUser = message.getJDA().getSelfUser();
        this.author = message.getJDA().getUserById("258316399049572353");
    }

    protected String getBotName() {
        return selfUser.getName();
    }

    protected String getBotURL() {
        return null;
    }

    protected String getBotAvatarURL() {
        return selfUser.getAvatarUrl();
    }

    protected abstract Color getColor();

    protected abstract String getTitle();

    protected abstract String getDescription();

    protected abstract String getThumbnail();

    protected abstract String getFooterText();

    protected abstract String getFooterIconURL();

    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(getBotName(), getBotURL(), getBotAvatarURL())
                .setColor(getColor())
                .setTitle(getTitle())
                .setDescription(getDescription())
                .setThumbnail(getThumbnail())
                .setFooter(getFooterText(), getFooterIconURL())
                .build();
    }

}
