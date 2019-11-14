package sample.ViewCallback

import sample.Bean.HXYYBean.GetAllPatientBeanData
import sample.Bean.UserBean

interface UserRegCallback {
    fun getUserInfo(): UserBean
    fun onGetUnicode(isSuccess: Boolean, message: String, unicode: String = "")
    fun onSignIn(isSuccess: Boolean, message: String = "")
    fun onGetAllPatient(isSuccess: Boolean, message: String, getAllPatientBeanData: GetAllPatientBeanData? = null)
}