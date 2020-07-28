package love.korni.studydiscordbot.command;

import love.korni.studydiscordbot.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InfoCmdTest {

    private static final String TEXT = "Some new text";

    @Mock
    private Message message;

    @Mock
    private MessageChannel messageChannel;

    @Spy
    private InfoCmd infoCmd;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void handle() {
        when(message.getContentStripped()).thenReturn("@Bot info " + TEXT);
        when(message.getChannel()).thenReturn(messageChannel);

        infoCmd.handle(message);

        verify(message).getChannel();
        verify(messageChannel).sendMessage(TEXT);
    }
}