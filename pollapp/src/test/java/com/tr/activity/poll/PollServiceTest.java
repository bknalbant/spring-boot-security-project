package com.tr.activity.poll;


import com.tr.activity.poll.data.OptionReportModel;
import com.tr.activity.poll.entity.poll.Answers;
import com.tr.activity.poll.entity.poll.Option;
import com.tr.activity.poll.entity.poll.Poll;
import com.tr.activity.poll.entity.user.UserInfo;
import com.tr.activity.poll.exception.PollNotFoundException;
import com.tr.activity.poll.repository.AnswerRepository;
import com.tr.activity.poll.repository.PollRepository;
import com.tr.activity.poll.service.impl.PollServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PollServiceTest {

    @Mock
    private PollRepository pollRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    PollServiceImpl pollService;


    @Test
    public void should_insert_poll_when_available_poll(){

        Poll pollModel= buildPollModel("Şampiyon kim olur","Fenerbahçe","Galatasaray");

        when(pollRepository.save(isA(Poll.class))).thenReturn(pollModel);
        Poll question = pollService.createQuestion(pollModel);


        verify(pollRepository).save(pollModel);

        assertEquals(pollModel.getQuestionName(),question.getQuestionName());


    }

    @Test
    public void do_not_anything_when_null_insert(){

        Poll pollModel= buildPollModel("Şampiyon kim olur","Fenerbahçe","Galatasaray");

        when(pollRepository.save(any(Poll.class))).thenReturn(null);
        Poll question = pollService.createQuestion(pollModel);

        verify(pollRepository).save(pollModel);

        assertNull(question);

    }

    @Test
    public void should_get_all_data_when_loading_poll(){

        List<Poll> pollList=buildPollList();

        when(pollRepository.findAll()).thenReturn(pollList);

        Optional<List<Poll>> allQuestion = pollService.getAllQuestion();

        verify(pollRepository,atLeast(1)).findAll();

        assertTrue(allQuestion.get().containsAll(pollList));
    }

    @Test(expected= PollNotFoundException.class)
    public void should_get_an_exception_when_no_data_found(){


        when(pollRepository.findAll()).thenReturn(null);

        Optional<List<Poll>> allQuestion = pollService.getAllQuestion();

        verify(pollRepository,atLeast(1)).findAll();

        pollService.getAllQuestion();

    }


    @Test
    public void should_mark_question_when_user_selected_option(){

        Answers answers= buildAnswer();

        when(answerRepository.save(any(Answers.class))).thenReturn(answers);

        pollService.markQuestion(1L,2L,1L);

        verify(answerRepository,atLeast(1)).save(any(Answers.class));
    }

    @Test
    public void should_get_poll_report_when_call_method(){

        when(answerRepository.getAnswersByQuestion(anyLong())).thenReturn(List.of(OptionReportModel.builder().build()));

        Optional<List<OptionReportModel>> pollReport = pollService.getPollReport(1L);

        assertTrue(pollReport.isPresent());

        verify(answerRepository,atLeastOnce()).getAnswersByQuestion(1L);

        assertNotNull(pollReport);

        assertTrue(!pollReport.isEmpty());
    }

    @Test(expected= PollNotFoundException.class)
    public void should_get_an_exception_when_no_data_found_on_report(){


        when(answerRepository.getAnswersByQuestion(anyLong())).thenReturn(null);

        Optional<List<OptionReportModel>> allQuestion = pollService.getPollReport(2L);

        verify(answerRepository,atLeastOnce()).getAnswersByQuestion(1L);

    }



    private Answers buildAnswer() {
        Answers answers = Answers.builder().id(1L).build();
        answers.setOption(Option.builder().id(1L).option("fener").build());
        answers.setPool(Poll.builder().id(2L).questionName("who wins?").build());
        answers.setUser(UserInfo.builder().id(3L).build());

        return  answers;
    }


    private List<Poll> buildPollList() {

        List<Poll> pollList = Stream.of(buildPollModel("Hava nasıl?", "sıcak", "soğuk"),
                buildPollModel("nasılsın", "iyi", "kötü")).collect(Collectors.toList());

        return pollList;
    }

    private Poll buildPollModel(String pollQuestion,String ...args) {
        Option option =Option.builder().id(1L).option(args[0]).build();
        Option option1 =Option.builder().id(1L).option(args[1]).build();
        return Poll.builder().id(2L).questionName(pollQuestion).choices(Arrays.asList(option,option1) ).build();
    }
}
