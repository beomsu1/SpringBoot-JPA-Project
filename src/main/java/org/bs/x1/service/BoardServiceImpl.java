package org.bs.x1.service;

import java.util.Optional;

import org.bs.x1.domain.Board;
import org.bs.x1.dto.BoardDTO;
import org.bs.x1.dto.BoardListRcntDTO;
import org.bs.x1.dto.PageRequestDTO;
import org.bs.x1.dto.PageResponseDTO;
import org.bs.x1.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{


    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;
    
    
    @Override
    public PageResponseDTO<BoardListRcntDTO> listRcnt(PageRequestDTO pageRequestDTO) {

        log.info("-----------------");
        log.info(pageRequestDTO);

        return boardRepository.searchDTORcnt(pageRequestDTO);
    }

    // 조회
    @Override
    public BoardDTO getOne(Long bno) {

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        BoardDTO dto = modelMapper.map(board, BoardDTO.class);

        return dto;
    }
    
}
