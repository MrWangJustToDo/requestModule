package com.java.request.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component("clientService")
public class HttpClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Resource(name = "httpClient")
    private CloseableHttpClient httpClient;
    @Resource(name = "requestConfig")
    private RequestConfig requestConfig;

    private static final int SUCCESS_CODE = 200;

    public static URIBuilder getUriBuilder(String url) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uriBuilder;
    }

    public static List<NameValuePair> getParams(Object[] params, Object[] values) {
        boolean flag = values.length > 0 && params.length == values.length;
        if (flag) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for (int i = 0; i < params.length; i++) {
                nameValuePairList.add(new BasicNameValuePair(params[i].toString(), values[i].toString()));
            }
            return nameValuePairList;
        } else {
            LOGGER.error("HttpClientService-errorMsg：{}", "请求参数为空/参数长度不一致");
        }
        return null;
    }

    public String doGet(String url) throws IOException {
        return doGet(url, null);
    }

    public String doGet(String url, List<NameValuePair> nameValuePairList)
            throws IOException {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = HttpClientService.getUriBuilder(url);
            if (uriBuilder != null) {
                if (nameValuePairList != null) {
                    uriBuilder.addParameters(nameValuePairList);
                }
                HttpGet httpGet = new HttpGet(uriBuilder.build());

                httpGet.setConfig(this.requestConfig);

                httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));

                httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

                response = httpClient.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (SUCCESS_CODE == statusCode) {
                    HttpEntity entity = response.getEntity();
                    return EntityUtils.toString(entity, "UTF-8");
                } else {
                    LOGGER.error("HttpClientService-statusCode: {}", statusCode);
                }
            }
        } catch (Exception e) {
            LOGGER.error("HttpClientService-Exception: {}", e.toString());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    public String doPost(String url) throws Exception {
        return doPost(url, null);
    }

    public String doPost(String url, List<NameValuePair> nameValuePairList) throws Exception {
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(this.requestConfig);
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            response = httpClient.execute(post);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == SUCCESS_CODE) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                LOGGER.error("HttpClientService-statusCode: {}", statusCode);
            }
        } catch (Exception e) {
            LOGGER.error("HttpClientService-Exception: {}", e.toString());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

//    public static void main(String[] args) {
//        String url = "https://ixyzero.com/blog/awk_sed.txt";
//        // 参数值
//        Object[] params = new Object[]{"param1", "param2"};
//        // 参数名
//        Object[] values = new Object[]{"value1", "value2"};
//        // 获取参数对象
//        List<NameValuePair> paramsList = HttpClientService.getParams(params, values);
//
//        // 发送get
//        String result = null;
//        try {
////            result = sendGet(url, paramsList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("GET返回信息：\n" + result);
//
//        // 发送get
//        result = null;
//        try {
//            // result = sendHttpsGet(url, paramsList);
////            result = sendHttpsGet(url, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("[HTTPS]GET返回信息：\n" + result);
//
//        // 发送post
//        url = "https://httpbin.org/post";
//        result = null;
//        try {
////            result = sendPost(url, paramsList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("POST返回信息：\n" + result);
//
//        // 发送post
//        url = "https://ixyzero.com/blog/wp-comments-post.php";
//        result = null;
//        try {
////            result = sendHttpsPost(url, paramsList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("[HTTPS]POST返回信息：\n" + result);
//
//    }
}
