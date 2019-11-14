package sample.Bean;

public class LoginBean {
    private String msg;
    private boolean result;
    private LoginBeanData data;
    private String kind;
    private String tracerId;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public LoginBeanData getData() {
        return this.data;
    }

    public void setData(LoginBeanData data) {
        this.data = data;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTracerId() {
        return this.tracerId;
    }

    public void setTracerId(String tracerId) {
        this.tracerId = tracerId;
    }
}
