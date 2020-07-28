package love.korni.studydiscordbot.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

class MessageUtilsTest {

    private static final String BOT_NAME = "Bot (S)";
    private static final String TEXT = "cmd";
    private static final String FULL_TEXT = "@" + BOT_NAME + " " + TEXT;

    @Test
    void getClearMessageContent() {
        JDA jda = Mockito.mock(JDA.class);
        SelfUser botUser = Mockito.mock(SelfUser.class);
        Member member = Mockito.mock(Member.class);
        Message message = Mockito.mock(Message.class);
        Guild guild = Mockito.mock(Guild.class);

        when(jda.getSelfUser()).thenReturn(botUser);
        when(message.getJDA()).thenReturn(jda);
        when(message.getGuild()).thenReturn(guild);
        when(message.getContentStripped()).thenReturn(FULL_TEXT);
        when(guild.getMember(any(User.class))).thenReturn(member);
        when(member.getNickname()).thenReturn(BOT_NAME);

        String clearMessageContent = MessageUtils.getClearMessageContent(message);

        assertEquals(clearMessageContent, TEXT);
    }
}