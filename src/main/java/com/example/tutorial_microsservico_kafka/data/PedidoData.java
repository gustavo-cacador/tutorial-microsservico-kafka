package com.example.tutorial_microsservico_kafka.data;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Faz com que eu n precise gerar Getters e Setter por conta do lombok Data
@AllArgsConstructor // Cria um construtor com todos (codigo, nomeProduto e valor)
@NoArgsConstructor  // Cria um construtor com nenhum

public class PedidoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String codigo;
    private String nomeProduto;
    private BigDecimal valor;


}