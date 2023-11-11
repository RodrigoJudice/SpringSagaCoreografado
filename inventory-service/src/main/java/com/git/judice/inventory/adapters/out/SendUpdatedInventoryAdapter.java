package com.git.judice.inventory.adapters.out;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.git.judice.inventory.adapters.out.message.SaleMessage;
import com.git.judice.inventory.application.core.domain.Sale;
import com.git.judice.inventory.application.core.domain.enums.SaleEvent;
import com.git.judice.inventory.application.ports.out.SendUpdatedInventoryOutputPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendUpdatedInventoryAdapter implements SendUpdatedInventoryOutputPort {

  private final KafkaTemplate<String, SaleMessage> kafkaTemplate;

  @Override
  public void send(Sale sale, SaleEvent event) {
    var saleMessage = new SaleMessage(sale, event);
    kafkaTemplate.send("tr-saga-sale", saleMessage);
  }

}
