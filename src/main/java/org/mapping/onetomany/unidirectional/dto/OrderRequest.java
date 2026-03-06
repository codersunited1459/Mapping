package org.mapping.onetomany.unidirectional.dto;

public class OrderRequest {
    private String itemName;
    private Integer quantity;

    public OrderRequest() { }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}