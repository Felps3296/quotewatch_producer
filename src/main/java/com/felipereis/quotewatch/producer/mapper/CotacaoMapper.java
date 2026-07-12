package com.felipereis.quotewatch.producer.mapper;

import com.felipereis.quotewatch.producer.dto.CotacaoExternaDTO;
import com.felipereis.quotewatch.producer.model.Cotacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CotacaoMapper {

    private static final DateTimeFormatter FORMATO_DATA_AWESOMEAPI =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Cotacao toEntity(CotacaoExternaDTO externa) {
        Cotacao cotacao = new Cotacao();

        cotacao.setMoeda(externa.code());
        cotacao.setValorCompra(new BigDecimal(externa.bid()));
        cotacao.setValorVenda(new BigDecimal(externa.ask()));
        cotacao.setVariacaoPct(new BigDecimal(externa.pctChange()));
        cotacao.setDataCotacao(LocalDateTime.parse(externa.create_date(), FORMATO_DATA_AWESOMEAPI));
        cotacao.setDataColeta(LocalDateTime.now());

        return cotacao;
    }
}