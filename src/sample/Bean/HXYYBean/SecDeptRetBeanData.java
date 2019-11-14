package sample.Bean.HXYYBean;

public class SecDeptRetBeanData {
    private String deptName;
    private String deptId;
    private String hosDistId;
    private String hosId32;

    @Override
    public String toString() {
        return deptName;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getHosDistId() {
        return this.hosDistId;
    }

    public void setHosDistId(String hosDistId) {
        this.hosDistId = hosDistId;
    }

    public String getHosId32() {
        return this.hosId32;
    }

    public void setHosId32(String hosId32) {
        this.hosId32 = hosId32;
    }
}
