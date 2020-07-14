package qtc.project.pos.model;

public class ReportXuatNhapKhoModel extends BaseResponseModel{


    private String product_id;
    private String product_name;
    private String stock_out;
    private String stock;
    private String stock_in;

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

    public String getStock_out() {
        return stock_out;
    }

    public void setStock_out(String stock_out) {
        this.stock_out = stock_out;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStock_in() {
        return stock_in;
    }

    public void setStock_in(String stock_in) {
        this.stock_in = stock_in;
    }
}
