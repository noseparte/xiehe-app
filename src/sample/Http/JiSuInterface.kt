package sample.Http

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import sample.Bean.AliWebBean

interface JiSuInterface {
    @FormUrlEncoded
    @POST("/reqcode")
    fun getReq(
            @Field("acc") acc: String,
            @Field("pwd") pwd: String,
            @Field("mode") mode: String,
            @Field("codedata") codedata: String
    ): Call<AliWebBean>
}