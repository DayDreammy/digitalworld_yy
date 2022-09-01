package edu.tongji.cc.digitalworld;

import edu.tongji.cc.digitalworld.api.ScheduledTasks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.UnsupportedEncodingException;

/**
 * Main Application class.
 * This is the entrance of the whole application.
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
@SpringBootApplication
@EnableScheduling
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

    }

}
