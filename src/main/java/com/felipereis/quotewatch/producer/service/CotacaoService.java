package com.felipereis.quotewatch.producer.service;

import com.felipereis.quotewatch.producer.dto.CotacaoExternaDTO;
import com.felipereis.quotewatch.producer.dto.CotacaoResponseDTO;
import com.felipereis.quotewatch.producer.exception.CotacaoNaoEncontradaException;
import com.felipereis.quotewatch.producer.model.Cotacao;
import com.felipereis.quotewatch.producer.repository.CotacaoRepository;
import com.felipereis.quotewatch.producer.client.AwesomeApiClient;
import com.felipereis.quotewatch.producer.mapper.CotacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CotacaoService {

    private final CotacaoRepository cotacaoRepository;
    private final AwesomeApiClient awesomeApiClient;
    private final CotacaoMapper cotacaoMapper;

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

    public CotacaoResponseDTO salvarCotacao(String moeda) {
        CotacaoExternaDTO externa = awesomeApiClient.buscarCotacao(moeda);
        Cotacao cotacao = cotacaoMapper.toEntity(externa);
        Cotacao salva = cotacaoRepository.save(cotacao);

        return toDTO(salva);
    }
}