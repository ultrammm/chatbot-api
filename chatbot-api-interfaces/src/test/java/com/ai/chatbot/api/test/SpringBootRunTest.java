package com.ai.chatbot.api.test;

import com.ai.chatbot.api.domain.ai.IOpenAI;
import com.ai.chatbot.api.domain.zsxq.IZsxqApi;
import com.ai.chatbot.api.domain.zsxq.model.aggregates.UnansweredQuestionsAggregates;
import com.ai.chatbot.api.domain.zsxq.model.vo.Topics;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    public SpringBootRunTest() throws IOException {
    }

    @Test
    public void text_zsxqApi() throws IOException {
        UnansweredQuestionsAggregates unansweredQuestionsAggregates= zsxqApi.queryUnansweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果:{}", JSON.toJSONString(unansweredQuestionsAggregates));
        List<Topics> topics=unansweredQuestionsAggregates.getResp_data().getTopics();
        for(Topics topic:topics){
            String topic_id= topic.getTopic_id();
            String text=topic.getTalk().getText();

            logger.info("topic_id:{},text:{}",topic_id,text);
            //回答问题
            zsxqApi.answer(groupId,topic_id,cookie,text,false);
        }
    }

    @Test
    public void test_openAi()throws IOException {
        String question="帮我写一个java冒泡排序";
        String answer=openAI.doChatGpt(question);
        logger.info("测试结果:{}",answer);
    }
}
