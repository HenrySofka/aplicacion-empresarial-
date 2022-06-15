package co.com.sofka.questions.controllers;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService service;

    @GetMapping()
    public ResponseEntity<Flux<QuestionDTO>> findAll(){
        Flux<QuestionDTO> list = service.findByAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

}
