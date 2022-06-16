package co.com.sofka.questions.mappers;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class AnswerMapper {
    public Function<AnswerDTO, Answer> mapperToAnswer(String id){
        return dto -> {
            Answer answer = new Answer();
            answer.setId(id);
            answer.setQuestionId(dto.getQuestionId());
            answer.setUserId(dto.getUserId());
            answer.setPosition(dto.getPosition());
            answer.setAnswer(dto.getAnswer());
            return answer;
        };
    }

    public Function<Answer, AnswerDTO> mapperAnswerToDTO(){
        return answer -> {
            AnswerDTO dto = new AnswerDTO();
            dto.setId(answer.getId());
            dto.setAnswer(answer.getAnswer());
            dto.setQuestionId(answer.getQuestionId());
            dto.setUserId(answer.getUserId());
            dto.setPosition(answer.getPosition());
            return dto;
        };
    }




}
