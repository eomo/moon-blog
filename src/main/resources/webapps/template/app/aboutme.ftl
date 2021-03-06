<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${article.title} - HICSC</title>
    <meta name="description" content="${article.title} - 作者: chen.chuan,首发于HICSC.COM">
    <link href="/webapps/asserts/header.css" rel="stylesheet" />
    <link href="/webapps/asserts/article.css" rel="stylesheet" />
    <link href="https://resources.hicsc.com/ajax/libs/hicsc/comment.css" rel="stylesheet" />
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <link href="https://resources.hicsc.com/ajax/libs/emojione/2.2.7/emojione.min.css" rel="stylesheet">
    <link href="https://resources.hicsc.com/ajax/libs/emojionearea/emojionearea.min.css" rel="stylesheet">
    <#include "common/app-baidu.ftl"/>
</head>
<body>
<header class="article-header" style="padding: 20px 0">
    <div class="article-header__content">
        <a href="https://www.hicsc.com"><img src="/webapps/asserts/image/logo.png" width="64" alt="HICSC"></a>
    </div>
</header>
<div class="moon-article-layout" style="margin: 0 auto">
    <div class="block-group u-padding-top50">
        <header class="entry-header">
            <h2 class="entry-title" itemprop="headline">${article.title}</h2>
        </header>
        <input type="hidden" id="article_id">
        <article id="moon-article">${article.content}</article>
    </div>
</div>
<div class="single-column-layout single-column-layout-wide">
    <div id="commentEL" v-cloak style="width: 780px;margin: auto;">
        <meta v-bind:content="'UserComments:'+commentCount" itemprop="interactionCount">
        <h3 class="responses-title csc">Comments</h3>
        <ol id='comment-list' class="commentlist">
            <li v-for="item in comments" class="comment" itemtype="http://schema.org/Comment" v-bind:data-id="item.id"
                itemscope="" itemprop="comment">
                <div v-bind:id="'comment-' + item.id" class="comment-block">
                    <div class="comment-info u-clearfix">
                        <div class="comment-avatar">
                            <img height="40" width="40" class="avatar"
                                 v-bind:src="'https://cdn.v2ex.com/gravatar/' + item.emailHash + '?s=40&amp;d=mm&amp;r=x'">
                        </div>
                        <div class="comment-meta">
                            <div class="comment-author" itemprop="author">{{item.author}}
                                <span class="comment-reply-link u-cursorPointer"
                                      v-on:click="moveResponseForm(item.id,item.articleId,item.id)">REPLY</span>
                            </div>
                            <div class="comment-time" itemprop="datePublished" datetime="{{item.createdTime}}">
                                发布于{{item.createdTimeDesc}}
                            </div>
                        </div>
                    </div>
                    <div class="comment-content" itemprop="description">
                        <p v-html="item.content"></p>
                    </div>
                </div>
                <ol v-if="!!item.children && item.children.length > 0" class="children">
                    <li v-for="child in item.children" class="comment" v-bind:data-id="child.id">
                        <div v-bind:id="'comment-' + child.id" class="comment-block">
                            <div class="comment-info u-clearfix">
                                <div class="comment-avatar">
                                    <img height="40" width="40" class="avatar"
                                         v-bind:src="'https://cdn.v2ex.com/gravatar/' + child.emailHash + '?s=40&amp;d=mm&amp;r=x'">
                                </div>
                                <div class="comment-meta">
                                    <div class="comment-author" itemprop="author">{{child.author}}
                                        <span class="comment-reply-link u-cursorPointer"
                                              v-on:click="moveResponseForm(child.id,child.articleId,item.id)">REPLY</span>
                                    </div>
                                    <div class="comment-time" itemprop="datePublished"
                                         v-bind:datetime="child.createdTime">
                                        发布于{{child.createdTimeDesc}}
                                    </div>
                                </div>
                            </div>
                            <div class="comment-content" itemprop="description">
                                <p>
                                    <a v-bind:href="'#comment-' + child.parentId" rel="nofollow"
                                       class="u-color-red-purple"
                                       v-bind:data-id="child.parentId" class="purple atreply">@{{child.atAuthor}}</a>&nbsp;&nbsp;
                                    <p v-html="child.content"></p>
                                </p>
                            </div>
                        </div>
                    </li>
                </ol>
            </li>
        </ol>
        <div id="splitter"></div>
        <div id="respond" class="respond">
                <h3 id="reply-title" class="comments-title csc">Post a Message</h3>
                <div class="responsesForm">
                    <p class="comment-note">
                        人生在世，错别字在所难免，无需纠正。
                    </p>
                    <div class="info">
                        <p>
                            <label for="author">昵称</label>
                            <input id="author" type="text" class="inputGroup" aria-required="true" v-model="comment.author">
                        </p>
                        <p class="comment-form-input">
                            <label for="email">邮箱</label>
                            <input id="email" class="inputGroup" type="text" aria-required="true"
                                   v-model="comment.authorEmail">
                        </p>
                        <p class="comment-form-input">
                            <label for="url">网址</label>
                            <input id="url" class="inputGroup" type="text" v-model="comment.authorUrl">
                        </p>
                    </div>

                    <p class="comment-form-comment">
                        <label for="comment">评论</label>
                        <textarea id="comment" v-model="comment.content" class="inputGroup inputTextarea" rows="4"
                                  cols="45"></textarea>
                    </p>
                    <div class="submit" v-on:click="addComment">提交评论</div>
                    <input type="hidden" id="article_id" v-model="comment.articleId">
                    <input type="hidden" id="comment_parent" v-model="comment.parentId">
                    <input type="hidden" id="comment_root" v-model="comment.rootId">
                </div>
            </div>
    </div>
