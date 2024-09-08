package com.example.tutorial_microsservico_kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tutorial_microsservico_kafka.data.PedidoData;

public interface PedidoRepository extends JpaRepository<PedidoData, Long> {
}