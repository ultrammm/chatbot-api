package com.ai.chatbot.api.test;


import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

@Test
public class ApiTest {
    public void query_unanswered_question() throws IOException {
        //封装数据信息
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=by_owner&count=20");
        get.addHeader("cookie", "zsxq_access_token=BDF9107B-67FF-B439-9CE7-0F1B155B47B0_68197274045DF936; zsxqsessionid=a660efcd61cb7537c3decbdee3bf54f0; abtest_env=product");
        get.addHeader("Content-Type", "application/json, text/plain, */*");

        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
        else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
    public void answer() throws IOException {
        CloseableHttpClient httpClient=HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/2858282818218251/comments");
        post.addHeader("cookie", "zsxq_access_token=BDF9107B-67FF-B439-9CE7-0F1B155B47B0_68197274045DF936; zsxqsessionid=a660efcd61cb7537c3decbdee3bf54f0; abtest_env=product");
        post.addHeader("Content-Type", "application/json, text/plain, */*");


        String paramJson="{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"你猜\\n\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";


        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
        else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
