package sample.Http;

import com.google.gson.Gson;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.Bean.ProxyType;
import sample.Bean.UnicodeBean;
import sample.Safe.AESEncryptor;
import sample.Safe.RSAEncryptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HttpClient {
    private static final String version = "2.12.2";
    private static final String getPackageName = "com.hundsun.qy.hospitalcloud.bj.xhhosp.hsyy";
    private ClientInterface mHttp;
    private String BaseUrl = "https://xh2.hsyuntai.com:8663";
    private String unicode = "";
    private int timelag = 0;
    private String access_Token = "";

    public HttpClient(ProxyType proxy) {
        OkHttpClient mOkHttp = new OkHttpClient.Builder()
                //.addInterceptor(new LogInterceptor())//添加okhttp日志拦截器
                //不加以下两行代码,https请求不到自签名的服务器
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getWebIp(), proxy.getWebSocket())))
                .sslSocketFactory(createSSLSocketFactory())//创建一个证书对象
                .hostnameVerifier((s, sslSession) -> true)//校验名称,这个对象就是信任所有的主机,也就是信任所有https的请求
                .connectTimeout(30, TimeUnit.SECONDS)//连接超时时间
                .readTimeout(15, TimeUnit.SECONDS)//读取超时时间
                .writeTimeout(15, TimeUnit.SECONDS)//写入超时时间
                .retryOnConnectionFailure(true)//连接不上是否重连,false不重连
                .addInterceptor(chain ->
                {
                    Request original = chain.request();
                    String str = String.valueOf(System.currentTimeMillis() + timelag);
                    String sign = getPublicKeySignature(str);
                    String getSsion = "";
                    if (original.url().toString().equals("https://xh2.hsyuntai.com:8663/hs-udb-resource/r/10001/100")) {
                        long time = System.currentTimeMillis();
                        sign = RSAEncryptor.sign("{\"terminalTime\":" + time + ",\"devModel\":\"MI 5s\",\"terminalType\":1}");
                        UnicodeBean data = new UnicodeBean();
                        data.setTerminalTime(time);
                        data.setDevModel("MI 5s");
                        data.setTerminalType(1);
                        getSsion = new Gson().toJson(data);
                    }
                    String signal = getSignalEncryInfo(getPackageName + "+" + System.currentTimeMillis() + "+" + version);

                    String authHeader = "";
                    if (proxy != null) {
                        // 定义申请获得的appKey和appSecret
                        // 创建参数表
                        Map<String, String> paramMap = new HashMap<>();
                        paramMap.put("app_key", proxy.getKey());
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));//使用中国时间，以免时区不同导致认证错误
                        paramMap.put("timestamp", format.format(new Date()));

                        // 对参数名进行排序
                        String[] keyArray = paramMap.keySet().toArray(new String[0]);
                        Arrays.sort(keyArray);

                        // 拼接有序的参数名-值串
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(proxy.getSecret());
                        for (String key : keyArray) {
                            stringBuilder.append(key).append(paramMap.get(key));
                        }
                        stringBuilder.append(proxy.getSecret());
                        String codes = stringBuilder.toString();

                        byte[] secretBytes;
                        try {
                            secretBytes = MessageDigest.getInstance("md5").digest(
                                    codes.getBytes());
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException("没有这个md5算法！");
                        }
                        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
                        for (int i = 0; i < 32 - md5code.length(); i++) {
                            md5code.insert(0, "0");
                        }
                        // 拼装请求头Proxy-Authorization的值，这里使用 guava 进行map的拼接
                        authHeader = "MYH-AUTH-MD5 sign=" + md5code.toString().toUpperCase() + "&app_key=" + proxy.getKey() + "&timestamp=" + format.format(new Date());
//                    System.out.println(authHeader);
                    }
                    return chain.proceed(
                            original.newBuilder()
                                    .header("Proxy-Authorization", authHeader)
                                    .header("unicode", unicode)
                                    .header("client_id", "999998@173")
                                    .header("signature", sign)
                                    .header("access_token", access_Token)
                                    .header("version", version)
                                    .header("terminalType", "1")
                                    .header("signal", signal)
                                    .header("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 7.1.2; MI 5s Build/NJH47F)")
                                    .method(original.method(), !getSsion.equals("") ? okhttp3.RequestBody.create(MediaType.parse("application/json;charset=utf-8"), getSsion) : original.body())
                                    .build()
                    );
                })
                .build();
        mHttp = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttp)
                .build()
                .create(ClientInterface.class);
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
        }
        return ssfFactory;
    }

    public static String getPublicKeySignature(String str) {
        try {
            return Base64.getEncoder().encodeToString(new RSAEncryptor().encrypt(str.getBytes())).replaceAll("\\n", "");
        } catch (Exception unused) {
            return null;
        }
    }

    private static String getSignalEncryInfo(String str) {
        try {
            return Base64.getEncoder().encodeToString(new AESEncryptor().signalEncrypt(str, "4d3dd7229612473a")).replaceAll("\\n", "");
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getSafeUUID() {
        return String.valueOf(System.currentTimeMillis()) + UUID.randomUUID();
    }

    public void setAccess_Token(String access_Token) {
        this.access_Token = access_Token;
    }

    public void setUnicodeOlag(String unicode, int timelag) {
        this.unicode = unicode;
        this.timelag = timelag;
    }

    public ClientInterface getmHttp() {
        return mHttp;
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response ret = chain.proceed(chain.request());
            System.out.println(ret.body().string());
            return ret;
        }
    }
}
