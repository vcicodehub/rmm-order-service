package com.signet.model;

public class SupplyCategory extends ModelObject {
    private String category;
    private String description;
    private boolean bulkItem;

    public String  getCategory() { return category; }
    public void    setCategory(String category) { this.category = category; }
    public String  getDescription() { return description; }
    public void    setDescription(String description) { this.description = description; }
    public boolean isBulkItem() { return bulkItem; }
    public void    setBulkItem(boolean bulkItem) { this.bulkItem = bulkItem; }
    
}
