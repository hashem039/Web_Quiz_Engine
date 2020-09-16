package engine;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import engine.DBModel.DBUser;
import engine.SpringSecurity.MyUserDetails;
import engine.SpringSecurity.MyUserDetailsService;
import engine.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;

@SpringBootApplication
public class WebQuizEngine {

    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
        System.out.println("Hello Hashem, Lets start...");

    }

}
