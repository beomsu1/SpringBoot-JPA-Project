package org.bs.x1.controller;

import org.bs.x1.dto.BoardDTO;
import org.bs.x1.dto.BoardListRcntDTO;
import org.bs.x1.dto.PageRequestDTO;
import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.service.BoardService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class BoardController {

    private final BoardService boardService;

    // list 조회 ( + 댓글 개수 )
    @GetMapping("/list")
    // @ParameterObject -> pageRequestDTO 객체가 ORM 프레임워크에 의해 쿼리 작성 가능
    // ORM 프레임워크 : 객체와 관계형DB 간의 매핑을 자동화 해주는 프레임워크 -> Hibernate , JPA 등
    public PageResponseDTO<BoardListRcntDTO> getList(@ParameterObject PageRequestDTO pageRequestDTO){

        log.info(pageRequestDTO);

        return boardService.listRcnt(pageRequestDTO);
    }

    @GetMapping("/{bno}")
    public BoardDTO getRead(@PathVariable("bno")Long bno){

        return boardService.getOne(bno);
    }
    
}
