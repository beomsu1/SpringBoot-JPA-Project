package org.bs.x1.repository.search;

import java.util.List;

import org.bs.x1.domain.Board;
import org.bs.x1.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{
    
    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(String searchType , String keyword ,Pageable pageable) {
        
        // QBoard.board는 Board 엔티티에 대응하는 Q타입 클래스는 QBoard의 인스턴스 생성을 의미
        QBoard board = QBoard.board;

        //Querydsl 을 사용하여 Board 엔티티를 대상으로 JPQL 쿼리를 작성하기 위한 JPQLQuery 객체를 생성
        JPQLQuery<Board> query = from(board);

        // 키워드 , searchType이 not null
        if( keyword != null && searchType != null){

            // 문자열을 배열로 바꾸기
            // tc -> [t,c]
            String[] searchArr = searchType.split("");

            // 우선 연산자 생성 -> BooleanBuilder
            BooleanBuilder searchBuilder = new BooleanBuilder();

            // switch 문을 사용해서 or을 만들자
            for (String type : searchArr) {
                switch(type){
                    // 검색조건
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }
            } // end for

            // 검색 조건을 where로 지정하자
            query.where(searchBuilder);
        }

        // getQuerydsl() -> Querydls 객체 반환
        // applyPagination(pageable, query) -> Querydls의 메소드로 pageable 객체 사용해서 페이징 처리를 query에 적용
        this.getQuerydsl().applyPagination(pageable, query);

        // 목록 데이터 출력
        List<Board> list = query.fetch();

        // 총 데이터 갯수
        Long count = query.fetchCount();

        log.info(list);
        log.info(count);

        // JPA에서 페이지화된 결과를 반환하기 위한 Page객체 생성 코드
        // list-> 현제 페이지에 포함되는 엔티티의 리스트
        // pageable -> 페이징 정보를 담고있는 Pagealbe의 객체
        // count -> 전체 엔티티의 개수
        return new PageImpl<>(list, pageable, count);

    }
}
