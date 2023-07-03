package org.bs.x1.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;
    
}
