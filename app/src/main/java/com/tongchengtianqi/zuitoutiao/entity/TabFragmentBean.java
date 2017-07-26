package com.tongchengtianqi.zuitoutiao.entity;


import java.util.List;

public class TabFragmentBean {


    /**
     * status : 0
     */

    private int status;
    private DataBean data;

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
        private List<ArticlesBean> articles;

        public List<ArticlesBean> getArticles() {
            return articles;
        }

        public void setArticles(List<ArticlesBean> articles) {
            this.articles = articles;
        }

        public static class ArticlesBean {
            /**
             * article_id : 217067295905690402
             * title : 叶全真近照, 年近半百看似30少女, 一席深V礼服丰腴犹存
             * summary :
             * url : /uc/article/217067295905690402?app_id=1&cid=1&adline_newsdn=fhk2dsa47829jfdsaf434&c=null
             * content :
             * source_name : 大鱼不哭
             * source_url : 大鱼不哭
             * tags : []
             * comment_count : 0
             * style_type : 5
             * page_token : 6681017897677754979
             * thumbnails : [{"url":"http://image.uczzd.cn/15934407251257692872.jpg?id=0&from=export","width":720,"height":720,"type":"jpg"},{"url":"http://image.uczzd.cn/3848819349590125636.jpg?id=0&from=export","width":750,"height":750,"type":"jpg"},{"url":"http://image.uczzd.cn/10560136905175933910.jpg?id=0&from=export","width":750,"height":750,"type":"jpg"}]
             * create_time : 1499823838709
             * publish_time : 1498619460000
             */

            private String article_id;
            private String title;
            private String summary;
            private String url;
            private String content;
            private String source_name;
            private String source_url;
            private int comment_count;
            private int style_type;
            private String page_token;
            private long create_time;
            private long publish_time;
            private List<?> tags;
            private List<ThumbnailsBean> thumbnails;

            public String getArticle_id() {
                return article_id;
            }

            public void setArticle_id(String article_id) {
                this.article_id = article_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getSource_name() {
                return source_name;
            }

            public void setSource_name(String source_name) {
                this.source_name = source_name;
            }

            public String getSource_url() {
                return source_url;
            }

            public void setSource_url(String source_url) {
                this.source_url = source_url;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getStyle_type() {
                return style_type;
            }

            public void setStyle_type(int style_type) {
                this.style_type = style_type;
            }

            public String getPage_token() {
                return page_token;
            }

            public void setPage_token(String page_token) {
                this.page_token = page_token;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public long getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(long publish_time) {
                this.publish_time = publish_time;
            }

            public List<?> getTags() {
                return tags;
            }

            public void setTags(List<?> tags) {
                this.tags = tags;
            }

            public List<ThumbnailsBean> getThumbnails() {
                return thumbnails;
            }

            public void setThumbnails(List<ThumbnailsBean> thumbnails) {
                this.thumbnails = thumbnails;
            }

            public static class ThumbnailsBean {
                /**
                 * url : http://image.uczzd.cn/15934407251257692872.jpg?id=0&from=export
                 * width : 720
                 * height : 720
                 * type : jpg
                 */

                private String url;
                private int width;
                private int height;
                private String type;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }
}
