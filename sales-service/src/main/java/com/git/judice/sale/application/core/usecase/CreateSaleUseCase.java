package com.git.judice.sale.application.core.usecase;

import com.git.judice.sale.application.core.domain.Sale;
import com.git.judice.sale.application.core.domain.enums.SaleEvent;
import com.git.judice.sale.application.core.domain.enums.SaleStatus;
import com.git.judice.sale.application.ports.in.CreateSaleInputPort;
import com.git.judice.sale.application.ports.out.SaveSaleOutputPort;
import com.git.judice.sale.application.ports.out.SendCreatedSaleOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateSaleUseCase implements CreateSaleInputPort {

    private final SaveSaleOutputPort saveSaleOutputPort;
    private final SendCreatedSaleOutputPort sendCreatedSaleOutputPort;

    @Override
    public void create(Sale sale) {
        try {
            sale.setStatus(SaleStatus.PENDING);
            var saleSaved = saveSaleOutputPort.save(sale);
            sendCreatedSaleOutputPort.send(saleSaved, SaleEvent.CREATED_SALE);
        } catch (Exception e) {
            log.error("Houve um erro = {}", e.getMessage());
        }
    }
}
