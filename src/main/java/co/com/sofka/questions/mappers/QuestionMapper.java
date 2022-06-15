package co.com.sofka.questions.mappers;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class QuestionMapper {
    public Function<QuestionDTO, Question> mapperToQuestion(String id){
        return dto -> {
            Question question = new Question();
            question.setId(id);
            question.setUserId(dto.getUserId());
            question.setQuestion(dto.getQuestion());
            question.setCategory(dto.getCategory());
            question.setType(dto.getType());
            return question;
        };
    }

    public Function<Question, QuestionDTO> mapperQuestionToDTO(){
        return question -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setId(question.getId());
            dto.setUserId(question.getUserId());
            dto.setQuestion(question.getQuestion());
            dto.setCategory(question.getCategory());
            dto.setType(question.getType());
            return dto;
        };
    }


}
