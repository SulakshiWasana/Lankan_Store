package entity;

public class ItemDetails {
    private String itemCode;
    private String orderId;
    private int orderQTY;
    private double unitPrice;

    public ItemDetails() {
    }

    public ItemDetails(String itemCode, String orderId, int orderQTY, double unitPrice) {
        this.itemCode = itemCode;
        this.orderId = orderId;
        this.orderQTY = orderQTY;
        this.unitPrice = unitPrice;
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
