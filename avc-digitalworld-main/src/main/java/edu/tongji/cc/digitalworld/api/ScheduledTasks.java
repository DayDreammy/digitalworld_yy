package edu.tongji.cc.digitalworld.api;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


import edu.tongji.cc.digitalworld.service.AppService;
import edu.tongji.cc.digitalworld.service.MapService;
import edu.tongji.cc.digitalworld.service.WorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Resource(name = "avc")
    final AppService avc;

    public ScheduledTasks(AppService avc ) {
        this.avc = avc;

    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate = 100)
    public void reportCurrentTime() throws IOException {


//        log.info("time : {}", dateFormat.format(new Date()));
        for (int i = 0; i < WorldService.getAgentNumber(); i++) {
            log.info(avc.agents().get(i).toString());
        }

        for (int i = 0; i < WorldService.getAgentNumber(); i++) {
            avc.agents().get(i).invovle();
        }


    }
}
