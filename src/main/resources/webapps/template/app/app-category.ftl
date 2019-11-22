<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>记录生活，记录美好 - 不爱写代码的程序员，应该是一个好作家 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/vendor.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/category.css" type="text/css" media="screen">
    <#include "common/app-baidu.ftl"/>
</head>
<body>
<#include "common/app-header.ftl"/>
<div v-cloak id="articleEL">
    <header class="category-header">
        <div class="single-column-layout single-column-layout-wide u-clearfix">
            <div class="category-header-logo">
                <img class="category-header-image" src="${category.image}${category.format1}">
            </div>
            <div class="u-float-left">
                <h1 class="category-header-name">${category.name}</h1>
                <div class="category-header-desc"><p>${category.desc}</p></div>
                <div class="category-header-desc">
                    <span>文章 {{articleCount}}</span>
                    <span class="mid-dot-divider"></span>
                    <span>阅读 ${category.viewCount}</span>
                    <span class="mid-dot-divider"></span>
                    <span>评论 ${category.commentCount}</span>
                </div>
            </div>
        </div>
    </header>
    <div class="single-column-layout single-column-layout-wide category-wrapper">
        <article v-for="item in articles" class="block-list" style="margin-right: 20%;">
            <!-- 图片规格：140x120 -->
            <a class="block-image" v-bind:aria-label="item.title"
               v-bind:href="'/post/' + item.id"
               v-bind:style="'background-image: url(' + item.image + '/main2);'"></a>
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
                HttpUtils.post('/v1/article/font/page', {pager: self.pager, categoryId:'${category.id}'}).done(function (data) {
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
                    categoryId:'${category.id}'}).done(function (data) {
                    vm.showloadMore = data.pages > data.pager;
                    vm.articles = vm.articles.concat(data.list);
                });
            }
        }
    });
</script>
</html>