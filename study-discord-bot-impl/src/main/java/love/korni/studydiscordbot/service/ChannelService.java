package love.korni.studydiscordbot.service;

import love.korni.studydiscordbot.exception.UnsupportedTypeOfChannelException;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

/**
 * Service for work with channel.
 */
public interface ChannelService {

    /**
     * Create a channel.
     *
     * @param guild       guild where need to create a channel
     * @param name        name of channel
     * @param channelType type of a channel. Supported ChannelType.TEXT and ChannelType.VOICE
     * @return created channel
     * @throws UnsupportedOperationException error if ChannelType is unsupported
     * @see ChannelType
     */
    GuildChannel createChannel(Guild guild, String name, ChannelType channelType) throws UnsupportedTypeOfChannelException;

    /**
     * Create a channel and set category as parent.
     *
     * @param category    category where need to create a channel
     * @param name        name of channel
     * @param channelType type of a channel. Supported ChannelType.TEXT and ChannelType.VOICE
     * @return created channel
     * @throws UnsupportedOperationException error if ChannelType is unsupported
     * @see ChannelType
     */
    GuildChannel createChannel(Category category, String name, ChannelType channelType) throws UnsupportedTypeOfChannelException;

    /**
     * Delete the channel.
     *
     * @param guild guild where need to delete a channel
     * @param id    channel id
     */
    void deleteChannel(Guild guild, Long id);

    /**
     * Get channel list
     *
     * @param guildId guild
     * @return list of channels
     */
    List<GuildChannel> getTextChannels(String guildId);

    /**
     * Send message to current channel. Use {@link net.dv8tion.jda.api.MessageBuilder} for create a Message.
     *
     * @param channelId channel id
     * @param message   message object
     */
    void sendMessageToChannel(String channelId, Message message);

}
