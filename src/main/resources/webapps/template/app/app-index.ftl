<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>首页</title>
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/vendor.css" type="text/css" media="screen">
</head>

<#include "common/app-header.ftl"/>
<div class="multi-column-layout-container">
    <div v-cloak id="articleEL" class="multi-column-layout multi-column-layout-primary">
        <div class="block-group u-padding-top50">
            <div class="heading-title">最新文章</div>
            <article v-for="item in articles" class="block-list">
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
                        <a v-bind:href="'/category/' + item.categoryId" rel="category tag">{{item.categoryName}}</a>
                        <span class="mid-dot-divider"></span>
                        <time itemprop="datePublished" v-bind:datetime="item.publishTime">
                            发布于 {{item.publishTimeDesc}}
                        </time>
                    </div>
                </div>
            </article>
        </div>
        <div v-if="showloadMore" class="block-more" @click="loadArticleList">
            <button id="show-more" aria-label="加载更多" title="加载更多" class="loading-button" data-paged="2" data-total="36">
                加载更多
            </button>
        </div>
    </div>
    <div class="multi-column-layout multi-column-layout-secondary">
        <div class="u-padding-top50">
            <div class="widget">
                <div class="heading-title">关于博主</div>
                <div class="widget-card">
                    <div class="widget-card-imageWrapper">
                        <img src="http://pic.moondev.cn//image/personal/avastar.jpeg" class="avatar" width="32"
                             height="32">
                    </div>
                    <div class="widget-card-content">CHEN川</div>
                    <div class="widget-card-description">
                        <p>混迹于非知名科技公司的非知名文艺青年</p>
                        <p class="more">
                            <a href="/about" title="更多关于博主的信息" aria-label="更多关于博主的信息">了解更多</a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="widget">
                <div class="heading-title">专题</div>
                <div class="widget-topic-list">
                    <#list topics as topic>
                        <a href="/topic/${topic.code!}" class="widget-topic-item widthImage"
                           title="和${topic.name!}有关的文章">
                            <img src="${topic.image!}" alt="${topic.name!}" aria-label="${topic.name!}"
                                 class="widget-topic-image">${topic.name!}
                        </a>
                    </#list>
                </div>
            </div>
            <div class="widget">
                <div class="heading-title">发现好文章</div>
                <ul class="widget-article-list widget-article-list-withIcon">
                    <#list hots as item>
                        <li class="widget-article-item">
                            <div class="widget-article-item-image">
                                <img aria-label="${item.title}" alt="${item.title}" class="image--outlined"
                                     src="${item.image}">
                            </div>
                            <div class="widget-article-item-info">
                                <h4 class="widget-article-item-title">
                                    <a aria-label="${item.title}"
                                       title="${item.title}"
                                       href="/post/${item.id}">${item.title}</a>
                                </h4>
                                <p class="widget-article-item-description JiEun">CHEN.CHUAN
                                    <span class="mid-dot-divider"></span>${item.commentCount} replies
                                </p>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
            <div class="widget">
                <div class="heading-title">留给朋友</div>
                <div class="widget-list">
                    <a href="/archive" class="widget-list-item">所有文章</a>
                    <span class="mid-dot-divider"></span>
                    <a href="/categories" class="widget-list-item">所有分类</a>
                    <span class="mid-dot-divider"></span>
                    <a href="/movie" class="widget-list-item">观影记录</a>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "common/app-footer.ftl"/>
</body>
<script>
    var vm = new Vue({
        el: '#articleEL',
        data: {
            showloadMore: false,
            pager: 1,
            articles: []
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/article/page', {pager: self.pager}).done(function (data) {
                    self.showloadMore = data.pages > data.pager;
                    self.articles = data.list;
                });
            });
        },
        methods: {
            loadArticleList: function () {
                HttpUtils.post('/v1/article/page', {pager: this.pager + 1}).done(function (data) {
                    vm.showloadMore = data.pages > data.pager;
                    vm.articles = vm.articles.concat(data.list);
                });
            }
        }
    });
</script>
</html>