package com.tr.activity.poll.repository;

import com.tr.activity.poll.data.OptionReportModel;
import com.tr.activity.poll.entity.poll.Answers;
import com.tr.activity.poll.entity.poll.Poll;
import com.tr.activity.poll.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answers,Long> {

    @Query("SELECT NEW com.tr.activity.poll.data.OptionReportModel(v.option.id,o.option,count(v.id)) FROM Answers v,Option o WHERE o.id=v.option.id and v.pool.id = :pollId GROUP BY v.option.id")
    List<OptionReportModel> getAnswersByQuestion(@Param("pollId") Long pollId);

}
