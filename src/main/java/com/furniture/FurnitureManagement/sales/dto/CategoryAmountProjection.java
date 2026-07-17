package com.furniture.FurnitureManagement.sales.dto;

import java.math.BigDecimal;

/**
 * Spring Data projection for the native category-aggregation query in
 * ShowroomSaleRepository. Native queries can't use JPQL's "SELECT new ..."
 * constructor expressions, so this interface-based projection is used
 * instead and mapped to SalesGroupAmountResponse in the service layer.
 */
public interface CategoryAmountProjection {

    String getLabel();

    BigDecimal getAmount();
}