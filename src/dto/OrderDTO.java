package dto;

import java.util.ArrayList;

public class OrderDTO {
    private String OrderID;
    private String CID;
    private String OrderDate;
    private double Cost;
    private ArrayList<ItemDetailsDTO> items;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, String CID, String orderDate, double cost, ArrayList<ItemDetailsDTO> items) {
        OrderID = orderID;
        this.CID = CID;
        OrderDate = orderDate;
        Cost = cost;
        this.items = items;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public ArrayList<ItemDetailsDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetailsDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderID='" + OrderID + '\'' +
                ", CID='" + CID + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                ", Cost=" + Cost +
                ", items=" + items +
                '}';
    }
}
