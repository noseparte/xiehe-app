package sample.Bean;

public class UnicodeGet {
    private boolean result;
    private UnicodeGetData data;
    private String kind;
    private String msg;

    public boolean isResult() {
        return result;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getMsg() {
        return msg;
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

    public UnicodeGetData getData() {
        return this.data;
    }

    public void setData(UnicodeGetData data) {
        this.data = data;
    }
}
