<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>看过的书</title>
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/book.css" type="text/css" media="screen">
</head>

<body>
<#include "common/app-header.ftl"/>
<header class="category-header">
    <div class="single-column-layout single-column-layout-wide u-clearfix">
        <div class="category-header-logo">
            <a href="https://fatesinger.com/zine">
                <img class="category-header-image"
                     src="http://apps.moondev.cn/image/book3.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim">
            </a>
        </div>
        <div class="u-float-left">
            <h1 class="category-header-name">看过的书</h1>
            <div class="category-header-desc">
                <p>如果你愿意购买或者与我交换图书，请与我联系</p>
            </div>
        </div>
    </div>
</header>
<div id="bookEL" class="single-column-layout single-column-layout-wide book-wrapper">
    <div class="block-list">
        <div v-for="item in books" class="book-item">
            <div class="book-image block-image"
                 v-bind:style="'background-image:url(' + item.mediumImage + ')'">
                <span class="rating">{{item.rating}}</span>
            </div>
            <div class="book-info">
                <p class="title">{{item.title}}</p>
                <p class="author">作者：{{item.author}}</p>
                <p class="fenlei">{{item.tag}}</p>
                <p class="extra-link">
                    <a v-bind:href="item.doubanUrl" class="badge douban" target="_blank"><span class="link-title">豆瓣</span></a>
                    <a v-if="!!item.jingdongUrl" v-bind:href="item.jingdongUrl" class="badge jingdong" target="_blank"><span
                            class="link-title">京东</span></a>
                    <a v-if="!!item.duokanUrl" v-bind:href="item.duokanUrl" class="badge duokan" target="_blank"><span
                            class="link-title">多看电子书</span></a>
                </p>
                <p class="reviews">
                    {{item.remark}}
                </p>
            </div>
        </div>
    </div>
    <div v-if="showloadMore" class="block-more" @click="bookList">
        <button id="show-more" aria-label="加载更多" title="加载更多" class="loading-button" data-paged="2" data-total="36">
            加载更多
        </button>
    </div>

</body>
<script>
    var vm = new Vue({
        el: '#bookEL',
        data: {
            showloadMore: false,
            pager: 1,
            books: []
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/book/page', {
                    pager: self.pager
                }).done(function (data) {
                    self.showloadMore = data.pages > data.pager;
                    self.books = data.list;
                });
            });
        },
        methods: {
            bookList: function () {
                HttpUtils.post('/v1/book/page', {
                    pager: this.pager + 1
                }).done(function (data) {
                    vm.showloadMore = data.pages > data.pager;
                    vm.books = vm.books.concat(data.list);
                });
            }
        }
    });
</script>
</html>