package com.tr.activity.poll.entity.poll;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",length = 500,nullable = false,unique = true)
    @NotBlank( message="question name could not be empty !")
    private String questionName;

    @OneToMany(mappedBy = "poll",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotEmpty(message="choices could not be empty")
    private List<Option> choices=new ArrayList<Option>();




}
