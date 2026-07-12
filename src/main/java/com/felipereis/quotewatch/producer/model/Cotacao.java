package com.felipereis.quotewatch.producer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cotacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String moeda;

    @Column(name = "valor_compra", nullable = false, precision = 18, scale = 6)
    private BigDecimal valorCompra;

    @Column(name = "valor_venda", nullable = false, precision = 18, scale = 6)
    private BigDecimal valorVenda;

    @Column(name = "variacao_pct", precision = 8, scale = 4)
    private BigDecimal variacaoPct;

    @Column(name = "data_cotacao", nullable = false)
    private LocalDateTime dataCotacao;

    @Column(name = "data_coleta", nullable = false)
    private LocalDateTime dataColeta;
}