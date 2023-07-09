package org.bs.x1.repository.search;

import java.util.List;

import org.bs.x1.domain.Board;
import org.bs.x1.dto.BoardDTO;
import org.bs.x1.dto.BoardListRcntDTO;
import org.bs.x1.dto.PageRequestDTO;
import org.bs.x1.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface BoardSearch {
    
    // 페이징 처리 , switch문 (검색 조건) (String searchType , String keyword 추가)
    Page<Board> search1(String searchType , String keyword ,Pageable pageable);

    // 페이지 처리 , object[]로 받기
    Page<Object[]> searchWithRcnt(String searchType , String keyword , Pageable pageable);

    // 서비스 계층에서 변환을 없애버리기 위해서 -> PageResponseDTO타입으로 받자!
    PageResponseDTO<BoardListRcntDTO> searchDTORcnt (PageRequestDTO requestDTO);

    // default : 인터페이스 내에서 메서드를 구현할 수 있게 해주는 키워드
    default Pageable makePageable(PageRequestDTO requestDTO){

        // getPage-1을 주는 이유는 pageable 객체를 생성할 때 일관성있게 0부터 시작하기 위함
        Pageable pageable = PageRequest.of(requestDTO.getPage()-1,
        requestDTO.getSize(),
        Sort.by("bno").descending());

        return pageable;

    }



}
