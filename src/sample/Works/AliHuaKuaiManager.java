// AliHuaKuaiManager.java
package sample.Works;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import sample.Bean.AliWebBean;

import java.io.IOException;
import java.util.List;

@Data
public class AliHuaKuaiManager {
    private int limit = -1;
    private static List<AliWebBean> mSigBeans;
    private String user = "";
    private String pass = "";
    private static int errorSize = 10;
    private static AliHuaKuaiManager instance;

    public static AliHuaKuaiManager getInstance() {
        return instance;
    }

    public static AliHuaKuaiManager getInstance(String user, String pass, int loaderLimit) {
        if (instance == null) {
                setUserAndPass(user, pass, loaderLimit);
        }
        return instance;
    }

    private static void callback(int size) {
        errorSize = size;
    }

    private static void setUserAndPass(String user, String pass, int limit) {
        (new Thread((Runnable) (new Runnable() {
            public final void run() {
                int i = 1;
                int var2 = limit;
                if (i <= var2) {
                    while (true) {
                        String var3 = "开始初始化滑块数据 " + i + "...";
                        boolean var4 = false;
                        System.out.println(var3);
                        requestNew();
                        if (i == var2) {
                            break;
                        }

                        ++i;
                    }
                }

            }
        }))).start();
    }

    public AliWebBean getSingle() {
        try {
            synchronized (this) {
                while (mSigBeans.size() <= 0) {
                    System.out.println("数据库为空,正在等待滑块数据返回...");
                    Thread.sleep(100);
                    AliWebBean data = mSigBeans.get(0);
                    mSigBeans.remove(0);
                    System.out.println("消耗了一个");
                    if (mSigBeans.size() <= limit) {
                        requestNew();
                        System.out.println("正在请求新的滑块数据...");
                    }
                    return data;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void requestNew() {
        String url = "http://api.yaomy.net/api/data/get";
        OkHttpClient client = (new Builder()).build();
        Request request = (new okhttp3.Request.Builder()).url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue((Callback) (new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                JSONObject result = JSONObject.parseObject(response.body().string());
                String resultData = result.getString("resultdata");
                JSONObject dataObj = JSONObject.parseObject(resultData);
                String token = dataObj.getString("token");
                String sig = dataObj.getString("sig");
                String sessionid = dataObj.getString("sessionid");
                if (sig != null) {
                    AliWebBean aliWebBean = new AliWebBean(sig, sessionid, token);
                    mSigBeans.add(aliWebBean);
                    callback(mSigBeans.size());
                    System.out.println("加入一个新的滑块数据");
                } else {
                    errorSize = 10;
                    requestNew();
                }
            }

            public void onFailure(Call call, IOException e) {
                if (errorSize < 0) {
                    errorSize = 10;
                } else {
                    errorSize = errorSize + -1;
                    requestNew();
                }

            }
        }));
    }


}
