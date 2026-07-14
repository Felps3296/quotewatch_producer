package com.felipereis.quotewatch.producer.kafka;

import com.felipereis.quotewatch.producer.dto.CotacaoExternaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CotacaoProducerKafka {

    private static final String TOPICO = "cotacoes-coletadas";

    private final KafkaTemplate<String, CotacaoExternaDTO> kafkaTemplate;

    public void publicar(CotacaoExternaDTO cotacao) {
        log.info("Publicando cotacao {} no topico {}", cotacao.code(), TOPICO);
        kafkaTemplate.send(TOPICO, cotacao.code(), cotacao);
    }
}