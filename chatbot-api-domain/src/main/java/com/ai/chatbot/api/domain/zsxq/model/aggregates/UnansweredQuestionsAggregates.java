package com.ai.chatbot.api.domain.zsxq.model.aggregates;

import com.ai.chatbot.api.domain.zsxq.model.req.ReqData;
import com.ai.chatbot.api.domain.zsxq.model.res.RespData;

public class UnansweredQuestionsAggregates {
    private boolean succeeded;
    private RespData resp_data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public RespData getResp_data() {
        return resp_data;
    }

    public void setResp_data(RespData resp_data) {
        this.resp_data = resp_data;
    }
}
