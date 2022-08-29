package com.example.demo;

import com.example.demo.user.entity.SiteUser;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("test") // 테스트 모드 활성화
// 이렇게 클래스 @Transactional를 붙이면, 클래스의 각 테스트케이스에 전부 @Transactional 붙은 것과 동일 , T1 케이스 실행시 롤백이 된다
// @Test + @Transactional 조합은 자동으로 롤백을 유발시킨다.
@Transactional
class DemoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원생성")
    void t1() {
        SiteUser u3 = SiteUser.builder()
                .username("user3")
                .password("{noop}1234")
                .email("user3@test.com")
                .build();

        SiteUser u4 = SiteUser.builder()
                .username("user4")
                .password("{noop}1234")
                .email("user4@test.com")
                .build();
        userRepository.saveAll(Arrays.asList(u3, u4));
    }

    @Test
    @DisplayName("회원생성")
    void t2() {
        userRepository.getQslUser(4L);
    }

}
