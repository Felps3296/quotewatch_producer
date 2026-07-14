package com.felipereis.quotewatch.producer.scheduler;

import com.felipereis.quotewatch.producer.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CotacaoScheduler {

    private final CotacaoService cotacaoService;

    @Scheduled(fixedRate = 60000)
    public void coletarCotacaoUsd() {
        log.info("Iniciando coleta de cotacao USD");
        cotacaoService.publicarCotacao("USD");
        log.info("Cotacao USD publicada no Kafka com sucesso");
    }
}