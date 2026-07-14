package com.felipereis.quotewatch.producer.controller;

import com.felipereis.quotewatch.producer.model.Cotacao;
import com.felipereis.quotewatch.producer.repository.CotacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CotacaoControllerTest {

    @LocalServerPort
    private int port;

    //ferramenta Spring pra fazer chamadas HTTP reais
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CotacaoRepository cotacaoRepository;

    @Test
    void deveRetornar200QuandoMoedaExiste() {
        // Arrange: salva uma cotacao de verdade no banco de teste
        Cotacao cotacao = new Cotacao();
        cotacao.setMoeda("EUR");
        cotacao.setValorCompra(new BigDecimal("6.10"));
        cotacao.setValorVenda(new BigDecimal("6.11"));
        cotacao.setVariacaoPct(new BigDecimal("0.2"));
        cotacao.setDataCotacao(LocalDateTime.now());
        cotacao.setDataColeta(LocalDateTime.now());
        cotacaoRepository.save(cotacao);

        // Act: faz uma chamada HTTP de verdade
        String url = "http://localhost:" + port + "/api/cotacoes/EUR";
        ResponseEntity<String> resposta = restTemplate.getForEntity(url, String.class);

        // Assert
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).contains("EUR");
    }

    @Test
    void deveRetornar404QuandoMoedaNaoExiste() {
        String url = "http://localhost:" + port + "/api/cotacoes/NAOEXISTE";
        ResponseEntity<String> resposta = restTemplate.getForEntity(url, String.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(resposta.getBody()).contains("NAOEXISTE");
    }
}