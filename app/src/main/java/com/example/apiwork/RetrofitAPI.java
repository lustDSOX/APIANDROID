package com.example.apiwork;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("animals")
    Call<Animal> createPost(@Body Animal dataModal);

    @PUT("animals/")
    Call<Animal> updateData(@Query("Id") int id, @Body Animal dataModal);

    @DELETE("animals/")
    Call<Animal> deleteData(@Query("Id") int id);
}