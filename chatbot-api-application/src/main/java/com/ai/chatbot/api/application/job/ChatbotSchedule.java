package com.ai.chatbot.api.application.job;



import com.ai.chatbot.api.domain.ai.IOpenAI;
import com.ai.chatbot.api.domain.zsxq.IZsxqApi;
import com.ai.chatbot.api.domain.zsxq.model.aggregates.UnansweredQuestionsAggregates;
import com.ai.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

//问题任务
@EnableScheduling//开启定时任务
@Configuration
public class ChatbotSchedule {
    private int count=0;
    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    //表达式：cron.qqe2.com
    @Scheduled(cron = "0/30 * * * * ? ")
    public void  run(){
        try {
            if(count>=20){
                return;
            }
            //为了防止风控，进行随机打烊
            if(new Random().nextBoolean()){
                logger.info("随机打烊中");
                return;
            }
            GregorianCalendar calendar=new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour>=22||hour<7){
                logger.info("随机打烊中");
                return;
            }

            //1.检索问题
            UnansweredQuestionsAggregates unansweredQuestionsAggregates= zsxqApi.queryUnansweredQuestionsTopicId(groupId, cookie);
            logger.info("检索结果:{}", JSON.toJSONString(unansweredQuestionsAggregates));
            List<Topics> topics=unansweredQuestionsAggregates.getResp_data().getTopics();
            if(null== topics||topics.isEmpty()){
                logger .info("本次检索未查询到待回答问题");
                return;
            }
            //2.调用chatgpt回答

            Topics topic=topics.get(count);//一次只回答一个，防止风控
            String answer = openAI.doChatGpt(topic.getTalk().getText());
            //3.回答问题
            boolean status = zsxqApi.answer(groupId, topic.getTopic_id(), cookie, answer, false);
            if (status){
                count++;
            }
            logger.info("编号：{} 问题：{} 回答：{} 状态：{}",topic.getTopic_id(),topic.getTalk().getText(),answer,status);

        }catch (Exception e){
            logger.error("自动回答问题异常",e);

        }
    }


}
