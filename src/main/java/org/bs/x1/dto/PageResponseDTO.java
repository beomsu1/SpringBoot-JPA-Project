package org.bs.x1.dto;

import java.util.List;

import lombok.Data;

@Data
// 재사용하게 제네릭타입으로 생성하자
// 클라이언트쪽으로 무엇을 보낼지 생각해서 변수를 잡자!
public class PageResponseDTO<E> {

    private List<E> dtoList;

    private long totalCount;

    private List<Integer> pageNums;

    private boolean prev, next;

    // 페이지 요청 정보를 저장하면서 응답 데이터와 함께 클라이언트에게 필요한 정보를 주기 위해서 필드 선언
    private PageRequestDTO requestDTO;

    private int page , size , start , end;

    // 데이터 , 전체 개수 , 페이지 요청 정보 초기화하는 생성자 생성
    public PageResponseDTO(List<E> dtoList , long totalCount , PageRequestDTO pageRequestDTO){
        this.dtoList=dtoList;
        this.totalCount=totalCount;
        this.requestDTO=pageRequestDTO;

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        

    }


    
}
