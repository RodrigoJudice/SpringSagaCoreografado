package com.git.judice.inventory.application.core.usecase;

import org.springframework.stereotype.Component;

import com.git.judice.inventory.application.core.domain.Sale;
import com.git.judice.inventory.application.core.domain.enums.SaleEvent;
import com.git.judice.inventory.application.ports.in.DebitInventoryInputPort;
import com.git.judice.inventory.application.ports.out.FindInventoryByProductIdOutputPort;
import com.git.judice.inventory.application.ports.out.SendUpdatedInventoryOutputPort;
import com.git.judice.inventory.application.ports.out.UpdateInventoryOutputPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DebitInventoryUseCase implements DebitInventoryInputPort {

  private final FindInventoryByProductIdOutputPort findInventoryByProductIdOutputPort;
  private final UpdateInventoryOutputPort updateInventoryOutputPort;
  private final SendUpdatedInventoryOutputPort sendUpdatedInventoryOutputPort;

  @Override
  public void debit(Sale sale) {
    var inventory = findInventoryByProductIdOutputPort.find(sale.getProductId()).get();
    inventory.debitQuantity(sale.getQuantity());
    updateInventoryOutputPort.update(inventory);
    sendUpdatedInventoryOutputPort.send(sale, SaleEvent.UPDATED_INVENTORY);

  }

}
