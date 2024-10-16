package com.ai.chatbot.api.domain.ai.service;

import com.ai.chatbot.api.domain.ai.IOpenAI;
import com.ai.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.ai.chatbot.api.domain.ai.model.vo.Choices;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class OpenAI implements IOpenAI {
    private Logger logger = (Logger) LoggerFactory.getLogger(OpenAI.class);
    @Value("${chatbot-api.openAIKey}")
    private String openAIKey;


    @Override
    public String doChatGpt(String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        post.addHeader("Authorization", "Bearer " + openAIKey);
        post.addHeader("Content-Type", "application/json");

        String paramJson = "{\n" +
                "    \"model\": \"glm-4\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"" + question + "\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("open.bigmodel.cn Err Code is" + response.getStatusLine().getStatusCode());
        }
    }
}

