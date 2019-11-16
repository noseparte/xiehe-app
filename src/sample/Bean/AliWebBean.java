package sample.Bean;

public class AliWebBean {
    private String sig;
    private String cses;
    private String token;

    public String getSig() {
        return this.sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getCses() {
        return this.cses;
    }

    public void setCses(String cses) {
        this.cses = cses;
    }

    public AliWebBean() {
    }

    public AliWebBean(String sig, String cses, String token) {
        this.sig = sig;
        this.cses = cses;
        this.token = token;
    }

    public String getToken() {
        if (this.token.equals("")) return "数据错误!";
        return this.token.split("\\u0026scene\\u003d")[0].replaceAll("%3A", ":");
    }

    public void setToken(String token) {
        this.token = token;
    }
}
