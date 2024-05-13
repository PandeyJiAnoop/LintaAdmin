package com.akp.lintapipe_admin.Retrofit;
/**
 * Created by Anoop pandey-9696381023.
 */
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("GetServiceList")
    Call<String> OperatorList();

    @Headers("Content-Type: application/json")
    @GET("SAAllDashboard")
    Call<String> SAAllDashboard();

    @Headers("Content-Type: application/json")
    @POST("SuperAdminLogin")
    Call<String> APIName_SuperAdminLogin(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("SATotalsalepersonReg")
    Call<String> APIName_SATotalsalepersonReg(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("SATotalDistributorRetailerReg")
    Call<String> APIName_SATotalDistributorRetailerReg(
            @Body String body);


    @Headers("Content-Type: application/json")
    @POST("SAOrderbysales")
    Call<String> APIName_SAOrderbysales(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("SAOrderbysalesByorderId")
    Call<String> APIName_SAOrderbysalesByorderId(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("SADIS_retOrderList")
    Call<String> APIName_SADIS_retOrderList(
            @Body String body);


    @Headers("Content-Type: application/json")
    @POST("SADIS_retOrderListByOrderID")
    Call<String> APIName_SADIS_retOrderListByOrderID(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("SAAllCommissionDetail")
    Call<String> APIName_SAAllCommissionDetail(
            @Body String body);

}