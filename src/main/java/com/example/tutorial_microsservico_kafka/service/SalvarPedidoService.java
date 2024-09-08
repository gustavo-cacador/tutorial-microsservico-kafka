package com.example.tutorial_microsservico_kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.tutorial_microsservico_kafka.data.PedidoData;
import com.example.tutorial_microsservico_kafka.repository.PedidoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalvarPedidoService {

    private final PedidoRepository pedidoRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public SalvarPedidoService(PedidoRepository pedidoRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.pedidoRepository = pedidoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "SalvarPedido", groupId = "MicrosservicoSalvaPedido")
    private void executar(ConsumerRecord<String, String> record) {
        
            log.info("Chave = {}", record.key());
            log.info("Cabecalho = {}", record.headers());
            log.info("Particao = {}", record.partition());

            String strdados = record.value();
            // Convertendo para Objeto
            ObjectMapper mapper = new ObjectMapper();
            PedidoData pedido = null;  

            try {
                pedido = mapper.readValue(strdados, PedidoData.class);
            } catch (JsonProcessingException ex) {
                log.error("Falha ao converter evento [dado={}}]", strdados, ex);
                return;
    }   

    log.info("Evento Recebido = {}", pedido);

    // Gravar no Banco de dados
    gravar(pedido);

    // Retornar para a fila que o pedido foi salvo
    kafkaTemplate.send("PedidoSalvo", "Pedido ID " + pedido.getId() + " salvo com sucesso");
}

    private void gravar(PedidoData pedido) {
        // Gravar no banco de dados
        pedidoRepository.save(pedido);
        log.info("Pedido salvo no Banco de Dados: {}", pedido);
    }
}
