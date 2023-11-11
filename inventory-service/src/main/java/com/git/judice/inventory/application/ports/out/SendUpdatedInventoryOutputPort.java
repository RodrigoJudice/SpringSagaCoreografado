package com.git.judice.inventory.application.ports.out;

import com.git.judice.inventory.application.core.domain.Sale;
import com.git.judice.inventory.application.core.domain.enums.SaleEvent;

public interface SendUpdatedInventoryOutputPort {

    void send(Sale sale, SaleEvent event);

}
