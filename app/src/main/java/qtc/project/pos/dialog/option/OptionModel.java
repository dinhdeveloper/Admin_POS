package qtc.project.pos.dialog.option;

import java.io.Serializable;

public class OptionModel<T> implements Serializable {

    private String title;

    private String image;

    private boolean isSelected;

    private OptionType optionType;

    private T dtaCustom;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public OptionType getOptionType() {
        return optionType != null ? optionType : OptionType.DEFAULT;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    public T getDtaCustom() {
        return dtaCustom;
    }

    public void setDtaCustom(T dtaCustom) {
        this.dtaCustom = dtaCustom;
    }

    public enum OptionType {
        DEFAULT,
        CUSTOM_TYPE_PRODUCT,
        CUSTOM_TYPE_EMPLOYEE,
        CUSTOM_TYPE_CUSTOMER,
        CUSTOM_TYPE_TASK
    }
}
