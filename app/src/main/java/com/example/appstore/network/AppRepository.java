package com.example.appstore.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appstore.model.CategoriesItem;
import com.example.appstore.model.Category;
import com.example.appstore.model.ResponseModel;

import java.io.IOException;
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
    private static AppRepository instance;

    private MutableLiveData<List<ResponseModel>> mListRecentPro = new MutableLiveData<>();
    private MutableLiveData<List<ResponseModel>> mListBestPro = new MutableLiveData<>();
    private MutableLiveData<List<ResponseModel>> mListMostVisitedPro = new MutableLiveData<>();

    private MutableLiveData<List<ResponseModel>> mListRecentProPerPage = new MutableLiveData<>();
    private MutableLiveData<List<ResponseModel>> mListBestProPerPage = new MutableLiveData<>();
    private MutableLiveData<List<ResponseModel>> mListMostVisitedProPerPage = new MutableLiveData<>();

    private MutableLiveData<List<CategoriesItem>> mListCategories = new MutableLiveData<>();
    // private MutableLiveData<List<ResponseModel>> mListSubCatPro = new MutableLiveData<>();
    private CallBack mCallBack;

    public MutableLiveData<List<Category>> getListCategory() {
        return mListCategory;
    }

    private MutableLiveData<List<Category>> mListCategory=new MutableLiveData<>();

    public MutableLiveData<List<ResponseModel>> getListRecentProPerPage() {
        return mListRecentProPerPage;
    }

    public MutableLiveData<List<ResponseModel>> getListBestProPerPage() {
        return mListBestProPerPage;
    }

    public MutableLiveData<List<ResponseModel>> getListMostVisitedProPerPage() {
        return mListMostVisitedProPerPage;
    }

 /*   public MutableLiveData<List<ResponseModel>> getListSubCatPro() {
        return mListSubCatPro;
    }*/

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public LiveData<List<CategoriesItem>> getListCategories() {
        return mListCategories;
    }

    public LiveData<List<ResponseModel>> getListRecentPro() {
        return mListRecentPro;
    }

    public LiveData<List<ResponseModel>> getListBestPro() {
        return mListBestPro;
    }

    public LiveData<List<ResponseModel>> getListMostVisitedPro() {
        return mListMostVisitedPro;
    }

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

    public void getAllProPerPage(int pageNum) throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("page", String.valueOf(pageNum));
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelPerPageCallback(0));
    }

    public void getBestProPerPage(int pageNum) throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("orderby", "rating");
        queryMap.put("page", String.valueOf(pageNum));
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelPerPageCallback(2));
    }

    public void getMostVisitedProPerPage(int pageNum) throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("orderby", "popularity");
        queryMap.put("page", String.valueOf(pageNum));
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelPerPageCallback(1));
    }

    public void getSpecificProducts(int id) throws IOException {
        mAppService.getProductById(String.valueOf(id), mQueries).enqueue(getResponseModelCallback());
    }

    public void getBestProducts() throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("orderby", "rating");
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(2));
    }

    public void getMostVisitedProducts() throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("orderby", "popularity");
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(1));
    }

    public void getRecentProducts() throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("orderby", "date");
        mAppService.getAllProducts(queryMap).enqueue(getResponseModelCallback(0));
    }

    public void getCategories() throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("parent", String.valueOf(0));
        queryMap.put("display", "default");

        mAppService.getAllCategories(queryMap).enqueue(getListCategoryCallback());
    }


    private Callback<ResponseModel> getResponseModelCallback() {
        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    mCallBack.detailProCallBack(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }

    private Callback<List<ResponseModel>> getResponseModelPerPageCallback(final int i) {
        return new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");

                    List<ResponseModel> responseList = response.body();
                    switch (i) {
                        case 0: {
                            mListRecentProPerPage.setValue(responseList);
                            break;
                        }
                        case 1: {
                            mListMostVisitedProPerPage.setValue(responseList);
                            break;
                        }
                        case 2: {
                            mListBestProPerPage.setValue(responseList);
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

    public MutableLiveData<List<ResponseModel>> FetchSearchProduct(String query){
        MutableLiveData<List<ResponseModel>> productList = new MutableLiveData();
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("search", query);
        mAppService.getAllProducts(queryMap).enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    productList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return productList;
    }

    public MutableLiveData<List<Category>> getSubCatList(int id) throws IOException {
        MutableLiveData<List<Category>> SubCatList = new MutableLiveData();
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(mQueries);
        queryMap.put("display", "subcategories");

       mAppService.getSubCategories(queryMap, String.valueOf(id))
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        Log.d(TAG, "onResponse: " + response.message());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "isSuccessful: ");
                            SubCatList.setValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
        return SubCatList;
    }

    private Callback<List<Category>> getListSubCategoryCallback() {
        MutableLiveData<List<Category>> mListSubCatPro = new MutableLiveData();
        return new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    mListSubCatPro.setValue(response.body());
                    mListCategory .setValue( response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }

    private Callback<List<CategoriesItem>> getListCategoryCallback() {
        return new Callback<List<CategoriesItem>>() {
            @Override
            public void onResponse(Call<List<CategoriesItem>> call, Response<List<CategoriesItem>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    mListCategories.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }

    public MutableLiveData<List<ResponseModel>> getProductPerCategory(int id) {
        Map<String, String> queryMap = mQueries;
        MutableLiveData<List<ResponseModel>> responseLiveData = new MutableLiveData<>();

        queryMap.put("category", String.valueOf(id));
        mAppService.getProductsOfPerCategory(queryMap).enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, retrofit2.Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> responseList = response.body();
                    responseLiveData.setValue(responseList);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return responseLiveData;
    }

    private Callback<List<ResponseModel>> getProSubCatListCategoryCallback(MutableLiveData<List<ResponseModel>> proList) {
        return new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful: ");
                    proList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };

    }

    public void getSpecificCategory(int id) throws IOException {
        mAppService.getProductById(String.valueOf(id), mQueries).enqueue(getResponseModelCallback());
    }

    public List<ResponseModel> getAllProducts() throws IOException {
        return mAppService.getAllProducts(mQueries).execute().body();
    }


    public interface CallBack {
        public void detailProCallBack(ResponseModel model);
    }

}
