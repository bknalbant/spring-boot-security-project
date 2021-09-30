package com.tr.activity.poll.service.impl;

import com.tr.activity.poll.data.OptionReportModel;
import com.tr.activity.poll.entity.poll.Answers;
import com.tr.activity.poll.entity.poll.Option;
import com.tr.activity.poll.entity.poll.Poll;
import com.tr.activity.poll.entity.user.UserInfo;
import com.tr.activity.poll.exception.PollNotFoundException;
import com.tr.activity.poll.repository.AnswerRepository;
import com.tr.activity.poll.repository.PollRepository;
import com.tr.activity.poll.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    private PollRepository pollRepository;


    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Poll createQuestion(Poll questionModel) {
        return pollRepository.save(questionModel);
    }

    @Override
    public Optional<List<Poll>> getAllQuestion() {
        Optional<List<Poll>> pollList = Optional.ofNullable (pollRepository.findAll());
        if(!pollList.isPresent() || pollList.get().isEmpty()){
            throw  new PollNotFoundException("any poll does not exist on system , try later !");
        }
        return pollList;
    }

    @Override
    public void markQuestion(Long userid, Long questionid, Long choiseid) {

        Answers answers = new Answers();
        answers.setPool(Poll.builder().id(questionid).build());
        answers.setUser(UserInfo.builder().id(userid).build());
        answers.setOption(Option.builder().id(choiseid).build());

      answerRepository.save(answers);
    }

    @Override
    public Optional<List<OptionReportModel>> getPollReport(Long id) {

        Optional<List<OptionReportModel>> optionReportModel = Optional.ofNullable(answerRepository.getAnswersByQuestion(id));

        if(!optionReportModel.isPresent() || optionReportModel.get().isEmpty()){
            throw  new PollNotFoundException("any poll report does not exist on system , try later !");
        }

        return optionReportModel;

    }

}
