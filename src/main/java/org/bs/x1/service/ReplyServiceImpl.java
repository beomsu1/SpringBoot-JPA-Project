package org.bs.x1.service;

import java.util.List;
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
}
