package com.example.demo.user.repository;

import com.example.demo.user.entity.SiteUser;

import java.util.List;

public interface UserRepositoryCustom {
    SiteUser getQslUser(Long id);
    Long getQslUserCount();
    SiteUser getQslUserOrderByIdAscOne();
    List<SiteUser> getQslUserOrderByIdAsc();
    List<SiteUser> getQslUserByLikeIdOrEmail(String name);
}
