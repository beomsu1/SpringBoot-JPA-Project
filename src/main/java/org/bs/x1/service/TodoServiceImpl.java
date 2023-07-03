package org.bs.x1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.bs.x1.domain.Todo;
import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.dto.TodoDTO;
import org.bs.x1.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    
    private final ModelMapper modelMapper;

    private final TodoRepository todoRepository;
    
    @Override
    public PageResponseDTO<TodoDTO> getList() {

        Pageable pageable = PageRequest.of(0, 20, 
        Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        List<TodoDTO> dtoList = result.getContent().stream()
        .map(todo -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());

        PageResponseDTO<TodoDTO> response = new PageResponseDTO<>();

        response.setDtoList(dtoList);  
        
        return response;


    
    
    }

    @Override
    public TodoDTO register(TodoDTO dto) {
    
        Todo entity = modelMapper.map(dto, Todo.class);
        
        Todo result = todoRepository.save(entity);

        return modelMapper.map(result, TodoDTO.class);


    }
    
}
