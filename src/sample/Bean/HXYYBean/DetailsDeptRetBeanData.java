package sample.Bean.HXYYBean;

public class DetailsDeptRetBeanData {
    private String deptId;
    private int hosId;
    private String subjectId;
    private String hosId32;
    private String subjectName;

    @Override
    public String toString() {
        return subjectName;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public int getHosId() {
        return this.hosId;
    }

    public void setHosId(int hosId) {
        this.hosId = hosId;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getHosId32() {
        return this.hosId32;
    }

    public void setHosId32(String hosId32) {
        this.hosId32 = hosId32;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
