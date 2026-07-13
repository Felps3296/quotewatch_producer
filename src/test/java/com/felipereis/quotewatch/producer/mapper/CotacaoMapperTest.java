package com.felipereis.quotewatch.producer.mapper;

import com.felipereis.quotewatch.producer.dto.CotacaoExternaDTO;
import com.felipereis.quotewatch.producer.model.Cotacao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CotacaoMapperTest {

    //Acessar classe mapper e guarda na variavel mapper
    private final CotacaoMapper mapper = new CotacaoMapper();

    @Test
    void deveConverterCotacaoExternaParaEntidadeCorretamente() {
        // Arrange: monta um objeto fake
        CotacaoExternaDTO externa = new CotacaoExternaDTO(
                "USD",
                "BRL",
                "Dolar Americano/Real Brasileiro",
                "5.16149",
                "5.0994",
                "-0.0275",
                "-0.535658",
                "5.1064",
                "5.1164",
                "1783718838",
                "2026-07-10 18:27:18"
        );

        // Act: executa o metodo que estamos testando, guardando o retorno na variavel "resultado"
        Cotacao resultado = mapper.toEntity(externa);

        // Assert: confere se o resultado e exatamente o que esperamos
        // eu afirmo que ALGO é igual a VALOR_ESPERADO — se não for, o teste falha
        // assertThat( ALGO ).isEqualTo( VALOR_ESPERADO );
        assertThat(resultado.getMoeda()).isEqualTo("USD");
        assertThat(resultado.getValorCompra()).isEqualByComparingTo(new BigDecimal("5.1064"));
        assertThat(resultado.getValorVenda()).isEqualByComparingTo(new BigDecimal("5.1164"));
        assertThat(resultado.getVariacaoPct()).isEqualByComparingTo(new BigDecimal("-0.535658"));
        assertThat(resultado.getDataCotacao()).isEqualTo(LocalDateTime.of(2026, 7, 10, 18, 27, 18));
    }
}