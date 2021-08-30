package com.signet.model.order;

import java.math.BigDecimal;

import com.signet.model.ModelObject;
import com.signet.model.Product;

public class OrderLineItem extends ModelObject {
    private static final long serialVersionUID = 1L;
    
    private String orderID;
    private int lineNumber;
    private String productKey;
    private String supplyID;
    private String description;
    private String unitOfMeasure;
    private BigDecimal price;
    private int quantityOrdered;
    private int quantityDelivered;
    private int quantityInvoiced;
    private Product product;

    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }
    public int getLineNumber() { return lineNumber; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
    public String getProductKey() { return productKey; }
    public void setProductKey(String productKey) { this.productKey = productKey; }
    public String getSupplyID() { return supplyID; }
    public void setSupplyID(String supplyID) { this.supplyID = supplyID; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUnitOfMeasure() { return unitOfMeasure; }
    public void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getQuantityOrdered() { return quantityOrdered; }
    public void setQuantityOrdered(int quantityOrdered) { this.quantityOrdered = quantityOrdered; }
    public int getQuantityDelivered() { return quantityDelivered; }
    public void setQuantityDelivered(int quantityDelivered) { this.quantityDelivered = quantityDelivered; }
    public int getQuantityInvoiced() { return quantityInvoiced; }
    public void setQuantityInvoiced(int quantityInvoiced) { this.quantityInvoiced = quantityInvoiced; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public String getProductID() {
      return product == null ? null : product.getID();
    }
    public void setProductID(String value) {}
    
}
