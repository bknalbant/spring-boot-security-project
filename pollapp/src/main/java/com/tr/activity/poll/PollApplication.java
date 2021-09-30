package com.tr.activity.poll;

import com.tr.activity.poll.entity.user.Role;
import com.tr.activity.poll.entity.user.UserInfo;
import com.tr.activity.poll.repository.RoleRepository;
import com.tr.activity.poll.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class PollApplication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

      Logger logger = LoggerFactory.getLogger(PollApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(PollApplication.class, args);

    }

    public PollApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {

            try{


                Role adminRole = Role.builder().id(1).name("ADMIN").build();

                Role userRole = Role.builder().id(2).name("USER").build();

                roleRepository.save(adminRole);
                roleRepository.save(userRole);


                Set<Role> roleListAdmin = Stream.of(adminRole, userRole).collect(Collectors.toSet());

                UserInfo admin=UserInfo.builder().id(1l).username("burak").password(encoder.encode("12345")).roles(roleListAdmin).build();


                Set<Role> roleListUser = Stream.of(userRole).collect(Collectors.toSet());

                UserInfo user=UserInfo.builder().id(2l).username("demo").password(encoder.encode("12345")).roles(roleListUser).build();

                userRepository.save(admin);

                userRepository.save(user);

            }catch(Exception e){
                    logger.info("data has not beeen initialized yet");
            }


        };
    }


     @Bean
     BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
