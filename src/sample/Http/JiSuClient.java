package sample.Http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class JiSuClient {

    private static JiSuClient mJiSu;
    private JiSuInterface mClients;

    public static JiSuClient getInstance() {
        if (mJiSu == null) {
            mJiSu = new JiSuClient();
        }
        return mJiSu;
    }

    public void destroy() {
        mJiSu = null;
    }

    public JiSuInterface getClient() {
        if (mClients == null) {
            OkHttpClient mClient;
            mClient = new OkHttpClient.Builder()
                    .hostnameVerifier((s, sslSession) -> true)//校验名称,这个对象就是信任所有的主机,也就是信任所有https的请求
                    .connectTimeout(10, TimeUnit.SECONDS)//连接超时时间
                    .readTimeout(10, TimeUnit.SECONDS)//读取超时时间
                    .writeTimeout(10, TimeUnit.SECONDS)//写入超时时间
                    .retryOnConnectionFailure(false)//连接不上是否重连,false不重连
                    .build();
            String baseUrl = "http://101.201.172.36";
            mClients = new Retrofit.Builder().baseUrl(baseUrl)
                    .client(mClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(JiSuInterface.class);
        }
        return mClients;
    }
}
