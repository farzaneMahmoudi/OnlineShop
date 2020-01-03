package com.example.appstore.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appstore.model.CategoriesItem;
import com.example.appstore.model.ResponseModel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRepository {

    public static final String CONSUMER_KEY_VALUE = "ck_1fdf599207f3d6e688240bcdc2e1dda77d4cf109";
    public static final String CONSUMER_SECRET_VALUE = "cs_e60bcfe78da97ee2c3f483470e7114ea406ab07d";
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String TAG = "AppRepository";

    private Map<String, String> mQueries = new HashMap<>();
    private Retrofit mRetrofit;
    private AppService mAppService;

    private MutableLiveData<List<ResponseModel>> mListRecentPro = new MutableLiveData<>();
    private MutableLiveData<List<ResponseModel>> mListBestPro=new MutableLiveData<>();
    private MutableLiveData<List<ResponseModel>> mListMostVisitedPro = new MutableLiveData<>();
    private MutableLiveData<List<String>> mImageUrlsRecentPro = new MutableLiveData<>();
    private ResponseModel mResponseModel ;
    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public ResponseModel getResponseModel() {
        return mResponseModel;
    }

    public MutableLiveData<List<String>> getImageUrlsRecentPro() {
        return mImageUrlsRecentPro;
    }

    public List<String> getUrls() {
        return mUrls;
    }

    private List<String> mUrls = new ArrayList<>();

    public LiveData<List<ResponseModel>> getListRecentPro() {
        return mListRecentPro;
    }

    public LiveData<List<ResponseModel>> getListBestPro() {
        return mListBestPro;
    }

    public LiveData<List<ResponseModel>> getListMostVisitedPro() {
        return mListMostVisitedPro;
    }

    private static AppRepository instance;

    public static AppRepository getInstance() {
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

    public void getAllProPerPage(int pageNum) throws IOException {
        Map<String, String> queryMap = mQueries;
        queryMap.put("page",String.valueOf(pageNum));
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(0));
    }

    public void getBestProPerPage(int pageNum) throws IOException {
        Map<String, String> queryMap = mQueries;
        queryMap.put("orderby", "rating");
        queryMap.put("page",String.valueOf(pageNum));
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(2));
    }

    public void getMostVisitedProPerPage(int pageNum) throws IOException {
        Map<String, String> queryMap = mQueries;
        queryMap.put("orderby", "popularity");
        queryMap.put("page",String.valueOf(pageNum));
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(1));
    }

    public void getSpecificProducts(int id) throws IOException {
        mAppService.getProductById(String.valueOf(id), mQueries).enqueue(getResponseModelCallback());
    }

    public void getSpecificCategory(int id) throws IOException {
        mAppService.getProductById(String.valueOf(id), mQueries).enqueue(getResponseModelCallback());
    }

    public void getBestProducts() throws IOException {
        Map<String, String> queryMap = mQueries;
        queryMap.put("orderby", "rating");
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(2));
    }

    public void getMostVisitedProducts() throws IOException {
        Map<String, String> queryMap = mQueries;
        queryMap.put("orderby", "popularity");
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(1));
    }

    public void getRecentProducts() throws IOException {
        Map<String, String> queryMap = mQueries;
        queryMap.put("orderby", "date");
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(0));
    }


    public void getCategories() throws IOException {
        mAppService.getAllCategories(mQueries).enqueue(getListCategoryCallback());
    }

    public void getProSubCatList(int id) throws IOException {
        mAppService.getProductsOfSpecificCategory(mQueries,String.valueOf(id)).enqueue(getProSubCatListCategoryCallback());
    }

    private Callback<List<CategoriesItem>> getListCategoryCallback() {
        return new Callback<List<CategoriesItem>>() {
            @Override
            public void onResponse(Call<List<CategoriesItem>> call, Response<List<CategoriesItem>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    List<CategoriesItem> categoriesList = response.body();
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
                    // mResponseModel.setValue( response.body());
                     mCallBack.detailProCallBack( response.body());
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
                    setUrlList(responseList);

                    switch (i) {
                        case 0: {
                            mListRecentPro.setValue(responseList);
                            break;
                        }
                        case 1: {
                            mListMostVisitedPro.setValue(responseList);
                            break;
                        }
                        case 2: {
                            mListBestPro.setValue(responseList);
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
    private void setUrlList(List<ResponseModel> list){
        for(ResponseModel responseModel:list){
            mUrls.add(responseModel.getImages().get(0).getSrc());
        }
        mImageUrlsRecentPro.setValue(mUrls);
    }

    public interface CallBack{
        public void detailProCallBack(ResponseModel model);
    }

}
