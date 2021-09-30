package com.tr.activity.poll.service;

import com.tr.activity.poll.data.OptionReportModel;
import com.tr.activity.poll.entity.poll.Poll;

import java.util.List;
import java.util.Optional;

public interface PollService {

    public Poll createQuestion(Poll questionModel);

    public Optional<List<Poll>> getAllQuestion();

    void markQuestion(Long userid, Long questionid, Long choiseid);

    Optional<List<OptionReportModel>> getPollReport(Long id);
}
