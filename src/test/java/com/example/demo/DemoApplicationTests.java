package com.example.demo;

import com.example.demo.user.entity.SiteUser;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("회원수")
    void t3() {
        assertThat(userRepository.getQslUserCount()).isEqualTo(2L);
    }


    @Test
    @DisplayName("가장오래된 회원")
    void t4() {
        assertThat(userRepository.getQslUserOrderByIdAscOne()).isEqualTo(userRepository.getQslUser(1L));
        SiteUser ul = userRepository.getQslUserOrderByIdAscOne();
        assertThat(ul.getId()).isEqualTo(1L);
        assertThat(ul.getEmail()).isEqualTo("user1@test.com");
        assertThat(ul.getPassword()).isEqualTo("{noop}1234");
        assertThat(ul.getUsername()).isEqualTo("user123");

    }

    @Test
    @DisplayName("가장오래된 id 기준 정렬")
    void t5() {
        List<SiteUser> ul = userRepository.getQslUserOrderByIdAsc();
        assertThat(ul.size()).isEqualTo(2);
        assertThat(ul.get(0)).isEqualTo(userRepository.getQslUser(1L));

    }

    @Test
    @DisplayName("user1이라는 정보 있는지")
    void t6() {
        List<SiteUser> ul = userRepository.getQslUserByLikeIdOrEmail("user1");
        assertThat(ul.size()).isEqualTo(1);
        assertThat(ul.get(0)).isEqualTo(userRepository.getQslUser(1L));

    }


    @Test
    @DisplayName("검색, Page 리턴")
    void t8() {
        long totalCount = userRepository.count();
        int pageSize = 1; // 한 페이지에 보여줄 아이템 개수
        int page = 1;
        int totalPages = (int) Math.ceil(totalCount / (double) pageSize);

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts)); // 한 페이지에 10까지 가능
        Page<SiteUser> usersPage = userRepository.searchQsl("user", pageable);


        assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
        assertThat(usersPage.getNumber()).isEqualTo(page);
        assertThat(usersPage.getSize()).isEqualTo(pageSize);


        List<SiteUser> users = usersPage.get().toList();

        assertThat(users.size()).isEqualTo(pageSize);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(2L);
        assertThat(u.getUsername()).isEqualTo("user2");
        assertThat(u.getEmail()).isEqualTo("user2@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");


        // 한 페이지에 나올 수 있는 아이템 수 : 1개
        // 현재 페이지 : 1
        // 정렬 : id 역순

        // 내용 가져오는 SQL
        /*
        SELECT site_user.*
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
        ORDER BY site_user.id ASC
        LIMIT 1, 1
         */

        // 전체 개수 계산하는 SQL
        /*
        SELECT COUNT(*)
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
         */
    }


    @Test
    @DisplayName("검색, Page 리턴, id DESC, pageSize=1, page=0")
    void t9() {
        long totalCount = userRepository.count();
        int pageSize = 1; // 한 페이지에 보여줄 아이템 개수
        int totalPages = (int) Math.ceil(totalCount / (double) pageSize);
        int page = 1;
        String kw = "user";

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts)); // 한 페이지에 10까지 가능
        Page<SiteUser> usersPage = userRepository.searchQsl(kw, pageable);

        assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
        assertThat(usersPage.getNumber()).isEqualTo(page);
        assertThat(usersPage.getSize()).isEqualTo(pageSize);

        List<SiteUser> users = usersPage.get().toList();

        assertThat(users.size()).isEqualTo(pageSize);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user123");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }


    @Test
    @DisplayName("중간테이블")
    @Rollback(value = false)
    void t10() {
        SiteUser u1 = userRepository.getQslUser(1L);
        SiteUser u2 = userRepository.getQslUser(2L);
        u2.addInterestKeywordContent("운동");
        u2.addInterestKeywordContent("신발수집");

        u1.addInterestKeywordContent("운동");
        u1.addInterestKeywordContent("신발수집");
        u1.addInterestKeywordContent("헬스");
        userRepository.save(u2);
        userRepository.save(u1);
// 엔티티클래스 : InterestKeyword(interest_keyword 테이블)
        // 중간테이블도 생성되어야 함, 힌트 : @ManyToMany
        // interest_keyword 테이블에 축구, 롤, 헬스에 해당하는 row 3개 생성

    }

    @Test
    @DisplayName("중간테이블 2명 추가 ")
    @Rollback(value = false)
    void t11() {
        SiteUser u1 = userRepository.getQslUser(1L);
        SiteUser u2 = userRepository.getQslUser(2L);
        u2.addInterestKeywordContent("운동");
        u2.addInterestKeywordContent("신발수집");
        userRepository.save(u2);
        u1.addInterestKeywordContent("운동");
        u1.addInterestKeywordContent("신발수집");
        u1.addInterestKeywordContent("헬스");
        userRepository.save(u1);

// 엔티티클래스 : InterestKeyword(interest_keyword 테이블)
        // 중간테이블도 생성되어야 함, 힌트 : @ManyToMany
        // interest_keyword 테이블에 축구, 롤, 헬스에 해당하는 row 3개 생성

    }
    @Test
    @DisplayName("축구에 관심이 있는 회원을 검색할 수 있다.")
    void t12() {
        SiteUser u1 = userRepository.getQslUser(1L);
        SiteUser u2 = userRepository.getQslUser(2L);
        u2.addInterestKeywordContent("운동");
        u2.addInterestKeywordContent("신발수집");
        userRepository.save(u2);

        u1.addInterestKeywordContent("축구");
        userRepository.save(u1);
        List<SiteUser> userList = userRepository.getQslUserWhereInterest("축구");
        assertThat(userList.get(0).getUsername()).isEqualTo(u1.getUsername());

// 엔티티클래스 : InterestKeyword(interest_keyword 테이블)
        // 중간테이블도 생성되어야 함, 힌트 : @ManyToMany
        // interest_keyword 테이블에 축구, 롤, 헬스에 해당하는 row 3개 생성

    }
}