package com.ai.chatbot.api.domain.zsxq;

import com.ai.chatbot.api.domain.zsxq.model.aggregates.UnansweredQuestionsAggregates;

import java.io.IOException;

public interface IZsxqApi {
    UnansweredQuestionsAggregates queryUnansweredQuestionsTopicId(String groupId, String cookie)throws IOException;

    boolean answer(String groupId, String topicId, String cookie, String text,boolean silenced) throws IOException;

}
