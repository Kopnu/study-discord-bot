package love.korni.studydiscordbot.config;

import love.korni.studydiscordbot.listeners.MessageListener;
import love.korni.studydiscordbot.listeners.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class JdaConfig {

    @Value("${token}")
    private String token;

    @Bean
    public JDA jda(MessageListener messageListener, ReadyListener readyListener) throws LoginException {
        JDA jda = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("Mention me & type help"))
                .build();
        jda.addEventListener(
                messageListener,
                readyListener
        );
        return jda;
    }

}
