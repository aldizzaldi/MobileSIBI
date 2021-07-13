package eu.darken.myolib.exampleapp.data.api;

import com.google.gson.annotations.SerializedName;

public class DataSensorResponse {

    @SerializedName("values")
    String values;

    @SerializedName("message")
    String message;

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
