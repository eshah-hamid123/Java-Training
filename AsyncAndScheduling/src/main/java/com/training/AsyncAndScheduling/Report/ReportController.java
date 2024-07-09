package com.training.AsyncAndScheduling.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/generate-report")
    public String generateReport() {
        reportService.generateReport();
        return "Report generation function returned";
    }
}
