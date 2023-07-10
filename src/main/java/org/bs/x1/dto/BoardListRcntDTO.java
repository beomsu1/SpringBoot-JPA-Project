package org.bs.x1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// bno , title , writer , replyCount

// 보드에 댓글개수가 몇개 달린지 확인하는 DTO
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardListRcntDTO {

    private Long bno;
    private String title;
    private String writer;
    private long replyCount;
    
}
