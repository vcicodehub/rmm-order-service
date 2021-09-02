package com.signet.model;

import java.math.BigDecimal;

public class SupplyCategory extends ModelObject {
    private String category;
    private BigDecimal cost;
    private String description;
    private boolean bulkItem;

    public String  getCategory() { return category; }
    public void    setCategory(String category) { this.category = category; }
    public BigDecimal getCost() { return cost; }
    public void    setCost(BigDecimal cost) { this.cost = cost; }
    public String  getDescription() { return description; }
    public void    setDescription(String description) { this.description = description; }
    public boolean isBulkItem() { return bulkItem; }
    public void    setBulkItem(boolean bulkItem) { this.bulkItem = bulkItem; }
    
}
