package ca.on.sl.comp208.assignment3realortelli;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ray on 2017-04-06.
 */

/**
 * POJO Class for MemeByPopular
 */
public class MemeByPopular {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("warning")
    @Expose
    private Object warning;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    @Override
    public String toString() {
        return "MemeByPopular{" +
                "success=" + success +
                ", warning=" + warning +
                ", result=" + result +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getWarning() {
        return warning;
    }

    public void setWarning(Object warning) {
        this.warning = warning;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}

