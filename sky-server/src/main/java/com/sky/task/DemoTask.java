package com.sky.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DemoTask {


    //@Scheduled(cron = "0/3 * * * * *")
    public  void printTask(){

        log.info("Executed Scheduled Tasks every 3 seconds : {}", LocalDateTime.now());
    }
}
