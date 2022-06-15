package co.com.sofka.questions.services;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository repository;
    private QuestionMapper mapper = new QuestionMapper();

    public Flux<QuestionDTO> findByAll(){
        return repository.findAll()
                .map(mapper.mapperQuestionToDTO());
    }

}
