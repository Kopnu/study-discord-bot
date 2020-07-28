package love.korni.studydiscordbot.service;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public interface ChannelService {

    List<GuildChannel> getTextChannels(String guildId);

    void sendMessageToChannel(String channelId, Message message);

}
