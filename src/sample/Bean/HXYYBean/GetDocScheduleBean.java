package sample.Bean.HXYYBean;

import static sample.Http.HttpClient.getSafeUUID;

public class GetDocScheduleBean {
    private String docId;
    private String deptId;
    private String docName;
    private String subjectId;
    private String schType = "0";
    private String safeUniqueCode = getSafeUUID();

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getDocName() {
        return this.docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocId() {
        return this.docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSafeUniqueCode() {
        return this.safeUniqueCode;
    }

    public void setSafeUniqueCode(String safeUniqueCode) {
        this.safeUniqueCode = safeUniqueCode;
    }

    public String getSchType() {
        return this.schType;
    }

    public void setSchType(String schType) {
        this.schType = schType;
    }
}
