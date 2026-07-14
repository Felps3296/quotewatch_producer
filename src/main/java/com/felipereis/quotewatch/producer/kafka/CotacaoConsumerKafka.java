package com.felipereis.quotewatch.producer.kafka;

import com.felipereis.quotewatch.producer.dto.CotacaoExternaDTO;
import com.felipereis.quotewatch.producer.mapper.CotacaoMapper;
import com.felipereis.quotewatch.producer.model.Cotacao;
import com.felipereis.quotewatch.producer.repository.CotacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CotacaoConsumerKafka {

    private final CotacaoMapper cotacaoMapper;
    private final CotacaoRepository cotacaoRepository;

    @KafkaListener(topics = "cotacoes-coletadas", groupId = "quotewatch-consumer")
    public void escutar(CotacaoExternaDTO externa) {
        log.info("Mensagem recebida do Kafka: {}", externa.code());

        Cotacao cotacao = cotacaoMapper.toEntity(externa);
        cotacaoRepository.save(cotacao);

        log.info("Cotacao {} salva no banco via Kafka", cotacao.getMoeda());
    }
}