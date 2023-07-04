package org.bs.x1.service;

import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.dto.TodoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoService {

    PageResponseDTO<TodoDTO> getList();

    // 등록
    TodoDTO register(TodoDTO dto);

    // 조회
    TodoDTO getOne(Long tno);

    // 삭제
    void remove(Long tno);

    // 수정
    void modify(TodoDTO dto);
    
}
