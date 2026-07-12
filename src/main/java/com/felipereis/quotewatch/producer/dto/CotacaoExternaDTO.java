package com.felipereis.quotewatch.producer.dto;

public record CotacaoExternaDTO(
        String code,
        String codein,
        String name,
        String high,
        String low,
        String varBid,
        String pctChange,
        String bid,
        String ask,
        String timestamp,
        String create_date
) {
}