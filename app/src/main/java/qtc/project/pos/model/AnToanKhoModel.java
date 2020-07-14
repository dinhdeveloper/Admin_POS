package qtc.project.pos.model;

public class AnToanKhoModel extends BaseResponseModel {



    private String product_id;
    private String product_name;
    private String total_quantity_storage;
    private String quantity_safetystock;

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

    public String getTotal_quantity_storage() {
        return total_quantity_storage;
    }

    public void setTotal_quantity_storage(String total_quantity_storage) {
        this.total_quantity_storage = total_quantity_storage;
    }

    public String getQuantity_safetystock() {
        return quantity_safetystock;
    }

    public void setQuantity_safetystock(String quantity_safetystock) {
        this.quantity_safetystock = quantity_safetystock;
    }
}
