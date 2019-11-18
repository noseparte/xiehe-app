package sample.Bean;

public class GetNewsBean {
    private boolean result;
    private GetNewsBeanData[] data;

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public GetNewsBeanData[] getData() {
        return this.data;
    }

    public void setData(GetNewsBeanData[] data) {
        this.data = data;
    }
}
