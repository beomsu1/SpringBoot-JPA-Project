package org.bs.x1.service;

import org.bs.x1.dto.BoardDTO;
import org.bs.x1.dto.BoardListRcntDTO;
import org.bs.x1.dto.PageRequestDTO;
import org.bs.x1.dto.PageResponseDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface BoardService {
    
    // 댓글 개수도 포함한 리스트 목록
    PageResponseDTO<BoardListRcntDTO> listRcnt (PageRequestDTO pageRequestDTO);

    
    // 조회
    BoardDTO getOne(Long bno);
}
