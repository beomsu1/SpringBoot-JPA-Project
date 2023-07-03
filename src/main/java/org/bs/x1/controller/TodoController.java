package org.bs.x1.controller;

import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.dto.TodoDTO;
import org.bs.x1.service.TodoService;
import org.hibernate.engine.jdbc.env.internal.LobCreationLogging_.logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/todos/")
@RequiredArgsConstructor
@CrossOrigin
@Log4j2
public class TodoController {

    private final TodoService todoService;

    @GetMapping("list")
    public PageResponseDTO<TodoDTO> list(){
        
        return todoService.getList();
    }

    @PostMapping("/")
    public TodoDTO register(@RequestBody TodoDTO todoDTO){

        log.info("register-------------");
        log.info(todoDTO);

        return todoService.register(todoDTO);
    }


    
}
