package com.felipereis.quotewatch.producer.client;

import com.felipereis.quotewatch.producer.dto.CotacaoExternaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class AwesomeApiClient {

    private final RestClient restClient;

    public AwesomeApiClient(@Value("${awesomeapi.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public CotacaoExternaDTO buscarCotacao(String moeda) {
        String par = moeda.toUpperCase() + "-BRL";

        Map<String, CotacaoExternaDTO> resposta = restClient.get()
                .uri("/last/{par}", par)
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<Map<String, CotacaoExternaDTO>>() {});

        String chave = moeda.toUpperCase() + "BRL";
        return resposta.get(chave);
    }
}