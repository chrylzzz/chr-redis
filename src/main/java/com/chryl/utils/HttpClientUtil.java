package com.chryl.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created By Chr.yl on 2022-04-29.
 *
 * @author Chr.yl
 */
@Slf4j
public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            //超时断开连接
            //setConnectTimeout：设置连接超时时间，单位毫秒。
            //setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。
            //这个属性是新加的属性，因为目前版本是可以共享连接池的。
            //setSocketTimeout：请求获取数据的超时时间，单位毫秒。
            //如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(1000) //连接超时时间
                    .setConnectionRequestTimeout(1000) //从线程池中获取线程超时时间
                    .setSocketTimeout(3000) //设置数据超时时间

                    .setStaleConnectionCheckEnabled(true) //提交请求前检查连接是否可用
                    .build();

            httpGet.setConfig(config);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("你的代码报错啦: method: doGet , exception: {} ", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //setConnectTimeout：设置连接超时时间，单位毫秒。
            //setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。
            //这个属性是新加的属性，因为目前版本是可以共享连接池的。
            //setSocketTimeout：请求获取数据的超时时间，单位毫秒。
            //如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(1000) //连接超时时间
                    .setConnectionRequestTimeout(1000) //从线程池中获取线程超时时间
                    .setSocketTimeout(3000) //设置数据超时时间

                    .setStaleConnectionCheckEnabled(true)//提交请求前检查连接是否可用
                    .build();
            httpPost.setConfig(defaultRequestConfig);

//            httpPost.setHeader("");
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("你的代码报错啦: method: doPost , exception: {} ", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //setConnectTimeout：设置连接超时时间，单位毫秒。
            //setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。
            //这个属性是新加的属性，因为目前版本是可以共享连接池的。
            //setSocketTimeout：请求获取数据的超时时间，单位毫秒。
            //如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(3000) //连接超时时间
                    .setConnectionRequestTimeout(3000) //从线程池中获取线程超时时间
                    .setSocketTimeout(3000) //设置数据超时时间

                    .setStaleConnectionCheckEnabled(true)//提交请求前检查连接是否可用
                    .build();
            httpPost.setConfig(defaultRequestConfig);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            //设置头信息
//            httpPost.setHeader("yjw_context", StringSizeUtil.stringStrSize());
            httpPost.setHeader("appId", "332631337");
            httpPost.setHeader("appKey", "33f1272db47248ebe87275d1d69c55632461c0e5f2c74fc802391a0dcbf4110c.1761815914190");

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("你的代码报错啦: method: doPostJson , exception: {} ", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    private static String accessId = "77e790e9";
    private static String uri = "/91d40e14/iat";
    private static String secretKey = "702f4b2e8b";
    private static String appId = "20220421000001";

    public static void main(String[] args) {
        System.out.println(
                getASRBody()
        );
    }

    public static String getASRBody() {
        JSONObject json_params = new JSONObject();
        json_params.put("appid", appId);
        json_params.put("aue", "raw");
        json_params.put("cmd", "ssb");
        json_params.put("auf", "audio/L16;tate=16000");
        json_params.put("online", "off");
        json_params.put("audioStatus", "1");
        JSONObject params = new JSONObject();
        params.put("params", "seginfo=1,vspp=0,offline=on,area=anhui,ability=ab_asr,engine_name=iat_offline");
        json_params.put("extend_params", params);
        json_params.put("svc", "iat");
        json_params.put("syncid", "0");

        JSONObject json_body = new JSONObject();
        json_body.put("jsonrpc", "2.0");
        json_body.put("method", "deal_request");
        json_body.put("params", json_params);
        json_body.put("id", 1);
        log.info("asr body:\n{}", JSON.toJSONString(json_body, JSONWriter.Feature.PrettyFormat));
        return json_body.toJSONString();
    }

    public static String doPostJsonByASR(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //setConnectTimeout：设置连接超时时间，单位毫秒。
            //setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。
            //这个属性是新加的属性，因为目前版本是可以共享连接池的。
            //setSocketTimeout：请求获取数据的超时时间，单位毫秒。
            //如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(3000) //连接超时时间
                    .setConnectionRequestTimeout(3000) //从线程池中获取线程超时时间
                    .setSocketTimeout(3000) //设置数据超时时间

                    .setStaleConnectionCheckEnabled(true)//提交请求前检查连接是否可用
                    .build();
            httpPost.setConfig(defaultRequestConfig);
            /**
             * init body
             */
            json = getASRBody();
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            String time = String.valueOf(System.currentTimeMillis() / 1000);
//            String token = ASRTokenUtil.getToken(uri, accessId, appId, secretKey, time);
            String token = "";
//            log.info("time: [{}], token: [{}]", time, token);

            /**
             * init header
             */
            // 设置请求头Content-Type
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setHeader("accessId", accessId);
            httpPost.setHeader("applicationId", appId);
            httpPost.setHeader("time", time.trim());
            httpPost.setHeader("accessToken", token);

            // 打印请求头
            JSONObject jsonObject = new JSONObject();
            for (Header header : httpPost.getAllHeaders()) {
                jsonObject.put(header.getName(), header.getValue());
            }
            log.info("asr header:\n{}", JSON.toJSONString(jsonObject, JSONWriter.Feature.PrettyFormat));

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("你的代码报错啦: method: doPostJson , exception: {} ", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        log.info("result:[{}]", resultString);
        return resultString;
    }

}
