package com.git.judice.sale.adapters.out;

import com.git.judice.sale.adapters.out.message.SaleMessage;
import com.git.judice.sale.application.core.domain.Sale;
import com.git.judice.sale.application.core.domain.enums.SaleEvent;
import com.git.judice.sale.application.ports.out.SendCreatedSaleOutputPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendCreatedSaleAdapter implements SendCreatedSaleOutputPort {

    private final KafkaTemplate<String, SaleMessage> kafkaTemplate;

    @Override
    public void send(Sale sale, SaleEvent event) {
        try {
            var saleMessage = new SaleMessage(sale, event);
            kafkaTemplate.send("tp-saga-sale", saleMessage);
        } catch (Exception e) {
            log.error("Houve um erro = {}", e.getMessage());
        }

    }
}
