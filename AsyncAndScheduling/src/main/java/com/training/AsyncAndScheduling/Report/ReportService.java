package com.training.AsyncAndScheduling.Report;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Async
    public void generateReport() {
        logger.info("Start generating report asynchronously");
        try {
            Thread.sleep(5000); // Simulate a long-running task
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Report generation completed");
    }
}
