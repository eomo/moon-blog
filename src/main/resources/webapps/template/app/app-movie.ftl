<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>看过的电影</title>
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/movie.css" type="text/css" media="screen">
</head>
<body>

<#include "common/app-header.ftl"/>
<header class="category-header">
    <div class="single-column-layout single-column-layout-wide u-clearfix">
        <div class="category-header-logo">
            <a href="https://fatesinger.com/zine">
                <img class="category-header-image"
                     src="http://apps.moondev.cn/image/movie.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim"></a>
        </div>
        <div class="u-float-left">
            <h1 class="category-header-name">看过的电影</h1>
            <div class="category-header-desc"><p>人生不止眼前，还有远方</p></div>
        </div>
    </div>
</header>
<div id="movieEL" class="single-column-layout single-column-layout-wide movie-wrapper">
    <div class="block-list">
        <div v-for="item in movies" class="movie-item">
            <a v-bind:href="item.doubanUrl" v-bind:title="item.title" target="_blank">
                <div class="movie-image block-image"
                     v-bind:style="'background-image:url(' + item.doubanImage3 + ')'">
                    <span class="rating">{{item.rating}}</span></div>
                <span class="movie-title">{{item.title}}</span>
            </a>
        </div>
    </div>
    <div v-if="showloadMore" class="block-more" @click="movieList">
        <button id="show-more" aria-label="加载更多" title="加载更多" class="loading-button" data-paged="2" data-total="36">
            加载更多
        </button>
    </div>
</body>
<script>
    var vm = new Vue({
        el: '#movieEL',
        data: {
            showloadMore: false,
            pager: 1,
            movies: []
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/movie/page', {
                    pager: self.pager
                }).done(function (data) {
                    self.showloadMore = data.pages > data.pager;
                    self.movies = data.list;
                });
            });
        },
        methods: {
            movieList: function () {
                HttpUtils.post('/v1/movie/page', {
                    pager: this.pager + 1
                }).done(function (data) {
                    vm.showloadMore = data.pages > data.pager;
                    vm.movies = vm.movies.concat(data.list);
                });
            }
        }
    });
</script>
</html>