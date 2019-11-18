package sample.Bean.HXYYBean;

import static sample.Http.HttpClient.getSafeUUID;

public class SecDeptBean {
    private String sectId;
    private String isAccess;
    private String hosDistId;
    private String safeUniqueCode = getSafeUUID();

    public String getSectId() {
        return this.sectId;
    }

    public void setSectId(String sectId) {
        this.sectId = sectId;
    }

    public String getIsAccess() {
        return this.isAccess;
    }

    public void setIsAccess(String isAccess) {
        this.isAccess = isAccess;
    }

    public String getHosDistId() {
        return this.hosDistId;
    }

    public void setHosDistId(String hosDistId) {
        this.hosDistId = hosDistId;
    }

    public String getSafeUniqueCode() {
        return this.safeUniqueCode;
    }

    public void setSafeUniqueCode(String safeUniqueCode) {
        this.safeUniqueCode = safeUniqueCode;
    }
}
