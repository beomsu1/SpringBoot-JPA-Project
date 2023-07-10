package org.bs.x1.controller;

import java.util.Map;

import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.dto.ReplyDTO;
import org.bs.x1.dto.ReplyPageRequestDTO;
import org.bs.x1.service.ReplyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
@CrossOrigin
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{bno}/list")
    public PageResponseDTO<ReplyDTO> list (@PathVariable("bno") Long bno , ReplyPageRequestDTO requestDTO){
        
        log.info(bno);

        return replyService.list(requestDTO);
    }

    // 등록
    @PostMapping("/")
    public Map<String,Long> register(@RequestBody ReplyDTO replyDTO){

        log.info("ReplyController~~");
        log.info(replyDTO);

        // 새로운 rno 생성
        Long newRno = replyService.register(replyDTO);

        return Map.of("result" , newRno);
    }

    // 조회
    @GetMapping("/{rno}")
    public ReplyDTO getRead(@PathVariable("rno") Long rno){

        return replyService.read(rno);
    }

    //삭제
    @DeleteMapping("/{rno}")
    public Map<String,Long> remove(@PathVariable("rno") Long rno){

        replyService.remove(rno);

        return Map.of("result" , rno);
    }

    //수정
    @PutMapping("/{rno}")
    public Map<String , Long> modify(@RequestBody ReplyDTO replyDTO){

        replyService.modify(replyDTO);

        return Map.of("result" , replyDTO.getRno());
    }
    
}
