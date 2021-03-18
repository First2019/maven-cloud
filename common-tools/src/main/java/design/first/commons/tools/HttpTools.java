package design.first.commons.tools;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * http工具类
 */
@Slf4j
public class HttpTools {
    private static final String POST = "POST";
    private static final String GET = "GET";

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri=new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(9011)
                .setPath("/rabbit/send")
                .build();
//        HttpResponse httpResponse = get(uri);
//        System.out.println(JSON.toJSONString(httpResponse));
        dealResponse(get(uri));
    }

    public static HttpResponse get(URI uri) throws IOException {
        return http(uri, GET);
    }

    //post请求获取响应
    public static HttpResponse post(URI uri) throws IOException {
        return http(uri, POST);
    }

    public static HttpResponse http(URI uri, final String method) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .build();

        HttpClient client = HttpClients.createDefault();
        //HttpPost post = new HttpPost(uri);
        HttpEntityEnclosingRequestBase http = new HttpEntityEnclosingRequestBase() {
            @Override
            public String getMethod() {
                return method;
            }
        };
        http.setURI(uri);
        http.setConfig(requestConfig);
        log.info("\n发起http请求:{}", JSON.toJSONString(uri));
        HttpResponse response = client.execute(http);
        return response;
    }


    public static HttpResponse postByJson(URI uri, Map<String,Object> requestBody) throws IOException {
        return postByJson(uri,requestBody,null);
    }
    public static HttpResponse postByJson(URI uri, Map<String,Object> requestBody, Map<String,String> header) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        //添加header信息
        Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            post.setHeader(next.getKey(),next.getValue());
        }

        //使用addHeader方法添加请求头部,诸如User-Agent, Accept-Encoding等参数.
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setHeader("conn","看得到我吗");
        // 组织数据
        StringEntity se = new StringEntity(JSON.toJSONString(requestBody));
        //设置编码格式
        se.setContentEncoding(Consts.UTF_8.toString());
        //设置数据类型
        se.setContentType("application/json");
        //对于POST请求,把请求体填充进HttpPost实体.
        post.setEntity(se);
        HttpResponse execute = client.execute(post);
        return execute;
    }

    public static String dealResponse(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity resEntity = response.getEntity();
            String message = EntityUtils.toString(resEntity, Consts.UTF_8);
            System.out.println(message);
            return message;
        } else {
            System.out.println("请求失败");
            return "请求失败";
        }
    }
}
