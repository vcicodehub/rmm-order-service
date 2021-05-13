package com.signet.model;

import java.math.BigDecimal;

public class Product extends ModelObject {
    private static final long serialVersionUID = 1L;

    private String key;
    private String name;
    private String description;
    private String category;
    private BigDecimal unitPrice;

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

}
