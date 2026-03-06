package org.mapping.onetomany.unidirectional.dto;

public class OrderResponse {
    private Long id;
    private String itemName;
    private Integer quantity;

    public OrderResponse() { }

    public OrderResponse(Long id, String itemName, Integer quantity) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}