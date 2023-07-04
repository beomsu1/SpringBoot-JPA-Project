package org.bs.x1.repository.search;

import java.util.List;

import org.bs.x1.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
    
    // 페이징 처리 , switch문 (검색 조건) (String searchType , String keyword 추가)
    Page<Board> search1(String searchType , String keyword ,Pageable pageable);


}
