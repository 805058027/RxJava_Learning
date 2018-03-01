package com.example.administrator.rxjava_learning.net.client;

import android.content.Context;

import com.example.administrator.rxjava_learning.App.AppContext;
import com.example.administrator.rxjava_learning.net.Logger;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class MyOkHttpClient {
    private static Context mContext = AppContext.getInstance().getContext();

    private static Logger logger = Logger.getLogger();

    private static OkHttpClient mHttpClient = null;

    private static PersistentCookieStore cookieStore;

    public static PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    // 将构造函数封掉，只能通过对外接口来获取HttpClient实例
    private MyOkHttpClient() {

    }

    public static synchronized OkHttpClient getSaveHttpClient() {
        if (mHttpClient == null) {
            cookieStore = new PersistentCookieStore(mContext);
            try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance
                        (TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(getkeyStore());
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:" + Arrays
                            .toString(trustManagers));
                }
                X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager}, null);
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        logger.i("", hostname);
                        return true;
                    }
                };

                mHttpClient = new OkHttpClient.Builder()
                        .cache(new Cache(mContext.getExternalCacheDir(), 10 * 1024 * 1024))
                        .sslSocketFactory(sslSocketFactory, trustManager)
                        .hostnameVerifier(hostnameVerifier)
                        .addInterceptor(new LoggerInterceptor())
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .cookieJar(new CookiesManager())
                        .build();
                return mHttpClient;
            } catch (Exception e) {
                logger.e(e);
            }
        }
        return mHttpClient;
    }

    /*
     * 导入证书
     * 将证书放入 assets/certs/目录下，即可自动导入
     */
    private static KeyStore getkeyStore() {
        // 添加https证书
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

            String[] certFiles = mContext.getAssets().list("certs");
            if (certFiles == null) return keyStore;

            int index = 0;
            for (String cert : certFiles) {
                InputStream certificate = mContext.getAssets().open("certs/" + cert);
                String alias = Integer.toString(index++);
                keyStore.setCertificateEntry(alias, certificateFactory.generateCertificate
                        (certificate));

                if (certificate != null) certificate.close();
            }
            return keyStore;
        } catch (Exception e) {
            logger.e(e);
        }
        return null;
    }

    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar {

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            return cookieStore.get(url);
        }
    }
}