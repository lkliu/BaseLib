package com.liko.base.entity;

import java.util.List;

/**
*
* @author Liko
* @Date 17/10/28 上午11:16
*
* @Description
*/

public class FristBean {


    /**
     * error : false
     * results : [{"_id":"59eedfc2421aa90fe2f02be9","createdAt":"2017-10-24T14:37:54.794Z","desc":"自定义View实现卷尺效果，博客实现原理分析+github开源","publishedAt":"2017-10-27T12:02:30.376Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/06e65ef3f3f1","used":true,"who":null},{"_id":"59eef58d421aa90fef2034c8","createdAt":"2017-10-24T16:10:53.831Z","desc":"Idea 插件，OK, Gradle! 用于快速搜索 maven 库引用","images":["http://img.gank.io/7fb55566-e8c5-4235-8147-df2d7b80b60f"],"publishedAt":"2017-10-27T12:02:30.376Z","source":"web","type":"Android","url":"https://github.com/scana/ok-gradle","used":true,"who":"drakeet"},{"_id":"59f055d8421aa90fe50c01a2","createdAt":"2017-10-25T17:14:00.412Z","desc":"Android 弹性动画的三种实现方式","publishedAt":"2017-10-27T12:02:30.376Z","source":"web","type":"Android","url":"http://blog.csdn.net/qq_34902522/article/details/77651799","used":true,"who":null},{"_id":"59f164c9421aa90fe50c01a7","createdAt":"2017-10-26T12:30:01.836Z","desc":"Android 开发面试 \u201c108\u201d 问","images":["http://img.gank.io/2614d7f0-ff4a-49e1-9b61-8908c372dfd5"],"publishedAt":"2017-10-27T12:02:30.376Z","source":"web","type":"Android","url":"https://www.diycode.cc/topics/993","used":true,"who":null},{"_id":"59f1b7b1421aa90fe2f02bfa","createdAt":"2017-10-26T18:23:45.430Z","desc":"使用 Vue 簡單打包 elevator.js 組件","images":["http://img.gank.io/9431dbbc-4976-41fb-8bd3-d06f85a5c376"],"publishedAt":"2017-10-27T12:02:30.376Z","source":"web","type":"Android","url":"https://git.io/vFJeQ","used":true,"who":"WesleyChang"},{"_id":"59f212a9421aa90fe72535c9","createdAt":"2017-10-27T00:51:53.772Z","desc":"今天早上，Google 发布了 AS 3.0，因为之前一直在看 kotlin的支持，特地翻了一下对 Java8 的支持方式，结果\u2026\u2026","images":["http://img.gank.io/a0cbee78-0495-4237-b30f-209d4b9a6111"],"publishedAt":"2017-10-27T12:02:30.376Z","source":"web","type":"Android","url":"https://kymjs.com/code/2017/10/26/01/","used":true,"who":"张涛"},{"_id":"59f29720421aa90fe2f02bfc","createdAt":"2017-10-27T10:17:04.728Z","desc":"LowPloy 风格的动画","images":["http://img.gank.io/6ba882f8-d473-413b-b93c-c094a4fa549b"],"publishedAt":"2017-10-27T12:02:30.376Z","source":"web","type":"Android","url":"https://github.com/nekocode/TriangulationDrawable#","used":true,"who":"nekocode"},{"_id":"59efe9fb421aa90fe72535c1","createdAt":"2017-10-25T09:33:47.784Z","desc":"堪比阿里插件的Android Studio插件集合(IDE通用)（上）","publishedAt":"2017-10-25T11:39:10.950Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247487640&idx=1&sn=3752d389f908a6116341a03e6f3c6730","used":true,"who":"陈宇明"},{"_id":"59eff4ad421aa90fef2034cb","createdAt":"2017-10-25T10:19:25.884Z","desc":"Android开源框架榜单\u2014\u2014百大框架排行榜","publishedAt":"2017-10-25T11:39:10.950Z","source":"web","type":"Android","url":"https://github.com/ShaunSheep/Android_100_TOP-Projects","used":true,"who":"Chaofun"},{"_id":"59f00673421aa90fe2f02bf5","createdAt":"2017-10-25T11:35:15.934Z","desc":"够长的 Shadow 效果。","publishedAt":"2017-10-25T11:39:10.950Z","source":"chrome","type":"Android","url":"https://github.com/florent37/LongShadow","used":true,"who":"代码家"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 59eedfc2421aa90fe2f02be9
         * createdAt : 2017-10-24T14:37:54.794Z
         * desc : 自定义View实现卷尺效果，博客实现原理分析+github开源
         * publishedAt : 2017-10-27T12:02:30.376Z
         * source : web
         * type : Android
         * url : http://www.jianshu.com/p/06e65ef3f3f1
         * used : true
         * who : null
         * images : ["http://img.gank.io/7fb55566-e8c5-4235-8147-df2d7b80b60f"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private Object who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public Object getWho() {
            return who;
        }

        public void setWho(Object who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
