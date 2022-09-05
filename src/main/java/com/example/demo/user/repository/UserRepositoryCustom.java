package com.example.demo.user.repository;

import com.example.demo.interestKeyword.entity.InterestKeyword;
import com.example.demo.user.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
    SiteUser getQslUser(Long id);
    Long getQslUserCount();
    SiteUser getQslUserOrderByIdAscOne();
    List<SiteUser> getQslUserOrderByIdAsc();
    List<SiteUser> getQslUserByLikeIdOrEmail(String name);
    Page<SiteUser> searchQsl(String kw, Pageable pageable);

    List<SiteUser> getQslUserWhereInterest(String 축구);
    List<SiteUser> getQslUserWhereInterest_v2(String keyword);
    List<SiteUser> getQslUserWhereInterest_v3(String keyword);
    List<InterestKeyword> getQslKeywordWhereFollowing(SiteUser u);
    List<String> getByInterestKeywordContents_byFollowingsOf(SiteUser u);
}
