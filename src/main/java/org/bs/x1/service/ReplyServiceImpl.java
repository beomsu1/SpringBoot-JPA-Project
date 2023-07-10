package org.bs.x1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bs.x1.domain.Reply;
import org.bs.x1.dto.PageRequestDTO;
import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.dto.ReplyDTO;
import org.bs.x1.dto.ReplyPageRequestDTO;
import org.bs.x1.repository.ReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO) {

        // isLast는 requestDTO가 마지막인지 판단
        boolean last = requestDTO.isLast();

        int pageNum = requestDTO.getPage();

        if (last) {
            // 해당 게시물의 댓글 총 개수
            long totalCount = replyRepository.getCountBoard(requestDTO.getBno());

            // 진짜 페이지 넘버
            pageNum = (int) (Math.ceil(totalCount / (double) requestDTO.getSize()));

            // pageNum가 존재히자 않을 때 기본 값 부여
            pageNum = pageNum <= 0 ? 1 : pageNum;
        }

        Pageable pageable = PageRequest.of(pageNum - 1, requestDTO.getSize(), Sort.by("rno"));

        Page<Reply> result = replyRepository.listBoard(requestDTO.getBno(), pageable);

        log.info("-------------");
        log.info(result.getNumber());
        log.info(result.getContent());

        // 결과의 총 개수
        long totalReplyCount = result.getTotalElements();

        List<ReplyDTO> dtoList = result.get().map(en -> modelMapper.map(en, ReplyDTO.class))
                .collect(Collectors.toList());

        // PageResponseDTO 객체를 생성하고 초기화 한 후 , 페이지 번호 설정 후 결과 반환
        PageResponseDTO<ReplyDTO> responseDTO = new PageResponseDTO<>(dtoList, totalReplyCount, requestDTO);
        responseDTO.setPage(pageNum);
        return responseDTO;
    }

    // 등록
    @Override
    public Long register(ReplyDTO replyDTO) {

        // Reply로 변환하는 이유 : 데이터의 저장 또는 처리를 위해 Reply 엔티티 객체를 사용해야 할 때가 있기에
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        log.info(reply);

        // reply에 있는 rno를 가져와서 newRno를 만듬
        Long newRno = replyRepository.save(reply).getRno();

        return newRno;
    }

    // 조회
    @Override
    public ReplyDTO read(Long rno) {

        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply = result.orElseThrow();

        return modelMapper.map(reply, ReplyDTO.class);
    }

    // 삭제
    @Override
    public void remove(Long rno) {

        // 조회 후 삭제

        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply = result.orElseThrow();

        // 삭제인데 업데이트 하는거랑 같다.
        reply.changeText("해당 글은 삭제되었습니다.");
        reply.changeFile(null);

        replyRepository.save(reply);
    }

    // 수정
    @Override
    public void modify(ReplyDTO replyDTO) {

        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());

        Reply reply = result.orElseThrow();

        // 업데이트
        reply.changeText(replyDTO.getReplyText());
        reply.changeFile(replyDTO.getReplyFile());

        replyRepository.save(reply);
    }
}
