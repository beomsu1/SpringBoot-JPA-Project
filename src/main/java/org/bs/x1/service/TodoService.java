package org.bs.x1.service;

import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.dto.TodoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoService {

    PageResponseDTO<TodoDTO> getList();

    TodoDTO register(TodoDTO dto);
    
}
