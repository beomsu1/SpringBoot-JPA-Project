package org.bs.x1.repository;

import org.bs.x1.domain.Board;
import org.bs.x1.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board,Long> ,BoardSearch{

    // 제목으로 검색
    // title을 찾고 그 결과를 Board타입의 리스트로 반환
    List<Board> findByTitleContaining(String title);

    // 내용으로 검색 , 페이징 처리까지
    // Pageable 을 사용했으니 반환타입은 Page가 되어야함
    Page<Board> findByContentContaining(String content , Pageable pageable);

    // JPQL - @Query를 사용하는 코드
    // title을 검색
    // 쿼리문에 공백조심!!!
    @Query("select b from Board b where b.title = :title")
    List<Board> listTitle(@Param("title") String title);

    // like 사용해서 검색
    @Query("select b from Board b where b.title like %:title% ")
    List<Object[]> listTitle2(@Param("title") String title);

    // 페이징처리 까지 해주는 검색 기능
    @Query("select b from Board b where b.title like %:title% ")
    Page<Object[]> listTitle2(@Param("title") String title , Pageable pageable);

    // update
    @Modifying
    // 파라미터는 :뒤에 바로 붙여서 사용해야 오류가 안 뜸
    @Query("update Board b set b.title = :title where b.bno = :bno ")
    int modifyTitle(@Param("title")String title , @Param("bno") Long tno);

    // Native Query
    // 기본 SQL에 사용하는 문법을 사용한다. 동적으로 접근하는거라 JPA에 위반!
    @Query( value =  "select * from t_board" , nativeQuery = true)
    List<Object[]> listNative();

    // 댓글 갯수
    // Object의 배열로 bno ,title , writer , board , reply join
    @Query("select b.bno, b.title, b.writer , count(r) from Board b left outer join Reply r on r.board = b group by b order by b.bno desc")
    List<Object[]> getListWithRcnt();
}
