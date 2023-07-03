package org.bs.x1.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.bs.x1.domain.Sample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleRepositoryTests {

    @Autowired
    private SampleRepository sampleRepository;

    @Test
    public void test1(){

        log.info("--------------");
        sampleRepository.getClass().getName();


    }

    @Test
    public void insertTest(){

        IntStream.rangeClosed(0, 100).forEach(i -> {
            Sample obj = Sample.builder()
            .keyCol("u" + i)
            .first("fisrt")
            .last("last")
            .build(); 

            sampleRepository.save(obj);


        });
    }
    
    @Test
    public void readTest(){
        
        String keyCol = "u10";

        Optional<Sample> result = sampleRepository.findById(keyCol);

        Sample obj = result.orElseThrow();

        log.info(obj);

        
    }

    @Test
    public void deleteTest(){

        String keyCol = "u100";

        sampleRepository.deleteById(keyCol);

    }

    @Test
    public void Paging(){

        Pageable pageable = PageRequest.of(0, 10, 
        Sort.by("keyCol").descending());

        Page<Sample> result = sampleRepository.findAll(pageable);

        log.info(result.getTotalElements());

        log.info(result.getTotalPages());
    
        result.getContent().forEach(obj -> {
            log.info(obj);
        });


    }
    
}
