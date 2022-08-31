package com.example.demo.user.entity;


import com.example.demo.interestKeyword.entity.InterestKeyword;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default  // 널값이 들어가는것을 방지하기 위해 유저 관련 내용생성 할때 비어있을경우
    private Set<InterestKeyword> interestKeywords = new HashSet<>();

    public void addInterestKeywordContent(String keywordContent) {
            interestKeywords.add(new InterestKeyword(keywordContent));
    }

}
