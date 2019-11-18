package sample.Bean;

public class RequestPayRetBean {
    private String msg;
    private boolean result;
    private RequestPayRetBeanData data;
    private String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

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

    public RequestPayRetBeanData getData() {
        return this.data;
    }

    public void setData(RequestPayRetBeanData data) {
        this.data = data;
    }
}
