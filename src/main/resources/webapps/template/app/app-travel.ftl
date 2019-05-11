<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>旅行者 - 世界那么大，我想去走走 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/category.css" type="text/css" media="screen">
    <#include "common/app-baidu.ftl"/>
</head>

<body>
<#include "common/app-header.ftl"/>
<header class="category-header">
    <div class="single-column-layout single-column-layout-wide u-clearfix">
        <div class="category-header-logo">
            <a href="/travel">
                <img class="category-header-image"
                     src="//static.hicsc.com/image/tc/travel.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim"></a>
        </div>
        <div class="u-float-left">
            <h1 class="category-header-name-2">足迹</h1>
            <div class="category-header-desc"><p>世界那么大，我想去走走</p></div>
        </div>
    </div>
</header>
<div id="travelEL"  class="single-column-layout single-column-layout-wide travel-wrapper">
    <div class="block-list" style="margin-right: 20%;">
        <div v-for="item in articles" class="travel-article-item" itemscope="itemscope" itemtype="http://schema.org/Article">
            <div class="travel-article-title">
                <h2 itemprop="headline">
                    <a target="_blank" class="js-trackedPostLink"
                       v-bind:data-id="item.id"
                       v-bind:href="'/post/' + item.id"
                       v-bind:title="item.title">
                        {{item.title}}
                    </a>
                </h2>
            </div>
            <a class="travel-article-image js-trackedPostLink" target="_blank"
               v-bind:data-id="item.id"
               v-bind:title="item.title"
               v-bind:href="'/post/' + item.id"
               v-bind:style="'background-image: url(' + item.image + ');'"
               v-bind:aria-label="item.title">
            </a>
        </div>
    </div>

    <div v-if="showloadMore" class="block-more" @click="travelList">
        <button id="show-more" aria-label="加载更多" title="加载更多" class="loading-button" data-paged="2" data-total="36">
            加载更多
        </button>
    </div>
</div>
</body>
<script>
    var vm = new Vue({
        el: '#travelEL',
        data: {
            showloadMore: false,
            pager: 1,
            articles: []
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/article/badge/page', {pager: self.pager, badge:'TRAVEL'}).done(function (data) {
                    self.showloadMore = data.pages > data.pager;
                    self.articles = data.list;
                    self.articleCount = data.total;
                });
            });
        },
        methods: {
            travelList: function () {
                HttpUtils.post('/v1/article/badge/page', {pager: this.pager + 1, badge:'TRAVEL'}).done(function (data) {
                    vm.showloadMore = data.pages > data.pager;
                    vm.articles = vm.articles.concat(data.list);
                });
            }
        }
    });
</script>
</html>