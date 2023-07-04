package org.bs.x1.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bs.x1.domain.Board;
import org.bs.x1.repository.search.BoardSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    // 의존성 주입
    @Autowired
    private BoardRepository boardRepository;

    // insert
    @Test
    public void testInsert(){

        for (int i = 0; i < 100; i++) {

        Board board = Board.builder()
        .title("Sample Title" + i)
        .content("Sample Content"+ i)
        .writer("user"+ (i%10))
        .build();

        boardRepository.save(board);
            
        }


    }

    // read
    @Test
    public void testRead(){

        Long bno = 3L;

        Optional<Board> result = boardRepository.findById(bno);

        log.info("-------------");

        // 예외나면 던져라!
        Board board = result.orElseThrow();


        log.info(result.get());
    }

    // update
    // read -> update -> save 순으로 진행
    @Test
    public void testUpdate(){

        // 조회
        Long bno = 3L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        // 수정
        
        // title을 수정
        board.changeTitle("Update title");

        // board를 db에 save
        boardRepository.save(board);
    }

    // title로 검색
    @Test
    public void testQuery1(){

        // 제목에 1이라는 숫자가 있으면 다 보여줌 -> Board 타입의 리스트로 반환
        List<Board> list = boardRepository.findByTitleContaining("1");

        log.info(list);

    }

    // content로 검색
    @Test
    public void testQuery2(){

        // 0페이지 사이즈10 tno로 내림차순 정렬
        Pageable pageable = PageRequest.of(0, 10, 
        Sort.by("bno").descending());

        // 1이 포함되어 있는 content와 페이징처리를 한번에 검색 -> 그 값을 Board타입의 Page로 반환
        Page<Board> result = boardRepository.findByContentContaining("1", pageable);
    
        log.info("------------");
        log.info(result);
    }

    // jPQL title 검색
    @Test
    public void testQuery1_1(){
        List<Board> list = boardRepository.listTitle("1");

        log.info("-----------");
        log.info(list.size());
        log.info(list);
    }

    // like를 이용해서 Object[] 타입으로 검색
    @Test
    public void testQuery1_2(){
        List<Object[]> list = boardRepository.listTitle2("1");

        log.info("---------");
        log.info(list.size());
        
        // 배열들의 값을 보는 법 ( forEach 사용 )
        list.forEach(arr -> log.info(Arrays.toString(arr)));
    }

    // 페이징처리 까지 함께 하는 like 검색
    @Test
    public void testQuery1_3(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

        Page<Object[]> result = boardRepository.listTitle2("1", pageable);

        log.info(result);
    }

    // update
    @Test
    @Transactional
    @Commit
    public void testModify(){
        Long bno = 100L;
        String title = "업데이트할게요";
        
        // ModifyTitle에 반환 타입이 int라 count에 담고 확인해보자
        int count = boardRepository.modifyTitle(title, bno);

        log.info(count);
    }


    // Native Query -> JPA가 아님
    @Test
    public void listNative(){

        // 배열로 생성
        List<Object[]> result = boardRepository.listNative();

        // 배열들의 값을 보는방법 forEach
        result.forEach(arr -> log.info(Arrays.toString(arr)));
    }

    // Search
    @Test
    public void testSearch1(){
        
        // 페이징 처리를 하자
        Pageable pageable = PageRequest.of(0, 10, 
        Sort.by("bno").descending());

        Page<Board> result = boardRepository.search1("tcw", "1", pageable);

        log.info("--------------");
        // 총 갯수
        log.info(result.getTotalElements());

        result.get().forEach(b -> log.info(b));
    }
    
}
