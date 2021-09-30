package com.tr.activity.poll.entity.poll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="options")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="option",length = 100,nullable = false)
    private String option;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="poll_id",nullable = false)
    @JsonIgnore
    private Poll poll;

}
