package com.tongchengtianqi.zuitoutiao.entity;


import java.util.List;

public class VideoFragmentBean {

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
             * article_id : 4kg79mBMwRlD
             * title : 【萌娃】小宝宝遇上一窝小猫咪，爱心泛滥
             * url : /360/article/4kg79mBMwRlD?app_id=1&cid=2&c=null
             * source_name : 原来是这样
             * like_count : 1
             * unlike_count : 0
             * comment_count : 0
             * state : 0
             * style_type : 2
             * thumbnails : [{"url":"http://p6.qhimg.com/t01cd550e284ac2bbc7.jpg"}]
             * videos : [{"video_src":"http://video.mp.sj.360.cn/vod_zhushou/vod-shouzhu-bj/0469539a528e54c19ba4c49c2d1f5bd5.mp4","play_count":226,"duration":107}]
             * create_time : 1496995281
             * publish_time : 1496995281
             */

            private String article_id;
            private String title;
            private String url;
            private String source_name;
            private int like_count;
            private int unlike_count;
            private int comment_count;
            private int state;
            private int style_type;
            private int create_time;
            private int publish_time;
            private List<ThumbnailsBean> thumbnails;
            private List<VideosBean> videos;

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSource_name() {
                return source_name;
            }

            public void setSource_name(String source_name) {
                this.source_name = source_name;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public int getUnlike_count() {
                return unlike_count;
            }

            public void setUnlike_count(int unlike_count) {
                this.unlike_count = unlike_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getStyle_type() {
                return style_type;
            }

            public void setStyle_type(int style_type) {
                this.style_type = style_type;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(int publish_time) {
                this.publish_time = publish_time;
            }

            public List<ThumbnailsBean> getThumbnails() {
                return thumbnails;
            }

            public void setThumbnails(List<ThumbnailsBean> thumbnails) {
                this.thumbnails = thumbnails;
            }

            public List<VideosBean> getVideos() {
                return videos;
            }

            public void setVideos(List<VideosBean> videos) {
                this.videos = videos;
            }

            public static class ThumbnailsBean {
                /**
                 * url : http://p6.qhimg.com/t01cd550e284ac2bbc7.jpg
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class VideosBean {
                /**
                 * video_src : http://video.mp.sj.360.cn/vod_zhushou/vod-shouzhu-bj/0469539a528e54c19ba4c49c2d1f5bd5.mp4
                 * play_count : 226
                 * duration : 107
                 */

                private String video_src;
                private int play_count;
                private int duration;

                public String getVideo_src() {
                    return video_src;
                }

                public void setVideo_src(String video_src) {
                    this.video_src = video_src;
                }

                public int getPlay_count() {
                    return play_count;
                }

                public void setPlay_count(int play_count) {
                    this.play_count = play_count;
                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }
            }
        }
    }
}
