package com.rxsearchdemo.ReminderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by whiz-010 on 23-Oct-17 at 12:22 PM.
 */

public class ReminderHistoryResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("reminderId")
        @Expose
        private String reminderId;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("group_to_ary")
        @Expose
        private String groupToAry;
        @SerializedName("send_to_ary")
        @Expose
        private String sendToAry;
        @SerializedName("email_notify")
        @Expose
        private String emailNotify;
        @SerializedName("text_msg_notify")
        @Expose
        private String textMsgNotify;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getReminderId() {
            return reminderId;
        }

        public void setReminderId(String reminderId) {
            this.reminderId = reminderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getGroupToAry() {
            return groupToAry;
        }

        public void setGroupToAry(String groupToAry) {
            this.groupToAry = groupToAry;
        }

        public String getSendToAry() {
            return sendToAry;
        }

        public void setSendToAry(String sendToAry) {
            this.sendToAry = sendToAry;
        }

        public String getEmailNotify() {
            return emailNotify;
        }

        public void setEmailNotify(String emailNotify) {
            this.emailNotify = emailNotify;
        }

        public String getTextMsgNotify() {
            return textMsgNotify;
        }

        public void setTextMsgNotify(String textMsgNotify) {
            this.textMsgNotify = textMsgNotify;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
