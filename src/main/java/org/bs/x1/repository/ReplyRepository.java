package org.bs.x1.repository;

import org.bs.x1.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply,Long>{

    // 목록 조회
    // JPQL
    @Query("select r from Reply r where r.board.bno = :bno")
    // pageable을 사용하면 반환값은 무조건 Page;
    Page<Reply> listBoard(@Param("bno") Long bno , Pageable pageable); //@Param 빼먹지 말자
    
    // 댓글 달린 게시글 개수 조회
    @Query("select count(r) from Reply r where r.board.bno =:bno")
    long getCountBoard(@Param("bno") Long bno);
}
