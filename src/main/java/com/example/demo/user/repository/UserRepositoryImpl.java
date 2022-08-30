package com.example.demo.user.repository;


import com.example.demo.user.entity.QSiteUser;
import com.example.demo.user.entity.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.LongSupplier;

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

    @Override
    public Page<SiteUser> searchQsl(String kw, Pageable pageable) {
        List<SiteUser> users= jpaQueryFactory.select(siteUser)
                .from(siteUser)
                .where(siteUser.username.contains(kw).or(siteUser.email.contains(kw)))
                .offset(pageable.getPageSize()) // 몇개를 건너 띄어야 하는지 LIMIT 1
                .limit(pageable.getOffset())//한페이지에 몇개를 건너 띄어야하는지 LIMIT ?,{1}
                .orderBy(siteUser.id.asc())
                .fetch();
        LongSupplier totalSuppiler = () -> 2 ;
        return PageableExecutionUtils.getPage(users,pageable,null);

    }
}
