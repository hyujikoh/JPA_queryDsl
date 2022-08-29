package com.example.demo.user.repository;


import com.example.demo.user.entity.QSiteUser;
import com.example.demo.user.entity.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.demo.user.entity.QSiteUser.siteUser;

@RequiredArgsConstructor
//커스텀을 구현한 클래스를 만들어줘야한다. 어려운걸 만들기 위한 클래스
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public SiteUser getQslUser(Long id) {
        /*sel from siteuser where id = ? */
        return jpaQueryFactory.select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }
    @Override
    public Long getQslUserCount() {
        /*sel from siteuser where id = ? */
        return jpaQueryFactory.select(siteUser.count())
                .from(siteUser)
                .fetchOne();
    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {
        return jpaQueryFactory.select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .limit(1L)
                .fetchOne();
    }

    @Override
    public List<SiteUser> getQslUserOrderByIdAsc() {
        return jpaQueryFactory.select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetch();
    }

    @Override
    public List<SiteUser> getQslUserByLikeIdOrEmail(String name) {
        return jpaQueryFactory.select(siteUser)
                .from(siteUser)
                .where(siteUser.username.contains(name).or(siteUser.email.contains(name)))
                .fetch();
    }
}
