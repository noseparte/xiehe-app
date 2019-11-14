package sample.Works

import com.google.gson.Gson
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import sample.Bean.AliWebBean
import sample.Bean.HXYYBean.*
import sample.Bean.UnicodeBean
import sample.Bean.UserBean
import sample.Callback.ProxyGet
import sample.Http.HttpClient
import sample.Presenter.UserRegPresenter
import sample.ViewCallback.UserRegCallback
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserRunner(private val user: UserBean, proxy: ProxyGet) : Thread(), UserRegCallback {

    private var isLogin = false

    private var mUserRegPresenter: UserRegPresenter? = null

    init {
        mUserRegPresenter = UserRegPresenter(this)
        mUserRegPresenter?.setHttpClient(HttpClient(proxy.getProxySet()))
    }

    override fun getUserInfo() = user

    override fun onGetUnicode(isSuccess: Boolean, message: String, unicode: String) {
        user.inviteState = message
        if (isSuccess) {
            user.unicode = unicode
            mUserRegPresenter?.goSignIn()
        } else {
            //NMSL
        }
    }

    override fun onSignIn(isSuccess: Boolean, message: String) {
        user.accountState = message
        if (isSuccess) {
            isLogin = isSuccess
            getPatientID()
        } else {
            //NMSL
        }
    }

    fun getPatientID() {
        println("开始读取就诊卡：" + user.userName)
        mUserRegPresenter?.getPatientID(user.patient)
    }

    fun getSearchAble(keyWord: String, times: String, callback: (ArrayList<SearchRetBeanDataSectItems>?) -> Unit) {
        mUserRegPresenter?.searchObj(keyWord, times) { isSuccess: Boolean, searchRetBean: SearchRetBean, message: String ->
            if (isSuccess) {
                var data: ArrayList<SearchRetBeanDataSectItems>? = null
                searchRetBean.data?.let {
                    data = ArrayList(searchRetBean.data.sectItems + searchRetBean.data.deptItems + searchRetBean.data.subItems)
                }
                callback(data)
            } else {
                println(message)
            }
        }
    }

    fun getSecDept(keyWord: SecDeptBean, isToday: Boolean, callback: (SecDeptRetBean) -> Unit) {
        mUserRegPresenter?.getSecDept(keyWord, isToday = isToday) { isSuccess: Boolean, ret: SecDeptRetBean, message: String ->
            if (isSuccess) {
                callback(ret)
            } else {
                println(message)
            }
        }
    }

    fun getTheDept(keyWord: DetailsDeptBean, isToday: Boolean, callback: (DetailsDeptRetBean) -> Unit) {
        mUserRegPresenter?.getTheDept(keyWord, isToday) { isSuccess: Boolean, ret: DetailsDeptRetBean, message: String ->
            if (isSuccess) {
                callback(ret)
            } else {
                println(message)
            }
        }
    }

    fun getTheDeptDocs(keyWord: GetDeptDocBean, isToday: Boolean, callback: (ArrayList<GetDeptDocRetBean.Data.Daytype1Sch>) -> Unit) {
        mUserRegPresenter?.getTheDeptDocs(keyWord, isToday) { isSuccess: Boolean, ret: GetDeptDocRetBean, message: String ->
            ret.data?.let {
                val arr = ArrayList(it.daytype1Schs.filter { obj ->
                    obj.docName != null
                } + it.daytype2Schs.filter { obj ->
                    obj.docName != null
                } + it.daytype4Schs.filter { obj ->
                    obj.docName != null
                })
                callback(arr)
            }
            if (isSuccess) {
            } else {
                println(message)
            }
        }
    }


    private var getPatientSucc = false
    override fun onGetAllPatient(isSuccess: Boolean, message: String, getAllPatientBeanData: GetAllPatientBeanData?) {
        user.patient = "->" + if (isSuccess) {
            user.inviteState = "已找到就诊人,准备就绪。"
            //mUserRegPresenter?.getPatientCard(getAllPatientBeanData!!)
            mUserRegPresenter?.getPatientCard { isSuccess_, message_ ->
                if (isSuccess_) {
                    user.inviteState = "已读取该患者就诊卡。"
                    getPatientSucc = true
                } else {
                    user.inviteState = message_
                }
            }
            getAllPatientBeanData?.patientName
        } else "获取不到就诊人"
    }

    override fun run() {}

    fun login() {
        if (!isLogin) {
            user.inviteState = "生成唯一设备ID"
            mUserRegPresenter?.getUnicode(UnicodeBean().apply {
                terminalTime = System.currentTimeMillis()
                devModel = "MI 5s"
                terminalType = 1
            })
        } else if (!getPatientSucc || mUserRegPresenter?.mSinglePatientData?.patientName == null) {
            getPatientID()
        }
    }

    fun isLogin() = isLogin

    private var runState = true
    fun stopThread() {
        if (!runState) runState = true
    }

    /**
     * 返回true表示正在检查
     */
    fun isStopEx() = runState

    fun startThread() {
        runState = false
        startRequest()//改正一个暂停线程引起的软逻辑bug
    }

    private fun startRequest() {
        if (runState || user.docInfo == null || !getPatientSucc) {
            stopThread()
            return
        }
        Thread {
            //todo 医生科室信息居然获取不到？？ 本项目已解决  下一个项目未解决
            //TODO 获取到科室后检查是否符合用户设定 符合就直接新线程预约他妈的！
            if (!user.isToday)
                mUserRegPresenter?.getDocScheduleTask(GetDocScheduleBean().apply {
                    //deptId = user.docInfo
                    docId = user.docInfo.docId
                    this.docName = user.docInfo.docName
                    this.deptId = user.deptSubjectData.deptId ?: ""
                    this.subjectId = user.deptSubjectData.subjectId ?: ""
                }) { isSuccess: Boolean, ret: GetDocScheduleRetBean, message: String ->
                    var found = false
                    if (isSuccess) {
                        ret.data.reversed().forEach { docScheduler ->
                            user.inviteState = "检查时间:" + docScheduler.schDate + " APM:" + docScheduler.dayType
                            val now = try {
                                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                            } catch (e: Exception) {
                                "时间获取错误:"
                            }
                            user.lasted = "" + docScheduler.resNo
                            if (
                                    docScheduler.schDate == user.date &&
                                    docScheduler.dayType == when {
                                        user.apm === "上午" -> "1"
                                        user.apm === "下午" -> "2"
                                        else -> docScheduler.dayType
                                    } && docScheduler.resNo > 0
                            ) {
                                println("医生号源状态:" + Gson().toJson(docScheduler))
                                user.inviteState = "$now 找到号源" + docScheduler.schDate + ":" + docScheduler.dayType
                                found = true
                                reg(docScheduler.schId)
                            }
                            if (!found) {
                                user.inviteState = "$now 医生无号,继续寻找."
                            }
                            if (runState) {
                                user.inviteState = "$now 已停止"
                            }
                        }
                    }
                    println("" + !found + "  " + !runState)
                    if (!found && !runState) startRequest()
                }
            else {
                errorLimit = 20
                user.lasted = "" + user.docInfo.resNo
                reg(user.schID)
            }
        }.start()
    }

    private var player: MediaPlayer? = null
    private val mSuccessWAV = "Success.wav"

    init {
        val media = Media(File(mSuccessWAV).toURI().toString())
        player = MediaPlayer(media)
        player?.isAutoPlay = false //设置自动播放
        //player?.cycleCount = 5 //设置循环播放次数
    }

    private var size = 0
    private var errorLimit = 10
    private var visitSucc = false
    private fun reg(schId: String) {
        if (runState) return
        if (visitSucc) {// || size >= errorLimit
            size = 0
            user.inviteState = "预约成功!"
            println("预约成功。")
            return
        }
        user.accountState = "开始获取滑块数据..."
        val aliWebBean = AliHuaKuaiManager.getInstance()?.getSingle() ?: AliWebBean()//
        user.accountState = "获取滑块成功." + aliWebBean.sig
        println(Gson().toJson(aliWebBean))
        if (aliWebBean.sig == null || aliWebBean.sig == "") {
            reg(schId)
            return
        }
        mUserRegPresenter?.regUser(schId, aliWebBean, { isSuccess, msg ->
            val now = try {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            } catch (e: Exception) {
                "时间获取错误:"
            }
            if (isSuccess) {
                user.inviteState = now + "预约成功!$msg"
                player?.play()
                visitSucc = true
            } else {
                user.inviteState = now + "预约失败:$size!$msg"
                //{"msg":"当前账号有处于预约未支付的订单，不允许同时进行其他预约操作，请在挂号记录中完成该笔订单后再试","result":false,"kind":"600120008999133"}
                //{
                //	"kind": "600120008999132",
                //	"msg": "您的账号当日存在多笔预约支付超时的订单，当日不允许再次进行预约操作",
                //	"result": false
                //}
                if (msg == "当前无可用的预约号，不能进行锁号" || msg == "当前账号有处于预约未支付的订单，不允许同时进行其他预约操作，请在挂号记录中完成该笔订单后再试" ||
                        msg == "您的账号当日存在多笔预约支付超时的订单，当日不允许再次进行预约操作") return@regUser
                reg(schId)
                size++
            }
        }, user.isToday)
    }
}