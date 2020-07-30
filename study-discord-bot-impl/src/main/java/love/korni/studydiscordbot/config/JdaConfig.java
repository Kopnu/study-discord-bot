package love.korni.studydiscordbot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;
import java.util.List;

@Configuration
public class JdaConfig {

    @Value("${token}")
    private String token;

    @Bean
    public JDA jda(List<ListenerAdapter> listenerAdapters) throws LoginException {
        JDA jda = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("Mention me & type help"))
                .build();
        jda.addEventListener(listenerAdapters.toArray());
        return jda;
    }

}
