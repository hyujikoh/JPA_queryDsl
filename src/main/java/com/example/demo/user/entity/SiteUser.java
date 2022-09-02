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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<InterestKeyword> interestKeywords = new HashSet<>();

    public void addInterestKeywordContent(String keywordContent) {
        interestKeywords.add(new InterestKeyword(this,keywordContent));
    }
    public void delInterestKeywordContent(String keywordContent) {
        interestKeywords.remove(new InterestKeyword(this,keywordContent));
    }

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SiteUser> followers = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SiteUser> followings = new HashSet<>();


    public void follow(SiteUser following) {
        if (this == following) return;
        if (following == null) return;
        if (this.getId() == following.getId()) return;
        following.getFollowers().add(this);
        following.following(this);
    }
    public void following(SiteUser follower) {
        if (this == follower) return;
        if (follower == null) return;
        if (this.getId() == follower.getId()) return;
        follower.getFollowings().add(this);

    }
}