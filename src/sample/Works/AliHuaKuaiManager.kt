package sample.Works

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sample.Bean.AliWebBean
import sample.Http.JiSuClient

class AliHuaKuaiManager {
    private var limit: Int = -1
    private var mSigBeans: ArrayList<AliWebBean> = ArrayList()
    private var user = ""
    private var pass = ""
    private lateinit var callback: (currentSize: Int) -> Unit

    companion object {
        private var instance: AliHuaKuaiManager? = null
        fun getInstance(user: String, pass: String, loaderLimit: Int, callback: (currentSize: Int) -> Unit): AliHuaKuaiManager {
            if (instance == null) {
                instance = AliHuaKuaiManager().apply {
                    this.callback = callback
                    setUserAndPass(user, pass, loaderLimit)
                }
            }
            return instance!!
        }

        fun getInstance(): AliHuaKuaiManager? {
            return instance
        }
    }

    fun setUserAndPass(user: String, pass: String, limit: Int) {
        this.user = user
        this.pass = pass
        this.limit = limit
        Thread {
            for (i in 1..limit) {
                println("开始初始化滑块数据 $i...")
                requestNew()
            }
        }.start()

    }

    fun getSingle(): AliWebBean {
        synchronized(this) {
            while (mSigBeans.size <= 0) {
                Thread.sleep(100)
                println("数据库为空,正在等待滑块数据返回...")
            }
            val data: AliWebBean = mSigBeans[0]
            mSigBeans.removeAt(0)
            println("消耗了一个")
            if (mSigBeans.size <= limit) {
                Thread {
                    requestNew()
                    println("正在请求新的滑块数据...")
                }.start()
            }
            return data
        }
    }

    private var errorSize = 10
    fun requestNew() {
        /*mSigBeans.add(AliWebBean().apply {
            this.sig = "" + System.currentTimeMillis()
        })
        if (1 == 1) return*/
        if (errorSize < 0) {
            return
        }
        JiSuClient.getInstance().client.getReq(
                user,
                pass,
                "20",
                "{\"aid\":\"FFFF0HSYT00000000000\",\"scene\":\"register_h5\",\"token\":\"\",\"referer\":\"https://webcdn2.hsyuntai.com/page/app/hkyzh5_xh.html\"}"
        ).enqueue(object : Callback<AliWebBean> {
            override fun onFailure(call: Call<AliWebBean>, t: Throwable) {
                if (errorSize < 0) {
                    errorSize = 10
                } else {
                    errorSize--
                    requestNew()
                }
            }

            override fun onResponse(call: Call<AliWebBean>, response: Response<AliWebBean>) {
                println(Gson().toJson(response.body()))
                if (response.body()?.sig != null) {
                    mSigBeans.add(response.body()!!)
                    callback(mSigBeans.size)
                    println("加入一个新的滑块数据")
                } else {
                    errorSize = 10
                    requestNew()
                }
            }
        })
    }
}