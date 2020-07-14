package qtc.project.pos.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TongDoanhThuModel extends BaseResponseModel {


    private String title_filter;
    private DataChartModel[] data_chart;

    public String getTitle_filter() {
        return title_filter;
    }

    public void setTitle_filter(String title_filter) {
        this.title_filter = title_filter;
    }

    public DataChartModel[] getData_chart() {
        return data_chart;
    }

    public void setData_chart(DataChartModel[] data_chart) {
        this.data_chart = data_chart;
    }

    public List<DataChartModel> getListDataChartModel() {
        if (data_chart == null) {
            return null;
        }
        else {
            List<DataChartModel> list = new ArrayList<>();
            list.addAll(Arrays.asList(data_chart));
            return list;
        }
    }

}
