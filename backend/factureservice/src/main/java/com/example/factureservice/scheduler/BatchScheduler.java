package com.example.factureservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job processOverdueInvoicesJob;

    @Scheduled(cron = "${batch.schedule.cron:0 0 2 * * ?}") // Every day at 2 AM
    public void runOverdueInvoicesJob() {
        try {
            log.info("Starting overdue invoices batch job");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(processOverdueInvoicesJob, jobParameters);
            log.info("Completed overdue invoices batch job");
        } catch (Exception e) {
            log.error("Error running overdue invoices batch job", e);
        }
    }
}

