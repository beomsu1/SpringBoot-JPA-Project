package org.bs.x1.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.bs.x1.domain.Board;
import org.bs.x1.domain.QBoard;
import org.bs.x1.domain.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
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

    @Override
    public Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable) {

        // Querydsl
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        
        // reply 를 leftjoin -> reply.board == board
        query.leftJoin(reply).on(reply.board.eq(board));

        // 키워드이 not null , searchType이 not null 일 때
        if(keyword !=  null && searchType != null){

            //문자열을 배열로
            // tc -> [t,c]
            String[] searchArr = searchType.split("");

            // () 만들어주는게 BooleanBuiler다!
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {
                switch(type){
                    // 검색 조건
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }
            } // end for

            // 검색조건을 where절에 추가하자
            query.where(searchBuilder);

        }

        // board로 groupBy
        query.groupBy(board);

        // 조인이 되어있는 상태에서 어떤 값만 뽑아낼거야 하고 사용하는게 JPQL타입의 Tuple -> select
        // countDistinct -> 중복 제거
        JPQLQuery<Tuple> tupleQuery = 
        query.select(board.bno , board.title , board.writer , reply.countDistinct());

        // fetch 의 반환타입은 List<Tuple>이여서 object 타입으로 변환해줘야함
        List<Tuple> tuples = tupleQuery.fetch();

        // 페이징 처리 
        this.getQuerydsl().applyPagination(pageable, tupleQuery);

        // object[]로 변환
        List<Object[]> arrList =
        tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());
        
        log.info(tuples);

        long count = tupleQuery.fetchCount();

        log.info("count: " + count );
        
        return new PageImpl<>(arrList, pageable, count);
    }
}
