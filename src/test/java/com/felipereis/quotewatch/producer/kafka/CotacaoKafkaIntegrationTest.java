package com.felipereis.quotewatch.producer.kafka;

import com.felipereis.quotewatch.producer.dto.CotacaoExternaDTO;
import com.felipereis.quotewatch.producer.repository.CotacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "cotacoes-coletadas")
class CotacaoKafkaIntegrationTest {

    @Autowired
    private CotacaoProducerKafka producerKafka;

    @Autowired
    private CotacaoRepository cotacaoRepository;

    @Test
    void devePublicarEConsumirCotacaoDeVerdade() {
        // Arrange: monta uma cotacao fake, igual a AwesomeAPI mandaria
        CotacaoExternaDTO cotacao = new CotacaoExternaDTO(
                "GBP", "BRL", "Libra Esterlina/Real Brasileiro",
                "7.10", "7.00", "0.05", "0.7",
                "7.05", "7.06", "1783718838",
                "2026-07-14 10:00:00"
        );

        // Act: publica de verdade no Kafka embarcado
        producerKafka.publicar(cotacao);

        // Assert: espera ate 5 segundos o Consumer processar eassincrono
        // fica conferindo se tem dado a cada milessegundos
        await().atMost(Duration.ofSeconds(5)).untilAsserted(() -> {
            var salva = cotacaoRepository.findFirstByMoedaOrderByDataCotacaoDesc("GBP");
            assertThat(salva).isPresent();
            assertThat(salva.get().getMoeda()).isEqualTo("GBP");
        });
    }
}