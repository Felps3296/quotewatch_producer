package com.felipereis.quotewatch.producer.exception;

public class CotacaoNaoEncontradaException extends RuntimeException {

    public CotacaoNaoEncontradaException(String moeda) {
        super("Nenhuma cotacao encontrada para a moeda: " + moeda);
    }
}