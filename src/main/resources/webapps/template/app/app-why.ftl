<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>十万个为什么 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/vendor.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/category.css" type="text/css" media="screen">
</head>

<body>
<#include "common/app-header.ftl"/>
<header class="category-header">
    <div class="single-column-layout single-column-layout-wide u-clearfix">
        <div class="category-header-logo">
            <a href="/travel">
                <img class="category-header-image"
                     src="https://static.hicsc.com/image/tc/why-7.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim"></a>
        </div>
        <div class="u-float-left">
            <h1 class="category-header-name-2">十万个为什么</h1>
            <div class="category-header-desc"><p>世界那么大，我想去看看</p></div>
        </div>
    </div>
</header>
<div v-cloak id="whyEL" class="single-column-layout single-column-layout-wide category-wrapper">
    <article v-for="item in articles" class="block-list" style="margin-right: 20%;">
        <!-- 图片规格：140x120 -->
        <a class="block-image" v-bind:aria-label="item.title"
           v-bind:href="'/post/' + item.id"
           v-bind:style="'background-image: url(' + item.image + ');'"></a>
        <div class="block-content">
            <h2 class="block-title">
                <a v-bind:href="'/post/' + item.id" v-bind:aria-label="item.title"
                   v-bind:title="item.title">{{item.title}}</a>
            </h2>
            <div class="block-snippet">{{item.summary}}</div>
            <div class="block-post-meta">
                {{item.viewCount}} 次浏览
                <span class="mid-dot-divider"></span>
                <time itemprop="datePublished" v-bind:datetime="item.publishTime">
                    发布于 {{item.publishTimeDesc}}
                </time>
            </div>
        </div>
    </article>
    <div v-if="showloadMore" class="block-more" @click="loadArticleList">
        <button id="show-more" aria-label="加载更多" title="加载更多" class="loading-button" data-paged="2" data-total="36">
            加载更多
        </button>
    </div>
</div>
</body>
<script>
    var vm = new Vue({
        el: '#whyEL',
        data: {
            showloadMore: false,
            pager: 1,
            articles: []
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/article/badge/page', {pager: self.pager, badge:'WHY'}).done(function (data) {
                    self.showloadMore = data.pages > data.pager;
                    self.articles = data.list;
                    self.articleCount = data.total;
                });
            });
        },
        methods: {
            travelList: function () {
                HttpUtils.post('/v1/article/badge/page', {pager: this.pager + 1, badge:'WHY'}).done(function (data) {
                    vm.showloadMore = data.pages > data.pager;
                    vm.articles = vm.articles.concat(data.list);
                });
            }
        }
    });
</script>
</html>