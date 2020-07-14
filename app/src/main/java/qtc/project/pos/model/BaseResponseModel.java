package qtc.project.pos.model;

import javax.annotation.Nullable;

import b.laixuantam.myaarlibrary.api.BaseApiResponse;

/**
 * Created by laixuantam on 4/2/18.
 */

public class BaseResponseModel<T> implements BaseApiResponse {
    @Nullable
    private String success;

    @Nullable
    private String message;

    @Nullable
    private String total_page;

    @Nullable
    private String error_code;

    @Nullable
    private T[] data;

    @Nullable
    public String getSuccess() {
        return success;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public T[] getData() {
        return data;
    }

    @Nullable
    public String getTotal_page() {
        return total_page;
    }

    @Nullable
    public String getError_code() {
        return error_code;
    }
}