</div>
</body>
<script src="https://resources.hicsc.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://resources.hicsc.com/ajax/libs/vue/2.5.16/vue.min.js"></script>
<script src="https://resources.hicsc.com/ajax/libs/emojione/2.2.7/emojione.min.js"></script>
<script src="https://resources.hicsc.com/ajax/libs/emojionearea/emojionearea.min.js"></script>
<script src="/webapps/asserts/js/notify.js"></script>
<script src="/webapps/asserts/js/utils.js"></script>
<script type="text/javascript">
    var vm = new Vue({
        el: '#commentEL',
        data: {
            comment: {
                author: CookieUtils.get('comment_author'),
                authorEmail: CookieUtils.get('comment_author_email'),
                authorUrl: CookieUtils.get('comment_author_url')
            },
            commentCount: 0,
            comments: []
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.get('/v1/comment/${article.id}', null).done(function (data) {
                    data.forEach(function (item) {
                        item.content = emojione.unicodeToImage(emojione.shortnameToUnicode(item.content));
                        self.commentCount = self.commentCount + 1 + (!!item.children ? item.children.length : 0);
                    });
                    self.comments = data;
                });
            });
        },
        mounted: function () {
            this.$nextTick(function () {
                $("#comment").emojioneArea({
                    search: false,
                    useInternalCDN: true,
                    tonesStyle: "bullet"
                });
            })
        },
        methods: {
            addComment: function () {
                if (!this.comment.author) {
                    UiTools.alert('请填写昵称!', 'error');
                    return;
                }
                if (!this.comment.authorEmail) {
                    UiTools.alert('请填写邮件地址!', 'error');
                    return;
                }
                this.comment.content = emojione.toShort($('#comment').val());
                if (!this.comment.content) {
                    UiTools.alert('你还没有写评论呢!', 'error');
                    return;
                }
                CookieUtils.set('comment_author', this.comment.author);
                CookieUtils.set('comment_author_email', this.comment.authorEmail);
                CookieUtils.set('comment_author_url', this.comment.authorUrl);
                this.comment.articleId = '${article.id}';
                HttpUtils.post('/v1/comment', this.comment).done(function (data) {
                    HttpUtils.get('/v1/comment/${article.id}', null).done(function (data) {
                        data.forEach(function (item) {
                            item.content = emojione.unicodeToImage(emojione.shortnameToUnicode(item.content));
                            vm.commentCount = vm.commentCount + 1 + (!!item.children ? item.children.length : 0);
                        });
                        vm.comments = data;
                        vm.comment.content = null;
                        $('.emojionearea-editor').empty();
                        $("div[id='splitter']").append($('#respond'));
                    });
                });
            },
            moveResponseForm: function (commentId, articleId, rootId) {
                $("div[id='comment-" + commentId + "']").append($('#respond'));
                this.comment.articleId = articleId;
                this.comment.parentId = commentId;
                this.comment.rootId = rootId;
            }
        }
    });
</script>
</html>
