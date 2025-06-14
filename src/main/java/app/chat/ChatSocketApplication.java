package app.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = {"app.*"})
@ComponentScan(basePackages = {"app.chat.*"})
@EntityScan(basePackages = {"app.chat.model"})
@EnableJpaRepositories(basePackages = {"app.chat.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
public class ChatSocketApplication {

	public static void main(String[] args) {
		app.chat.config.DotenvLoader.init();
		SpringApplication.run(ChatSocketApplication.class, args);
	}

}
