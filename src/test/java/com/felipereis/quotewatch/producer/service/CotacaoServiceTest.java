package com.felipereis.quotewatch.producer.service;

import com.felipereis.quotewatch.producer.exception.CotacaoNaoEncontradaException;
import com.felipereis.quotewatch.producer.model.Cotacao;
import com.felipereis.quotewatch.producer.repository.CotacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CotacaoServiceTest {

    @Mock
    private CotacaoRepository cotacaoRepository;

    private CotacaoService cotacaoService;

    @Test
    void deveRetornarUltimaCotacaoQuandoMoedaExiste() {
        // Arrange
        // os parametros tem que ser passado como null, pq o java não deixa pular parametros de um construtor
        cotacaoService = new CotacaoService(cotacaoRepository, null, null);

        Cotacao cotacaoSalva = new Cotacao();
        cotacaoSalva.setId(1L);
        cotacaoSalva.setMoeda("USD");
        cotacaoSalva.setValorCompra(new BigDecimal("5.10"));
        cotacaoSalva.setValorVenda(new BigDecimal("5.11"));
        cotacaoSalva.setVariacaoPct(new BigDecimal("0.5"));
        cotacaoSalva.setDataCotacao(LocalDateTime.now());
        cotacaoSalva.setDataColeta(LocalDateTime.now());

        //Quando for chamado o metodo do repository pedindo USD finge que encontrou e retorna a variavel cotacaosalva
        when(cotacaoRepository.findFirstByMoedaOrderByDataCotacaoDesc("USD"))
                .thenReturn(Optional.of(cotacaoSalva));

        // Act
        var resultado = cotacaoService.buscarUltimaCotacao("USD");

        // Assert
        // Dado que o banco (fake) devolveu X, o meu Service consegue transformar isso em um DTO Y correto
        assertThat(resultado.moeda()).isEqualTo("USD");
        assertThat(resultado.valorCompra()).isEqualByComparingTo(new BigDecimal("5.10"));
    }

    @Test
    void deveLancarExcecaoQuandoMoedaNaoExiste() {
        // Arrange
        cotacaoService = new CotacaoService(cotacaoRepository, null, null);

        //conferindo se tratamento de erro está retornando
        when(cotacaoRepository.findFirstByMoedaOrderByDataCotacaoDesc("XYZ"))
                .thenReturn(Optional.empty());

        // Act + Assert
        //confirmar se isso vai lançar uma exceção
        //Quando o banco não encontra a moeda, meu Service lança a exceção certa, com a mensagem certa — em vez de, por exemplo, devolver null
        assertThatThrownBy(() -> cotacaoService.buscarUltimaCotacao("XYZ"))
                .isInstanceOf(CotacaoNaoEncontradaException.class)
                .hasMessageContaining("XYZ");
    }
}