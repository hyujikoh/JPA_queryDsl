package com.example.demo.user.repository;

import com.example.demo.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// queryDSL 적용하기 위해 custom interface 적용
public interface UserRepository extends JpaRepository<SiteUser, Long>,UserRepositoryCustom {

}