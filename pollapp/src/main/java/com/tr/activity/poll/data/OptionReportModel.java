package com.tr.activity.poll.data;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OptionReportModel {

    private long optionId;
    private String optionName;
    private long voteCount;

}
