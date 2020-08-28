package com.khurshid.kamkora.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    //users
    @POST(ApiClient.APPEND_URL_USER + "signin")
    Call<JsonObject> getSignIn(@Body JsonObject object);

    @POST(ApiClient.APPEND_URL_USER + "signup")
    Call<JsonObject> getSignUp(@Body JsonObject object);

    //orders
    @POST(ApiClient.APPEND_URL_ORDER + "add/{id}")
    Call<JsonObject> setOrder(@Path("id") String userId, @Body JsonObject object);

    @GET(ApiClient.APPEND_URL_ORDER + "{id}")
    Call<JsonObject> getOrderOfUser(@Header("x-access-token") String token, @Path("id") String userId);

    @GET(ApiClient.APPEND_URL_ORDER + "{id}")
    Call<JsonObject> getAllOrders(@Header("x-access-token") String token, @Path("id") String userId);

    @POST(ApiClient.APPEND_URL_ORDER + "cart/{id}")
    Call<JsonObject> setCartOrder(@Path("id") String userId, @Body JsonObject object);

    //address
    @GET(ApiClient.APPEND_URL_ADDRESS + "{id}")
    Call<JsonObject> getAddressOfUser(@Header("x-access-token") String token, @Path("id") String userId);

    @POST(ApiClient.APPEND_URL_ADDRESS + "add/{id}")
    Call<JsonObject> setUserAddress(@Path("id") String userId, @Body JsonObject object);

    //Services
    @GET(ApiClient.APPEND_URL_SERVICE + "")
    Call<JsonObject> getAllServices(@Header("x-access-token") String token);

    //Home Ads
    @GET(ApiClient.APPEND_URL_ADS + "")
    Call<JsonObject> getHomeAds(@Header("x-access-token") String token);
}
