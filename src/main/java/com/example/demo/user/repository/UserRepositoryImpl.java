package com.example.demo.user.repository;


import com.example.demo.user.entity.SiteUser;

//커스텀을 구현한 클래스를 만들어줘야한다. 어려운걸 만들기 위한 클래스
public class UserRepositoryImpl implements UserRepositoryCustom {
    @Override
    public SiteUser getQslUser(Long id) {
        return null;
    }
}
