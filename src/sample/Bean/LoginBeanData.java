package sample.Bean;

public class LoginBeanData {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
    private long phoneNo;
    private int usId;

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return this.refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return this.token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public long getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getUsId() {
        return this.usId;
    }

    public void setUsId(int usId) {
        this.usId = usId;
    }
}
