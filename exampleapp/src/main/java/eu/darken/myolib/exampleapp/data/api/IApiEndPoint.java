package eu.darken.myolib.exampleapp.data.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IApiEndPoint {

    @FormUrlEncoded
    @POST("login")
    Call<DataSensorResponse> login(
            @Field("etst") String emailUser,
            @Field("password") String passwordUser
    );
}
