package view.tm;

public class ItemTM implements Comparable<ItemTM> {
    private String code;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer QtyOnHand;

    public ItemTM() {
    }

    public ItemTM(String code, String description, String packSize, Double unitPrice, Integer qtyOnHand) {
        this.code = code;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        QtyOnHand = qtyOnHand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", QtyOnHand=" + QtyOnHand +
                '}';
    }

    @Override
    public int compareTo(ItemTM o) {
        return code.compareTo(o.getCode());
    }
}
