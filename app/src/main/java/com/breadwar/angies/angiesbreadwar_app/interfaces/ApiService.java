package com.breadwar.angies.angiesbreadwar_app.interfaces;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseMessage;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseMessage2;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    String API_BASE_URL = "https://laravel-gal05.cs50.io";

    @FormUrlEncoded
    @POST("/oauth/token")
            Call<ResponseMessage> login(@Field("username")      String username,
                                        @Field("password")      String password,
                                        @Field("grant_type")    String grant_type,
                                        @Field("client_id")     String client_id,
                                        @Field("client_secret") String client_secret );


    @POST("/api/profile")
            Call<ResponseUser> detail(@Header("authorization")  String authorization);


    @Multipart
    @POST("/api/reporteStore")
            Call<ResponseMessage> createReporte(
                                @Part("aula_id") RequestBody aula_id,
                                @Part("maquinaria_id")   RequestBody maquinaria_id,
                                @Part("observacion")    RequestBody observacion,
                                @Part("created_at")     RequestBody  created_at,
                                @Part(" update_at")     RequestBody  update_at,
                                @Part MultipartBody.Part imagen
                    );

    @Multipart
    @POST("/api/v1/reportesimage")
            Call<ResponseMessage2> createReporteImagen(
                    @Part("comentario") RequestBody comentario,
                    @Part MultipartBody.Part imagen);

}



