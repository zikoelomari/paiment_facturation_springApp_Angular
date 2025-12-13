package com.example.factureservice.batch;

import com.example.factureservice.model.Facture;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final OverdueInvoiceReader reader;
    private final OverdueInvoiceProcessor processor;
    private final OverdueInvoiceWriter writer;

    @Bean
    public Job processOverdueInvoicesJob() {
        return new JobBuilder("processOverdueInvoicesJob", jobRepository)
                .start(processOverdueInvoicesStep())
                .build();
    }

    @Bean
    public Step processOverdueInvoicesStep() {
        return new StepBuilder("processOverdueInvoicesStep", jobRepository)
                .<Facture, Facture>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
