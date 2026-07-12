package com.felipereis.quotewatch.producer.controller;

import com.felipereis.quotewatch.producer.dto.CotacaoResponseDTO;
import com.felipereis.quotewatch.producer.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cotacoes")
@RequiredArgsConstructor
public class CotacaoController {

    private final CotacaoService cotacaoService;

    @GetMapping("/{moeda}")
    public CotacaoResponseDTO buscarUltima(@PathVariable String moeda) {
        return cotacaoService.buscarUltimaCotacao(moeda);
    }

    @GetMapping("/{moeda}/historico")
    public List<CotacaoResponseDTO> buscarHistorico(@PathVariable String moeda) {
        return cotacaoService.buscarHistorico(moeda);
    }
}