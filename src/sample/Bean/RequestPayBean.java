package sample.Bean;

import static sample.Http.HttpClient.getSafeUUID;

public class RequestPayBean {
    private String schId;
    private String hosPatCardType = "0";
    private String hosPatCardNo;
    private String patId;
    private String psvFlag;
    private String patName;
    private String takeIndex;
    private String cardNo;
    private String phoneNo;
    private String safeUniqueCode = getSafeUUID();

    public String getSchId() {
        return this.schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

    public String getHosPatCardType() {
        return this.hosPatCardType;
    }

    public void setHosPatCardType(String hosPatCardType) {
        this.hosPatCardType = hosPatCardType;
    }

    public String getHosPatCardNo() {
        return this.hosPatCardNo;
    }

    public void setHosPatCardNo(String hosPatCardNo) {
        this.hosPatCardNo = hosPatCardNo;
    }

    public String getPatId() {
        return this.patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPsvFlag() {
        return this.psvFlag;
    }

    public void setPsvFlag(String psvFlag) {
        this.psvFlag = psvFlag;
    }

    public String getPatName() {
        return this.patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getTakeIndex() {
        return this.takeIndex;
    }

    public void setTakeIndex(String takeIndex) {
        this.takeIndex = takeIndex;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSafeUniqueCode() {
        return this.safeUniqueCode;
    }

    public void setSafeUniqueCode(String safeUniqueCode) {
        this.safeUniqueCode = safeUniqueCode;
    }
}
