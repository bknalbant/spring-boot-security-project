package com.tr.activity.poll.controller;

import com.tr.activity.poll.data.OptionReportModel;
import com.tr.activity.poll.entity.poll.Poll;
import com.tr.activity.poll.exception.PollNotFoundException;
import com.tr.activity.poll.service.PollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/v1/poll")
public class PollController {

      @Autowired
      private PollService pollService;

      private static final Logger logger= LoggerFactory.getLogger(PollController.class);

      @Operation(summary = "if you pass poll object which is what you want to add  when method will added it on poll system")
      @ApiResponse(responseCode = "200",content=@Content(schema = @Schema(implementation = Boolean.class)))
      @PostMapping("/createQuestion")
      public ResponseEntity<Boolean> createQuestion(@Valid @RequestBody Poll poll){

            try{
                  poll.getChoices().forEach(c->{
                        c.setPoll(poll);
                  });
                  pollService.createQuestion(poll);
            }catch(Exception e){
                  logger.trace("an error occured when inserting question",e);
                  return ResponseEntity.noContent().build();
            }
            logger.info("question was added");

            return ResponseEntity.status(HttpStatus.OK).build();
      }

      @Operation(summary = "get all poll question operation")
      @ApiResponse(responseCode = "200",content=@Content(schema = @Schema(implementation = Poll.class)))
      @GetMapping(value = "/allQuestion",produces = "application/json")
      public ResponseEntity<List<Poll>> getAllQuestion(){
            try{
                  Optional<List<Poll>> allQuestion = pollService.getAllQuestion();

                  if(allQuestion.isPresent()){
                        logger.info("succesfull operation");
                        return ResponseEntity.ok(allQuestion.get());
                  }
                  logger.info("no data return");
                  return ResponseEntity.notFound().build();
            }catch(Exception ex){
                  logger.trace("an error occured when loading all questions",ex);
                  return ResponseEntity.notFound().build();
            }
      }

      @Operation(summary = "mark your answer to an poll question operation")
      @ApiResponse(responseCode = "200",content=@Content(schema = @Schema(implementation = Boolean.class)))
      @PutMapping("/markquestion/{userid}/{questionid}/{choiseid}")
      public ResponseEntity<Boolean> markQuestion(@PathVariable(value="userid") Long userid,
                                                    @PathVariable(value="questionid") Long questionid,
                                                    @PathVariable(value="choiseid") Long choiseid){

            try{
                  pollService.markQuestion(userid,questionid,choiseid);
                  logger.info("some question is marked");

            }catch(Exception e){
                  logger.trace("an error occured when answer the question",e);
                  return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.status(HttpStatus.OK).build();
      }

      @Operation(summary = "get specific poll report operation , you can call this method with poll id")
      @ApiResponse(responseCode = "200",content=@Content(schema = @Schema(implementation = OptionReportModel.class)))
      @GetMapping(value = "/reportquestion/{pollId}",produces = "application/json")
      public ResponseEntity<List<OptionReportModel>> getPollReportById(@PathVariable("pollId") Long id){
            try{
                  Optional<List<OptionReportModel>> optionReportModel = pollService.getPollReport(id);

                  if(optionReportModel.isPresent()){
                        logger.info("successfully created poll report");
                        return ResponseEntity.ok(optionReportModel.get());

                  }
                  return ResponseEntity.notFound().build();

            }catch(Exception ex){
                  logger.trace("an error occured when loading questions report chart",ex);
                  return ResponseEntity.notFound().build();
            }
      }


}
