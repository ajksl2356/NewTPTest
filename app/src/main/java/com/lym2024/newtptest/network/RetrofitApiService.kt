package com.lym2024.newtptest.network

//import com.lym2024.newtptest.data.KakaoSearchplaceRespons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {

//    // 카카오 로컬 검색 API... 요청해주는 코드 만들어줘. 우선 응답 type : String
//    @Headers("Authorization: KakaoAK 8de4282a546f1afd46de7c9ae5894dcf")
//    @GET("/v2/local/search/keyword.json")
//    fun searchPlaceToString(@Query("query") query : String, @Query("x") longitude : String, @Query("y") latitude : String ) : Call<String>
//
//    // 카카오 로컬 검색 API... 여청해주는 코드 만들어줘. 우선 응답 type : KakaoSearchPlacaeResponse
//    @Headers("Authorization: KakaoAK 8de4282a546f1afd46de7c9ae5894dcf")
//    @GET("/v2/local/search/keyword.json?sort=distance")
//    fun searchPlace(@Query("query") query : String, @Query("x") longitude : String, @Query("y") latitude : String ) : Call<KakaoSearchplaceRespons>
}