package com.ai.chatbot.api.domain.zsxq.model.vo;

import java.util.ArrayList;
import java.util.List;
public class UserSpecific
{
    private boolean liked;

    private List<String> liked_emojis;

    private boolean subscribed;

    public void setLiked(boolean liked){
        this.liked = liked;
    }
    public boolean getLiked(){
        return this.liked;
    }
    public void setLiked_emojis(List<String> liked_emojis){
        this.liked_emojis = liked_emojis;
    }
    public List<String> getLiked_emojis(){
        return this.liked_emojis;
    }
    public void setSubscribed(boolean subscribed){
        this.subscribed = subscribed;
    }
    public boolean getSubscribed(){
        return this.subscribed;
    }
}

