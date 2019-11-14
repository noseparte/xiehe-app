package sample.Http

import retrofit2.Call
import retrofit2.http.*
import sample.Bean.*
import sample.Bean.HXYYBean.*


interface ClientInterface {
    /**
     * 获取服务器通知
     */
    @POST("/hs-xh-single-web/r/173/20013/001")
    fun getServerNotify(@Body post: NewsGet): Call<GetNewsBean>

    @POST("/hs-udb-resource/r/10001/100")
    fun getUnicode(@Body body: UnicodeBean): Call<UnicodeGet>

    /**
     * 登录
     */
    @POST("/hs-udb-auth/oauth/token?grant_type=password&client_secret=4d3dd7229612473a8bbdde4a6b21b974&client_id=999998@173")
    fun signIn(@QueryMap map: Map<String, String>, @Body body: LoginRequestBean): Call<LoginBean>

    /**
     * 下订单
     */
    @POST("/hs-xh-single-web/r/173/20008/{port}")
    fun requestPay(@Body data: RequestPayBean, @HeaderMap headers: HashMap<String, String>, @Path("port") port: String = "003"): Call<RequestPayRetBean>

    /**
     * 获取所有的就诊人
     */
    @POST("/hs-xh-single-web/r/173/20004/005")
    fun getAllPatient(@Body nullData: String): Call<GetAllPatientBean>

    /**
     * 获取就诊人的就诊卡信息
     */
    @POST("/hs-xh-single-web/r/173/20004/006")
    fun getPatientInformation(@Body patient: PatientBean): Call<PatientRetBean>

    /**
     * 搜索科室/医生
     */
    @POST("/hs-xh-single-web/r/173/20002/001")
    fun searchSection(@Body search: SearchSectionBean): Call<SearchRetBean>

    /**
     * 获取医生排班信息
     */
    @POST("/hs-xh-single-web/r/173/20002/070")
    fun getDocScheduleTask(@Body docInfo: GetDocScheduleBean): Call<GetDocScheduleRetBean>

    /**
     * 获取科室分类
     */
    @POST("/hs-xh-single-web/r/173/20002/{port}")
    fun getSecDept(@Body secDeptBean: SecDeptBean, @Path("port") port: String = "040"): Call<SecDeptRetBean>

    /**
     * 获取科室子分类
     */
    @POST("/hs-xh-single-web/r/173/20002/{port}")
    fun getTheDept(@Body secDeptBean: DetailsDeptBean, @Path("port") port: String = "045"): Call<DetailsDeptRetBean>

    /**
     * 获取科室子分类的医生
     */
    @POST("/hs-xh-single-web/r/173/20002/{port}")
    fun getTheDeptDocs(@Body secDeptBean: GetDeptDocBean, @Path("port") port: String = "072"): Call<GetDeptDocRetBean>


}