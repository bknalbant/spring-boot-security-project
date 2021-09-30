package com.tr.activity.poll.entity.poll;

import com.tr.activity.poll.entity.user.UserInfo;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="answers",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "poll_id",
                "user_id"
        })
})
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="poll_id",nullable = false)
    private Poll pool;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    private UserInfo user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="option_id",nullable = false)
    private Option option;


}
