package com.signet.model;

import java.math.BigDecimal;

public class Product extends ModelObject {
    private static final long serialVersionUID = 1L;

    private String key;
    private String type;
    private String description;
    private BigDecimal cost;
    private String status;
    private String name;
    private String quality;
    private String cut;
    private String size;
    private String shape;
    private String sizeCarat;
    private String color;
    private String sterlingQuality;
    private SupplyCategory supplyCategory;

    public String getKey() { return key; }
    public void   setKey(String key) { this.key = key; }
    public String getType() { return type; }
    public void   setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void   setDescription(String description) { this.description = description; }
    public BigDecimal getCost() { return cost; }
    public void   setCost(BigDecimal cost) { this.cost = cost; }
    public String getStatus() { return status; }
    public void   setStatus(String status) { this.status = status; }
    public String getName() { return name; }
    public void   setName(String name) { this.name = name; }
    public String getQuality() { return quality; }
    public void   setQuality(String quality) { this.quality = quality; }
    public String getCut() { return cut; }
    public void   setCut(String cut) { this.cut = cut; }
    public String getSize() { return size; }
    public void   setSize(String size) { this.size = size; }
    public String getShape() { return shape; }
    public void   setShape(String shape) { this.shape = shape; }
    public String getSizeCarat() { return sizeCarat; }
    public void   setSizeCarat(String sizeCarat) { this.sizeCarat = sizeCarat; }
    public String getColor() { return color; }
    public void   setColor(String color) { this.color = color; }
    public String getSterlingQuality() { return sterlingQuality; }
    public void   setSterlingQuality(String sterlingQuality) { this.sterlingQuality = sterlingQuality; }
    public SupplyCategory getSupplyCategory() { return supplyCategory; }
    public void setSupplyCategory(SupplyCategory supplyCategory) { this.supplyCategory = supplyCategory; }    

    public String getSupplyCategoryID() { return supplyCategory == null ? null : supplyCategory.getID(); }
    public void setSupplyCategoryID(String value) {}
}
