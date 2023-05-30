package dto;

public class ItemDetailsDTO {
    private String itemCode;
    private String orderId;
    private int orderQTY;
    private double unitPrice;

    public ItemDetailsDTO(String itemCode, String orderId, int orderQTY, double unitPrice) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setOrderQTY(orderQTY);
        this.setUnitPrice(unitPrice);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderQTY() {
        return orderQTY;
    }

    public void setOrderQTY(int orderQTY) {
        this.orderQTY = orderQTY;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
