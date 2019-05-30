package ru.stepanenko.tm.config;


import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.stepanenko.tm.endpoint.*;

@Configuration
@NoArgsConstructor
@ComponentScan("ru.stepanenko.tm")
public class AppConfiguration {

    @Bean
    public ProjectEndpoint getProjectEndpoint() {
        return new ProjectEndpointService().getProjectEndpointPort();
    }

    @Bean
    public TaskEndpoint getTaskEndpoint() {
        return new TaskEndpointService().getTaskEndpointPort();
    }

    @Bean
    public SessionEndpoint getSessionEndpoint() {
        return new SessionEndpointService().getSessionEndpointPort();
    }

    @Bean
    public UserEndpoint getUserEndpoint() {
        return new UserEndpointService().getUserEndpointPort();
    }

    @Bean
    public ServerEndpoint getServerEndpoint() {
        return new ServerEndpointService().getServerEndpointPort();
    }
}
