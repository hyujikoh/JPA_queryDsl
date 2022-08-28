package com.example.demo;

import com.example.demo.user.entity.SiteUser;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원생성")
    void t1() {
       SiteUser u1 = SiteUser.builder()
               .username("user3")
               .password("{noop}123")
               .email("user3@test.com")
               .build();
        SiteUser u2 = SiteUser.builder()
                .username("user4")
                .password("{noop}123")
                .email("user4@test.com")
                .build();
        userRepository.saveAll(Arrays.asList(u1, u2));
    }

    @Test
    @DisplayName("회원생성")
    void t2() {
        userRepository.getQslUser(1L);
    }

}
