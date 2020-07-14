package qtc.project.pos.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductListModel extends BaseResponseModel {

    private String id;
    private String id_code;
    private String name;
    private String description;
    private String image;
    private String barcode;
    private String qr_code;
    private String category_id;
    private String quantity_safetystock;
    private String category_name;
    private PackageInfoModel[] package_info;
    private StockOutModel[] stock_out;

    public PackageInfoModel[] getPackage_info() {
        return package_info;
    }
    public StockOutModel[] getStockOutModel(){return stock_out;}

    public void setPackage_info(PackageInfoModel[] package_info) {
        this.package_info = package_info;
    }

    public List<PackageInfoModel> getListDataProduct() {
        if (package_info == null) {
            return null;
        }
        else {
            List<PackageInfoModel> list = new ArrayList<>();
            list.addAll(Arrays.asList(package_info));
            return list;
        }
    }


    public void setStockOutModel(StockOutModel[] stock_out) {
        this.stock_out = stock_out;
    }

    public List<StockOutModel> getListDataStockOutModel() {
        if (stock_out == null) {
            return null;
        }
        else {
            List<StockOutModel> list = new ArrayList<>();
            list.addAll(Arrays.asList(stock_out));
            return list;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getQuantity_safetystock() {
        return quantity_safetystock;
    }

    public void setQuantity_safetystock(String quantity_safetystock) {
        this.quantity_safetystock = quantity_safetystock;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
