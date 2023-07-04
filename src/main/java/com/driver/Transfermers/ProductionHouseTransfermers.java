package com.driver.Transfermers;

import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;

public class ProductionHouseTransfermers {
    public static ProductionHouse convertProductionHouseDtoToEntity(ProductionHouseEntryDto productionHouseEntryDto) {
        ProductionHouse productionHouse = new ProductionHouse();
        productionHouse.setName(productionHouseEntryDto.getName());
        return productionHouse;
    }
}
