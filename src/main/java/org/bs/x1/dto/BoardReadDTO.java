package org.bs.x1.dto;

import java.time.LocalDateTime;

// get 방식으로 선언 해줘야함 - interface

public interface BoardReadDTO {

    Long getBno();
    String getTitle();
    String getContent();
    String getWriter();
    LocalDateTime getRegDate();
    LocalDateTime getModDate();
    
}
