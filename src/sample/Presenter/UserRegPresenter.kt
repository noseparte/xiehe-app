package sample.Presenter

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sample.Bean.*
import sample.Bean.HXYYBean.*
import sample.Http.HttpClient
import sample.ViewCallback.UserRegCallback
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class UserRegPresenter(private var callback: UserRegCallback) : BasePresenter() {
    private var mLoginBean: LoginBean? = null
    private var mPatientData: Array<GetAllPatientBeanData?>? = null
    private var mPatientIndex = -1
    var mSinglePatientData: GetAllPatientBeanData? = null

    /**
     * 临时测试用Http请求客户端
     */
    private var mHttpClient: HttpClient? = null

    fun setHttpClient(client: HttpClient) {
        this.mHttpClient = client
    }

    /**
     * 获取设备唯一ID
     */
    fun getUnicode(deviceBean: UnicodeBean) {
        mHttpClient!!.getmHttp().getUnicode(deviceBean).enqueue(object : Callback<UnicodeGet?> {
            override fun onResponse(call: Call<UnicodeGet?>?, response: Response<UnicodeGet?>) {
                println(Gson().toJson(response.body()))
                if (response.body() == null || response.body()?.result == false) {
                    callback.onGetUnicode(false, "失败:" + response.body()?.msg)
                    return
                }
                mHttpClient!!.setUnicodeOlag(response.body()!!.data.unicode, response.body()!!.data.timelag)
                callback.onGetUnicode(true, "新设备已注册!", response.body()!!.data.unicode)
            }

            override fun onFailure(call: Call<UnicodeGet?>?, throwable: Throwable?) {
                callback.onGetUnicode(false, throwable?.message ?: "网络异常")
            }
        })
    }

    /**
     * 登录账号
     */
    fun goSignIn() {
        val username = callback.getUserInfo().userName
        var pass = callback.getUserInfo().password
        val unicode = callback.getUserInfo().unicode
        pass = HttpClient.getPublicKeySignature(pass)
        val map = HashMap<String, String>()
        map["username"] = username
        map["unicode"] = unicode
        map["password"] = pass
        mHttpClient?.getmHttp()?.signIn(map, LoginRequestBean().apply {
            client_id = "999998@173"
            this.unicode = unicode
            grant_type = "password"
            this.password = pass
            this.username = username
            client_secret = "4d3dd7229612473a8bbdde4a6b21b974"
        })?.enqueue(object : Callback<LoginBean?> {
            override fun onResponse(call: Call<LoginBean?>?, response: Response<LoginBean?>) {
                println(Gson().toJson(response.body()))
                //{"access_token":"uLi6c9wAzFFa4oC0sTq8PNo3Ku5abUeB_P_yIhCQ7h87OPweBtNx7Ds-5cJVRXJMTOIms6VLofCNBm22H8WBj6dZDJ3zG9QW01BrtZAc7_Ypc419KPLZ98ZIbvxLJaCASvPFOPHdQM2--yre1ZsyBZ8ZYA7SYlzWT5lnzUc8FKdA","refresh_token":"gJu_2-sJPIgCsObFhOwiEOQ_UElswOOTvljXMxUxa5U2Q5ybn1IwdzITgQJx4s-cVMjAEG59BaFsxSy1eGjnFeYssz3I3GC2sv7nF2ZlDAjO7r4fXWQC4cSa8ZdaYrviXXy4KTmZssabuy0cjtyoL39b5nV3NV-_QXOUW812cOq8","token_type":"bearer","expires_in":7199,"phoneNo":13001223029,"usId":1161504}
                mLoginBean = response.body()
                if (mLoginBean?.result == true) {
                    callback.onSignIn(true, message = "登录成功!")
                    mHttpClient?.setAccess_Token(mLoginBean!!.data.access_token)
                } else {
                    callback.onSignIn(false, message = "登录失败!" + mLoginBean?.msg)
                }
            }

            override fun onFailure(call: Call<LoginBean?>?, throwable: Throwable?) {
                callback.onSignIn(false, message = "没有网络!")
            }
        })
    }

    fun getPatientArr() = mPatientData

    fun setPatientIndex(index: Int) {
        mPatientIndex = index
    }


    fun searchObj(str: String, time: String, callback: (isSuccess: Boolean, ret: SearchRetBean, message: String) -> Unit) {
        mHttpClient?.getmHttp()?.searchSection(SearchSectionBean().apply {
            this.sVal = str
            this.schDate = time
        })?.enqueue(object : Callback<SearchRetBean> {
            override fun onFailure(p0: Call<SearchRetBean>, p1: Throwable) {
                callback(false, SearchRetBean(), "网络错误")
            }

            override fun onResponse(p0: Call<SearchRetBean>, p1: Response<SearchRetBean>) {
                callback(p1.body()?.result == true, p1.body() ?: SearchRetBean(), "获取完成。")
            }
        })
    }

    fun getSecDept(str: SecDeptBean, isToday: Boolean, callback: (isSuccess: Boolean, ret: SecDeptRetBean, message: String) -> Unit) {
        mHttpClient?.getmHttp()?.getSecDept(str, if (isToday) "042" else "040")?.enqueue(object : Callback<SecDeptRetBean> {
            override fun onFailure(p0: Call<SecDeptRetBean>, p1: Throwable) {
                callback(false, SecDeptRetBean(), "网络错误")
            }

            override fun onResponse(p0: Call<SecDeptRetBean>, p1: Response<SecDeptRetBean>) {
                callback(p1.body()?.result == true, p1.body() ?: SecDeptRetBean(), "获取完成。")
            }
        })
    }

    fun getTheDept(str: DetailsDeptBean, isToday: Boolean, callback: (isSuccess: Boolean, ret: DetailsDeptRetBean, message: String) -> Unit) {
        mHttpClient?.getmHttp()?.getTheDept(str, if (isToday) "046" else "045")?.enqueue(object : Callback<DetailsDeptRetBean> {
            override fun onFailure(p0: Call<DetailsDeptRetBean>, p1: Throwable) {
                callback(false, DetailsDeptRetBean(), "网络错误")
            }

            override fun onResponse(p0: Call<DetailsDeptRetBean>, p1: Response<DetailsDeptRetBean>) {
                callback(p1.body()?.result == true, p1.body() ?: DetailsDeptRetBean(), "获取完成。")
            }
        })
    }

    fun getTheDeptDocs(str: GetDeptDocBean, isToday: Boolean, callback: (isSuccess: Boolean, ret: GetDeptDocRetBean, message: String) -> Unit) {
        mHttpClient?.getmHttp()?.getTheDeptDocs(str, if (isToday) "011" else "072")?.enqueue(object : Callback<GetDeptDocRetBean> {
            override fun onFailure(p0: Call<GetDeptDocRetBean>, p1: Throwable) {
                callback(false, GetDeptDocRetBean(null, false), "网络错误")
            }

            override fun onResponse(p0: Call<GetDeptDocRetBean>, p1: Response<GetDeptDocRetBean>) {
                p1.body()?.let { callback(p1.body()?.result == true, it, "获取完成。") }
            }
        })
    }

    fun a(str: String): String {
        println(str)
        return a(str, "MD5").toUpperCase()
    }

    fun a(str: String, str2: String): String {
        try {
            val instance = MessageDigest.getInstance(str2)
            instance.reset()
            instance.update(str.toByteArray())
            val digest = instance.digest()
            val stringBuffer = StringBuffer()
            for (b in digest) {
                val hexString = Integer.toHexString(b.toInt() and 255)
                if (hexString.length == 1) {
                    stringBuffer.append('0')
                }
                stringBuffer.append(hexString)
            }
            //k.c(f1274a, "pass " + str + "   md5 version is " + stringBuffer.toString());
            return stringBuffer.toString() + ""
        } catch (e: NoSuchAlgorithmException) {
            //k.e(f1274a, e.getMessage());
            return ""
        }

    }

    fun getPatientID(patient: String) {
        mHttpClient?.getmHttp()?.getAllPatient("")?.enqueue(object : Callback<GetAllPatientBean> {
            override fun onFailure(p0: Call<GetAllPatientBean>, p1: Throwable) {
                callback.onGetAllPatient(isSuccess = false, message = p1.message ?: "网络异常")
            }

            override fun onResponse(p0: Call<GetAllPatientBean>, p1: Response<GetAllPatientBean>) {
                println(Gson().toJson(p1.body()))
                //println("就诊人  ====   " + Gson().toJson(mPatientData))
                if (p1.body()?.result == true && p1.body()?.data?.size ?: 0 > 0) {
                    var find = false
                    mPatientData = p1.body()?.data!!
                    mPatientData!!.forEachIndexed { index, data ->
                        if (!find) {
                            print(data!!.patientName == patient)
                            println(a(data.patientName) + " 对比用户设置 " + a(patient))
                            println(data.patientName + " 对比用户设置 " + patient)
                        }
                        if (!find && data!!.patientName == patient) {
                            println("找到了：" + Gson().toJson(data))
                            mPatientIndex = index
                            mSinglePatientData = data
                            find = true
                            return@forEachIndexed
                        }
                    }
                    if (!find && mPatientIndex == -1) {//万一存在设置错误找不到就诊人 那么默认选第一个  应该不存在吧
                        mPatientIndex = 0
                        mSinglePatientData = mPatientData!![mPatientIndex]
                        println("没找到：" + mSinglePatientData?.patientName)
                    }
                }
                callback.onGetAllPatient(isSuccess = p1.body()?.result
                        ?: false, message = "获取数据成功!", getAllPatientBeanData = mSinglePatientData)
            }
        })
    }

    private var mSpecificPatientCard: PatientRetBeanData? = null
    fun getPatientCard(callback: (isSuccess: Boolean, message: String) -> Unit) {
        mHttpClient?.getmHttp()?.getPatientInformation(PatientBean().apply {
            this.patId = mSinglePatientData?.patId
        })?.enqueue(object : Callback<PatientRetBean> {
            override fun onFailure(p0: Call<PatientRetBean>, p1: Throwable) {
                callback(false, "网络错误")
            }

            override fun onResponse(p0: Call<PatientRetBean>, p1: Response<PatientRetBean>) {
                if (p1.body() != null)
                    mSpecificPatientCard = p1.body()!!.data
                callback(p1.body()?.result == true, "获取完成。")
            }
        })
    }

    fun getDocScheduleTask(str: GetDocScheduleBean, callback: (isSuccess: Boolean, ret: GetDocScheduleRetBean, message: String) -> Unit) {
        //println("医生数据" + Gson().toJson(str))
        mHttpClient?.getmHttp()?.getDocScheduleTask(str)?.enqueue(object : Callback<GetDocScheduleRetBean> {
            override fun onFailure(p0: Call<GetDocScheduleRetBean>, p1: Throwable) {
                callback(false, GetDocScheduleRetBean(), "网络错误")
            }

            override fun onResponse(p0: Call<GetDocScheduleRetBean>, p1: Response<GetDocScheduleRetBean>) {
                callback(p1.body()?.result == true, p1.body() ?: GetDocScheduleRetBean(), "获取完成。")
            }
        })
    }

    fun regUser(schId: String, mAliHuaKuai: AliWebBean, callback: (isSuccess: Boolean, msg: String) -> Unit, isToday: Boolean) {
        //{"accessPatId":"45608775","patientName":"万明先","patId":"d9359639d83e45e4a472d7378c233188","psvFlag":"Y","cardNo":"652201196304010679","isVerify":"Y","phoneNo":"17710615965","relation":5,"usId":"57d94f0c94f44006a374f05bf6f070d7","hosPatCardNo":"652201196304010679","patIdLong":0,"documentId":"","workDept":"","primaryPatFlag":"N","addr":"","email":""}
        val requestPayBean = RequestPayBean().apply {
            cardNo = mSpecificPatientCard?.cardNo
            hosPatCardNo = mSpecificPatientCard?.hosPatCardNo
            hosPatCardType = mSpecificPatientCard?.hosPatCardType
            patId = mSpecificPatientCard?.patId
            patName = mSpecificPatientCard?.patientName
            phoneNo = mSpecificPatientCard?.phoneNo
            psvFlag = mSpecificPatientCard?.psvFlag
            this.schId = schId
            takeIndex = ""
        }
        val header = HashMap<String, String>().apply {
            put("slider_token", mAliHuaKuai.token ?: "")
            put("slider_sessionid", mAliHuaKuai.cses ?: "")
            put("slider_sig", mAliHuaKuai.sig ?: "")
        }
        mHttpClient?.getmHttp()
                ?.requestPay(requestPayBean, header, if (isToday) "012" else "003")
                ?.enqueue(object : Callback<RequestPayRetBean> {
                    override fun onFailure(call: Call<RequestPayRetBean>, t: Throwable) {
                        callback(false, "网络错误!")
                    }

                    override fun onResponse(call: Call<RequestPayRetBean>, response: Response<RequestPayRetBean>) {
                        println(Gson().toJson(response.body()))
                        callback(response.body()?.result == true, response.body()?.msg
                                ?: if (isToday) "挂号成功!" else "未知错误!")
                    }
                })
    }
}