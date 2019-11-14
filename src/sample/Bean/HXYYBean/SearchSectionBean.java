package sample.Bean.HXYYBean;

import static sample.Http.HttpClient.getSafeUUID;

public class SearchSectionBean {
    private String schDate = "";
    private String sVal;
    private String type = "0";
    private String safeUniqueCode = getSafeUUID();

    public String getSchDate() {
        return this.schDate;
    }

    public void setSchDate(String schDate) {
        this.schDate = schDate;
    }

    public String getSVal() {
        return this.sVal;
    }

    public void setSVal(String sVal) {
        this.sVal = sVal;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
