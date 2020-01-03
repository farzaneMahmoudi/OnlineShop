package com.example.appstore.network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

import com.example.appstore.model.CategoriesItem;
import com.example.appstore.model.ResponseModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface AppService {

    @GET("products/{id}")
    Call<ResponseModel> getProductById(@Path("id") String productId, @QueryMap Map<String, String> queries);

    @GET("products")
    Call<List<ResponseModel>> getAllProducts(@QueryMap Map<String, String> queries);

    @GET("products/categories")
    Call<List<CategoriesItem>> getAllCategories(@QueryMap Map<String, String> queries);

    @GET("products/categories/{id}")
    Call<CategoriesItem> getCategoryById(@Path("id") String categoryId, @QueryMap Map<String, String> queries);

    @GET("products/?") Call<List<ResponseModel>> getProductsOfSpecificCategory
            (@QueryMap Map<String, String> productQueries , @Query("category") String categoryId/*, @Query("name") String name*/);
}