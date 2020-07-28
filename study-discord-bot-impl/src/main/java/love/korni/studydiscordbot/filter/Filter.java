package love.korni.studydiscordbot.filter;

import net.dv8tion.jda.api.entities.Message;

public interface Filter {

    void handle(Message message);

}
