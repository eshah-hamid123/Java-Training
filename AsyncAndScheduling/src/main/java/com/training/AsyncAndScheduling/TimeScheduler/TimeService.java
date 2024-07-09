package com.training.AsyncAndScheduling.TimeScheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TimeService {
    private static final Logger logger = LoggerFactory.getLogger(TimeService.class);

    @Scheduled(fixedDelay = 5000)
    public void reportCurrentTime() {
        try {
            Thread.sleep(5000); // Simulate a long-running task
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("The time is now {}", System.currentTimeMillis());
    }
}
