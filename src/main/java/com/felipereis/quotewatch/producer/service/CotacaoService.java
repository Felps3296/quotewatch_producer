package com.felipereis.quotewatch.producer.service;

import com.felipereis.quotewatch.producer.dto.CotacaoResponseDTO;
import com.felipereis.quotewatch.producer.model.Cotacao;
import com.felipereis.quotewatch.producer.repository.CotacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CotacaoService {

    private final CotacaoRepository cotacaoRepository;

    public CotacaoResponseDTO buscarUltimaCotacao(String moeda) {
        Cotacao cotacao = cotacaoRepository
                .findFirstByMoedaOrderByDataCotacaoDesc(moeda.toUpperCase())
                .orElseThrow(() -> new CotacaoNaoEncontradaException(moeda));

        return toDTO(cotacao);
    }

    public List<CotacaoResponseDTO> buscarHistorico(String moeda) {
        return cotacaoRepository
                .findByMoedaOrderByDataCotacaoDesc(moeda.toUpperCase())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private CotacaoResponseDTO toDTO(Cotacao cotacao) {
        return new CotacaoResponseDTO(
                cotacao.getMoeda(),
                cotacao.getValorCompra(),
                cotacao.getValorVenda(),
                cotacao.getVariacaoPct(),
                cotacao.getDataCotacao()
        );
    }
}