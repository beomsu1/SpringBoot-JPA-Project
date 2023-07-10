package org.bs.x1.service;

import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.dto.ReplyDTO;
import org.bs.x1.dto.ReplyPageRequestDTO;

public interface ReplyService {
    
    // 리턴 타입은 PageResponseDTO
    PageResponseDTO<ReplyDTO> list (ReplyPageRequestDTO requestDTO);

    // 등록
    Long register (ReplyDTO replyDTO);

    // 조회
    ReplyDTO read (Long rno);

    // 삭제
    void remove (Long rno);

    // 수정
    void modify (ReplyDTO replyDTO);
}
