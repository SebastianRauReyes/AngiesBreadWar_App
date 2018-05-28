package com.breadwar.angies.angiesbreadwar_app.interfaces;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseMessage;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseMessage2;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseUser;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    String API_BASE_URL = "https://pi4-v2-larav-pass-excel-api-gal05.c9users.io";

    @FormUrlEncoded
    @POST("/api/login")
            Call<ResponseMessage> login(@Field("username")      String username,
                                        @Field("password")      String password,
                                        @Field("grant_type")    String grant_type,
                                        @Field("client_id")     String client_id,
                                        @Field("client_secret") String client_secret );

    @Multipart
    @POST("/api/reporte")
            Call<ResponseMessage2> createReporte(
                                @Part("user_id") RequestBody user_id,
                                @Part("maquinaria_id")   RequestBody maquinaria_id,
                                @Part("aula_id") RequestBody aula_id,
                                @Part("observacion")    RequestBody observacion,
                                @Part MultipartBody.Part imagen
                    );


}



