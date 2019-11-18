package sample.Bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.Bean.HXYYBean.DetailsDeptRetBeanData;
import sample.Bean.HXYYBean.GetDeptDocRetBean;
import sample.Works.SuperCheckBox;

public class UserBean {
    public String schID = "";
    public boolean isToday;
    private SimpleStringProperty userName = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty patient = new SimpleStringProperty();//就诊人
    private SimpleStringProperty accountState = new SimpleStringProperty();//账号状态
    private SimpleStringProperty YYKS = new SimpleStringProperty();//预约科室
    private SimpleStringProperty docName = new SimpleStringProperty();//医生名称
    private SimpleStringProperty SFYB = new SimpleStringProperty();//是否医保
    private SimpleStringProperty APM = new SimpleStringProperty();
    private SimpleStringProperty date = new SimpleStringProperty();//日期
    private SimpleStringProperty inviteState = new SimpleStringProperty();//预约状态
    private SimpleIntegerProperty ids = new SimpleIntegerProperty();//id
    private SimpleStringProperty lasted = new SimpleStringProperty();//余票
    private SimpleStringProperty todayInvite = new SimpleStringProperty();//余票
    private SuperCheckBox checkBox = new SuperCheckBox();//cb
    private SimpleStringProperty unicode = new SimpleStringProperty();//unicode
    private SimpleObjectProperty<GetDeptDocRetBean.Data.Daytype1Sch> docInfo = new SimpleObjectProperty<>();
    private SimpleObjectProperty<DetailsDeptRetBeanData> deptSubjectData = new SimpleObjectProperty<>();

    public boolean willModify = false;

    public String getTodayInvite() {
        return todayInvite.get();
    }

    public SimpleStringProperty todayInviteProperty() {
        return todayInvite;
    }

    private void setTodayInvite(String todayInvite) {
        this.todayInvite.set(todayInvite);
    }

    public GetDeptDocRetBean.Data.Daytype1Sch getDocInfo() {
        return docInfo.get();
    }

    public void setDocInfo(GetDeptDocRetBean.Data.Daytype1Sch docInfo) {
        this.docInfo.set(docInfo);
        if (docInfo != null)
            setDocName(docInfo.getDocName());
    }

    public String getLasted() {
        return lasted.get();
    }

    public void setLasted(String lasted) {
        this.lasted.set(lasted);
    }

    public SimpleStringProperty lastedProperty() {
        return lasted;
    }

    public SimpleObjectProperty<GetDeptDocRetBean.Data.Daytype1Sch> docInfoProperty() {
        return docInfo;
    }

    public SuperCheckBox getCheckBox() {
        return checkBox;
    }

    public boolean isSelect() {
        return checkBox.isSelect();
    }

    public String getUnicode() {
        return unicode.get();
    }

    public void setUnicode(String unicode) {
        this.unicode.set(unicode);
    }

    public int getIds() {
        return ids.get();
    }

    public void setIds(int ids) {
        this.ids.set(ids);
    }

    public SimpleIntegerProperty idsProperty() {
        return ids;
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getPatient() {
        return patient.get();
    }

    public void setPatient(String patient) {
        this.patient.set(patient);
    }

    public SimpleStringProperty patientProperty() {
        return patient;
    }

    public String getAccountState() {
        return accountState.get();
    }

    public void setAccountState(String accountState) {
        this.accountState.set(accountState);
    }

    public SimpleStringProperty accountStateProperty() {
        return accountState;
    }

    public String getYYKS() {
        return YYKS.get();
    }

    public void setYYKS(String YYKS) {
        this.YYKS.set(YYKS);
    }

    public SimpleStringProperty YYKSProperty() {
        return YYKS;
    }

    public String getDocName() {
        return docName.get();
    }

    public void setDocName(String docName) {
        this.docName.set(docName);
    }

    public SimpleStringProperty docNameProperty() {
        return docName;
    }

    public String getSFYB() {
        return SFYB.get();
    }

    public void setSFYB(String SFYB) {
        this.SFYB.set(SFYB);
    }

    public SimpleStringProperty SFYBProperty() {
        return SFYB;
    }

    public String getAPM() {
        return APM.get();
    }

    public void setAPM(String APM) {
        this.APM.set(APM);
    }

    public SimpleStringProperty APMProperty() {
        return APM;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getInviteState() {
        return inviteState.get();
    }

    public void setInviteState(String inviteState) {
        this.inviteState.set(inviteState);
    }

    public SimpleStringProperty inviteStateProperty() {
        return inviteState;
    }

    public DetailsDeptRetBeanData getDeptSubjectData() {
        return deptSubjectData.get();
    }

    public void setDeptSubjectData(DetailsDeptRetBeanData deptSubjectData) {
        this.deptSubjectData.set(deptSubjectData);
    }

    public SimpleObjectProperty<DetailsDeptRetBeanData> deptSubjectDataProperty() {
        return deptSubjectData;
    }

    /**
     * 笨比操作 schId 和 schID分不清引发逻辑错误 属实弟弟 仅此记录
     *
     * @param isToday
     * @param schId
     */
    public void setTodayInvite(boolean isToday, String schId) {
        if (isToday) {
            setTodayInvite("今日预约");
        } else {
            setTodayInvite("预约挂号");
        }
        this.schID = schId;
        this.isToday = isToday;
    }
}
