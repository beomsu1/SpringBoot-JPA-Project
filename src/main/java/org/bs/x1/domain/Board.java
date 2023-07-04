package org.bs.x1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="t_board")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoIncrement
    private Long bno;

    // 길이 200 , not null
    @Column(length = 200 , nullable = false)
    private String title;

    @Column(length = 1000 , nullable = false)
    private String content;

    @Column(length = 50 , nullable = false)
    private String writer;

    // 함수에 전달되는 값으로(함수의 실행결과) title을 받고 Board에 있는 title에 그 값을 저장한다!
    public void changeTitle(String title){
        this.title=title;
    }
    
}
