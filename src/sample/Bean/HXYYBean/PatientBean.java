package sample.Bean.HXYYBean;

import static sample.Http.HttpClient.getSafeUUID;

public class PatientBean {
    private String patId;
    private String safeUniqueCode = getSafeUUID();

    public String getPatId() {
        return this.patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }
}
