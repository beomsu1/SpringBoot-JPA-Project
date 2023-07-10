package org.bs.x1.repository;

import org.bs.x1.domain.Board;
import org.bs.x1.domain.Reply;
import org.bs.x1.dto.ReplyPageRequestDTO;
import org.bs.x1.service.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    //의존성 주입
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyService replyService;

    // reply insert

    // @Test
    // public void insertTest(){
    
    //     // board의 bno만 알면 생성이 가능하다
    //     Long bno = 100L;

    //     // board 생성
    //     Board board = Board.builder().bno(bno).build();

    //     // reply 생성
    //     Reply reply = Reply.builder()
    //     .replyText("Sample Reply")
    //     .replyer("user1")
    //     // board를 참조해야 댓글 생성이 가능하다
    //     .board(board)
    //     .build();

    //     // 만든 댓글을 저장하자
    //     replyRepository.save(reply);
    // }
    
    // // 댓글 여러개 추가

    // @Test
    // public void insertDumeisTest(){

    //     // 배열을 생성해서 한번에 추가하자
    //     Long[] bnoArr = {99L, 92L, 90L , 87L ,82L};
    
    //     for (Long bno : bnoArr) {
            
    //         // board 생성하기
    //         Board board = Board.builder().bno(bno).build();

    //         // 댓글 생성하기
    //         for (int i = 0; i < 100 ; i++) {
                
    //             Reply reply = Reply.builder()
    //             .replyText("Reply" + bno + "--" + i)
    //             .replyer("user"+ i)
    //             .board(board)
    //             .build();


    //             // 댓글 저장
    //             replyRepository.save(reply);
    //         }
    //     }
    
   // }

   // 목록 조회
   @Test
    public void listBoardTest(){

        // bno 값을 설정하자
        Long bno = 92L;

        // 페이지 처리를 하자 . rno를 오름차순으로 정렬
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").ascending());

        // bno 와 pageable을 받고 목록 조회해서 그 결과를 page타입으로 반환해서 result에 저장
        Page<Reply> result = replyRepository.listBoard(bno, pageable);

        result.get().forEach(r -> log.info(r));
    }

    // 댓글 달린 게시글 개수 조회
    @Test
    public void testCount(){
        Long bno = 92L;

        log.info("-------------------------");
        log.info(replyRepository.getCountBoard(bno));
    }

    @Test
    public void testListLast(){

        ReplyPageRequestDTO requestDTO = ReplyPageRequestDTO.builder()
        .bno(99L)
        .last(true)
        .build(); 

        log.info(replyService.list(requestDTO));
    }


    
}
