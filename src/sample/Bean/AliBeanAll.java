package sample.Bean;

public class AliBeanAll {
    /**
     * slider_sig
     */
    private String sig;

    /**
     * slider_sessionid
     */
    private String nc_token;
    private int code;

    /**
     * slider_sessionid
     */
    private String csessionid;

    public String getSig() {
        return this.sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getNc_token() {
        return this.nc_token;
    }

    public void setNc_token(String nc_token) {
        this.nc_token = nc_token;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCsessionid() {
        return this.csessionid;
    }

    public void setCsessionid(String csessionid) {
        this.csessionid = csessionid;
    }
}
