package com.tongchengtianqi.zuitoutiao.http;


public interface Urls {
    /**
     * 主机
     */
    String HOST = "http://api.flow.adline.com.cn";
    /*详情页*/
    String DETAIL_PAGES = "http://m.adline.com.cn";
    /*http://api.flow.adline.com.cn/api/v2/article?app_id=1&c=0&cid=1&category_id=0&page_token=0&article_id=0&create_time=0*/
    /*频道数据*/
    String CHANNEL_DATA = HOST + "/api/v2/channel";
    /*信息流数据*/
    String INFORMATION_DATA = HOST + "/api/v2/article";
    /*详情页数据*/
    String DETAIL_PAGE_DATA = HOST + "/api/v2/article/detail";
    /*评论数据*/
    String REVIEW_DATA = HOST + "/api/v2/comment";
    /*评论提交*/
    String COMMENTS_SUBMITTED = HOST + "/api/v2/comment/create";
    /*反馈互动*/
    String FEEDBACK_INTERACTION = HOST + "/api/v2/feedback";


}