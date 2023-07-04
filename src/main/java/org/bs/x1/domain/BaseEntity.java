package org.bs.x1.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass // 테이블로 생성하지 마! 라고 선언
@Getter

// 엔티티 생성 및 수정시에 자동으로 생성일자와 수정일자를 업데이트 하는 기능
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseEntity {
    
    // 생성된 날짜와 시간을 나타냄
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    // 마지막을 수정된 날짜와 시간을 나타냄
    @LastModifiedDate
    private LocalDateTime modDate;

}
