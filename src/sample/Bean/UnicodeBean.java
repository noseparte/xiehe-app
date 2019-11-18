package sample.Bean;

import static sample.Http.HttpClient.getSafeUUID;

public class UnicodeBean {
    private long terminalTime;
    private String devModel;
    private String safeUniqueCode = getSafeUUID();
    private int terminalType;

    public long getTerminalTime() {
        return this.terminalTime;
    }

    public void setTerminalTime(long terminalTime) {
        this.terminalTime = terminalTime;
    }

    public String getDevModel() {
        return this.devModel;
    }

    public void setDevModel(String devModel) {
        this.devModel = devModel;
    }

    public int getTerminalType() {
        return this.terminalType;
    }

    public void setTerminalType(int terminalType) {
        this.terminalType = terminalType;
    }
}
