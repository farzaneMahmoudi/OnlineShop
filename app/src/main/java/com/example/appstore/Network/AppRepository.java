package com.example.appstore.Network;

import android.util.Log;

import com.example.appstore.Model.CategoriesItem;
import com.example.appstore.Model.ResponseModel;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRepository<T> {

    public static final String CONSUMER_KEY_VALUE = "ck_1fdf599207f3d6e688240bcdc2e1dda77d4cf109";
    public static final String CONSUMER_SECRET_VALUE = "cs_e60bcfe78da97ee2c3f483470e7114ea406ab07d";
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String TAG = "AppRepository";
    private Map<String, String> mQueries = new HashMap<>();

    private Retrofit mRetrofit;
    private AppService mAppService;

    private static AppRepoCallBack mAppRepoCallBack;

    private static AppRepository instance;

    public static AppRepository getInstance(AppRepoCallBack appRepoCallBack) {
        if (appRepoCallBack instanceof AppRepoCallBack)
            mAppRepoCallBack = (AppRepoCallBack) appRepoCallBack;
        if (instance == null)
            instance = new AppRepository();
        return instance;
    }

    private AppRepository() {
        mQueries = new HashMap<>();
        mQueries.put("consumer_key", CONSUMER_KEY_VALUE);
        mQueries.put("consumer_secret", CONSUMER_SECRET_VALUE);

        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        mAppService = mRetrofit.create(AppService.class);
    }

    public List<ResponseModel> getAllProducts() throws IOException {
        return mAppService.getAllProducts(mQueries).execute().body();
    }

    public void getSpecificProducts(int id) throws IOException {
        mAppService.getProductById(String.valueOf(id), mQueries).enqueue(getResponseModelCallback());
    }

    public void getSpecificCategory(int id) throws IOException {
        mAppService.getProductById(String.valueOf(id), mQueries).enqueue(getResponseModelCallback());
    }

    public void getBestProducts() throws IOException {
        mQueries.put("orderby", "rating");
        mAppService.getAllProducts(mQueries).enqueue(getResponseModelCallback(2));
    }

    public void getMostVisitedProducts() throws IOException {
        mQueries.put("orderby", "popularity");
        mAppService.getAllProducts(mQueries).enqueue(getResponseModelCallback(1));
    }

    public void getRecentProducts() throws IOException {
        mQueries.put("orderby", "date");
        mAppService.getAllProducts(mQueries).enqueue(getResponseModelCallback(0));
    }


    public void getCategories() throws IOException {
        mQueries.remove("orderby");
        mAppService.getAllCategories(mQueries).enqueue(getListCategoryCallback());
    }

    public void getProSubCatList(int id/*,String name*/) throws IOException {
        mAppService.getProductsOfSpecificCategory(mQueries,String.valueOf(id)/*,name*/).enqueue(getProSubCatListCategoryCallback());
    }

    private Callback<List<CategoriesItem>> getListCategoryCallback() {
        return new Callback<List<CategoriesItem>>() {
            @Override
            public void onResponse(Call<List<CategoriesItem>> call, Response<List<CategoriesItem>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    List<CategoriesItem> categoriesList = response.body();
                    mAppRepoCallBack.onListCategoryResponse(categoriesList);
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }

    private Callback<ResponseModel> getResponseModelCallback() {
        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    ResponseModel responseModel = response.body();
                    mAppRepoCallBack.onResponseProduct(responseModel);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };

    }

    private Callback<List<ResponseModel>> getProSubCatListCategoryCallback() {
        return new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    List<ResponseModel> proSubCatList  = response.body();
                    mAppRepoCallBack.onProListSubCategoryResponse(proSubCatList);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };

    }

    private Callback<List<ResponseModel>> getResponseModelCallback(final int i) {
        return new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");

                    List<ResponseModel> responseList = response.body();
                    switch (i) {
                        case 0: {
                            mAppRepoCallBack.onResponseRecent(responseList);
                            break;
                        }
                        case 1: {
                            mAppRepoCallBack.onResponsePopular(responseList);
                            break;
                        }
                        case 2: {
                            mAppRepoCallBack.onResponseBest(responseList);
                            break;
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }

    public interface AppRepoCallBack {
        public void onResponseRecent(List<ResponseModel> list);
        public void onResponseBest(List<ResponseModel> list);
        public void onResponsePopular(List<ResponseModel> list);

        public void onListCategoryResponse(List<CategoriesItem> listCategory);
        public void onProListSubCategoryResponse(List<ResponseModel> proSubCatList);
        public void onResponseProduct(ResponseModel prodect);
    }

}
