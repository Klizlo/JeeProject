package kw.pollub.myboardgamelist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MyBoardGameListApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBoardGameListApplication.class, args);
    }

}
