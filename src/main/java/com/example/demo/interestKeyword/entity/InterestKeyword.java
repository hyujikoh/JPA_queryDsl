package com.example.demo.interestKeyword.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InterestKeyword {
    @Id
    @EqualsAndHashCode.Include
    private String content;

}
