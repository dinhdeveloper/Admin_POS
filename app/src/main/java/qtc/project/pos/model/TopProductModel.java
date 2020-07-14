package qtc.project.pos.model;

public class TopProductModel extends BaseResponseModel {


    private String product_id;
    private String product_name;
    private String total_payment_item_order;
    private String total_quantity_item_order;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getTotal_payment_item_order() {
        return total_payment_item_order;
    }

    public void setTotal_payment_item_order(String total_payment_item_order) {
        this.total_payment_item_order = total_payment_item_order;
    }

    public String getTotal_quantity_item_order() {
        return total_quantity_item_order;
    }

    public void setTotal_quantity_item_order(String total_quantity_item_order) {
        this.total_quantity_item_order = total_quantity_item_order;
    }
}
