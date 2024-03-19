package com.lym2024.newtptest.network

//import com.lym2024.newtptest.data.KakaoSearchplaceRespons
import com.lym2024.newtptest.data.QSD
import com.lym2024.newtptest.data.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {

    // 카카오 로컬 검색 API... 요청해주는 코드 만들어줘. 우선 응답 type : String
    @Headers("Authorization: KakaoAK 2c257f76bbce8a06b64438e455b1f9ff")
    @GET("/v2/local/search/keyword.json")
    fun searchPlaceToString(@Query("query") query : String, @Query("x") longitude : String, @Query("y") latitude : String ) : Call<String>

    // 카카오 로컬 검색 API... 요청해주는 코드 만들어줘. 우선 응답 type : KakaoSearchPlacaeResponse
    @Headers("Authorization: KakaoAK 2c257f76bbce8a06b64438e455b1f9ff")
    @GET("/v2/local/search/keyword.json?sort=distance")
    fun searchPlace(@Query("query") query : String, @Query("x") longitude : String, @Query("y") latitude : String ) : Call<Search>

    // 네아로 회원정보 프로필 api요청
    @GET("/v1/nid/me")
    fun getNidUserInfo( @Header("Authorization") authorization : String ) : Call<String>

    @Headers("accept: application/json")
    @GET("CNV_060/request")
    fun getDatainfo(@Query("serviceKey")serviceKey: String, @Query("numOfRows") numOfRows : String, @Query("pageNo") pageNo : String) : Call<QSD>


}