<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>${topic.name} - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/topic.css" type="text/css" media="screen">
    <#include "common/app-baidu.ftl"/>
</head>

<body>
    <#include "common/app-header.ftl"/>
<div v-cloak id="articleEL">
    <header class="single-column-layout single-column-layout-wide topic-header">
        <h1 class="topic-header-title">${topic.name}</h1>
        <div class="entry-meta">
            <span>文章 {{articleCount}}</span>
            <span class="mid-dot-divider"></span>
            <span>阅读 ${topic.viewCount}</span>
            <span class="mid-dot-divider"></span>
            <span>评论 ${topic.commentCount}</span>
        </div>
    </header>
    <div class="single-column-layout single-column-layout-wide">
        <div class="topic-article-wrapper">
            <div v-for="item in articles" class="topic-article-item">
                <a class="topic-article-image js-trackedPostLink" target="_blank" v-bind:data-id="item.id"
                   v-bind:title="item.title"
                   v-bind:href="'/post/' + item.id"
                   v-bind:aria-label="item.title"
                   v-bind:style="'background-image: url(' + item.image + '/main);'"></a>
                <div class="topic-article-title">
                    <h2 itemprop="headline">
                        <a target="_blank" class="js-trackedPostLink" v-bind:data-id="item.id"
                           v-bind:href="'/post/' + item.id"
                           v-bind:title="item.title">
                            {{item.title.length > 14 ? item.title.substring(0,14) + '...' : item.title}}
                        </a>
                    </h2>
                </div>
            </div>
        </div>
        <div v-if="showloadMore" class="block-more" @click="loadArticleList">
            <button id="show-more" aria-label="加载更多" title="加载更多" class="loading-button" data-paged="2" data-total="36">
                加载更多
            </button>
        </div>
    </div>
</div>
</body>
<script>
    var vm = new Vue({
        el: '#articleEL',
        data: {
            showloadMore: false,
            articleCount: 0,
            pager: 1,
            articles: []
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/article/font/page', {pager: self.pager, topicId:${topic.id}}).done(function (data) {
                    self.showloadMore = data.pages > data.pager;
                    self.articles = data.list;
                    self.articleCount = data.total;
                });
            });
        },
        methods: {
            loadArticleList: function () {
                HttpUtils.post('/v1/article/font/page', {
                    pager: this.pager + 1,
                    topicId:${topic.id}}).done(function (data) {
                    vm.showloadMore = data.pages > data.pager;
                    vm.articles = vm.articles.concat(data.list);
                });
            }
        }
    });
</script>
</html>