package org.bs.x1.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name ="tbl_sample")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Sample {
    
    @Id
    private String keyCol;

    private String first;

    private String last;



    
}
