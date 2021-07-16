package eu.darken.myolib.exampleapp.data.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IApiEndPoint {



    @FormUrlEncoded
    @POST("myo")
    Call<DataSensorResponse> myo(
            @Header("Authorization") String token,
            @Field("test") String dataSensor
    );
}
