package sample.Bean.HXYYBean;

public class SearchRetBeanDataSectItems {

    private int sectTypeXh;
    private String sectId;
    private String hosDistName = "亚专业";
    private String sectName;
    private String hosDistId;
    //================ 第二部分 =================
    private String deptName;
    private String deptId;
    private String deptAddr;
    //第三部分
    private String subjectId;
    private String hosId32;
    private String subjectName;

    @Override
    public String toString() {
        return hosDistName + "-" +

                (subjectName == null ? (sectName == null ? "门诊-" + deptName : "科室-" + sectName) : subjectName);
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

    //============  第二部分结束

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptAddr() {
        return this.deptAddr;
    }

    public void setDeptAddr(String deptAddr) {
        this.deptAddr = deptAddr;
    }

    public int getSectTypeXh() {
        return sectTypeXh;
    }

    public void setSectTypeXh(int sectTypeXh) {
        this.sectTypeXh = sectTypeXh;
    }

    public String getSectId() {
        return sectId;
    }

    public void setSectId(String sectId) {
        this.sectId = sectId;
    }

    public String getHosDistName() {
        return hosDistName;
    }

    public void setHosDistName(String hosDistName) {
        this.hosDistName = hosDistName;
    }

    public String getSectName() {
        return sectName;
    }

    public void setSectName(String sectName) {
        this.sectName = sectName;
    }

    public String getHosDistId() {
        return hosDistId;
    }

    public void setHosDistId(String hosDistId) {
        this.hosDistId = hosDistId;
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
    //第三部分结束
}
