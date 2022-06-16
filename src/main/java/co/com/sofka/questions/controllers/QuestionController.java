package co.com.sofka.questions.controllers;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService service;

    @GetMapping()
    public ResponseEntity<Flux<QuestionDTO>> findAll(){
        Flux<QuestionDTO> list = service.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Mono<QuestionDTO>> findById(@PathVariable("id") String id){
        return new ResponseEntity(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Mono<String>> save(@RequestBody QuestionDTO questionDTO){
        return new ResponseEntity(service.save(questionDTO), HttpStatus.CREATED);
    }

}
