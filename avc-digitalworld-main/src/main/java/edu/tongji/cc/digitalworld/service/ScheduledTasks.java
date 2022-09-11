package edu.tongji.cc.digitalworld.service;

import java.io.*;
import java.text.SimpleDateFormat;


import edu.tongji.cc.digitalworld.entity.Agent;
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

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() throws IOException {

        int world_type=avc.Status();
        if(world_type == 1){//世界状态为运行中

            avc.tictoc();//滴答滴答

            int agent_num=avc.agents().find().size();
//        log.info("time : {}", dateFormat.format(new Date()));
            for (int i = 0; i < agent_num; i++) {
                log.info(avc.agents().get(i).toString());
            }

            for (int i = 0; i < agent_num; i++) {
                Agent agent=avc.agents().get(i);
                if(agent.getType() == 0){//digital car 需要involve
                    agent.involve();
                }else if(agent.getType() == 1 ||agent.getType() == 2  ){//app car 需要update
                    agent.appUpdate();
                }
            }
        }else if(world_type == 0){
            log.info("The World did not start");
        }else if(world_type == 2){
            log.info("The world is suspended");
        }




    }
}
