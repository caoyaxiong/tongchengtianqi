package com.tongchengtianqi.zuitoutiao.entity;

import java.util.List;

import com.tongchengtianqi.zuitoutiao.easytagdragview.bean.Tip;


public class TabTextBean implements Tip {

    public int status;
    public DataBean data;
    public int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public List<ChannelsBean> channels;

        public List<ChannelsBean> getChannels() {
            return channels;
        }

        public void setChannels(List<ChannelsBean> channels) {
            this.channels = channels;
        }

        public static class ChannelsBean {
            /**
             * cid : 1
             * cname : 推荐
             */
            public int cid;
            public String cname;

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }
        }
    }
}
