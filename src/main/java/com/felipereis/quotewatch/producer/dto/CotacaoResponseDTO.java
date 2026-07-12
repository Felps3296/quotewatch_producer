package com.felipereis.quotewatch.producer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CotacaoResponseDTO(
        String moeda,
        BigDecimal valorCompra,
        BigDecimal valorVenda,
        BigDecimal variacaoPct,
        LocalDateTime dataCotacao
) {
}