<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 编辑文章</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "common/admin-css.ftl"/>
    <link rel="stylesheet" type="text/css" href="/webapps/asserts/editor.css" media="screen">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
</head>
<body>
<#include "common/admin-header.ftl"/>
<div class="single-column-layout single-column-layout-admin admin-container">
    <div id="app" class="wrapper">
        <div class="publish-btn">
            <b-button @click="saveDraft" variant="primary">保存草稿</b-button>
            <b-button @click="publish" variant="success u-margin-left10">发布文章</b-button>
        </div>
        <form>
            <b-row>
                <b-col>
                    <b-form-group horizontal
                                  :label-cols="1"
                                  label="文章标题："
                                  label-for="input">
                        <b-form-input type="text"
                                      placeholder="请输入文章标题"
                                      v-model="article.title">
                        </b-form-input>
                    </b-form-group>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-group horizontal
                                  :label-cols="2"
                                  label="所属分类："
                                  label-for="input">
                        <b-form-select v-model="article.categoryId" :options="categories"/>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group horizontal
                                  :label-cols="2"
                                  label="所属专题："
                                  label-for="input">
                        <b-form-select v-model="article.topicId" :options="topics"/>
                    </b-form-group>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-group horizontal
                                  :label-cols="1"
                                  label="图片描述："
                                  label-for="input">
                        <b-form-input type="text"
                                      placeholder="请输入描述图片地址，图片用于首页或者其它搜索页面的展示，图片不宜过大"
                                      v-model="article.image">
                        </b-form-input>
                    </b-form-group>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-group horizontal
                                  :label-cols="1"
                                  label="文章摘要："
                                  label-for="input">
                        <b-form-textarea v-model="article.summary"
                                         placeholder="请输入文章摘要，若不输入，则将自动截取正文内容，其有可能包含各种标签"
                                         :rows="3"
                                         :max-rows="6">
                        </b-form-textarea>
                    </b-form-group>
                </b-col>
            </b-row>
        </form>
        <b-modal ref="confirmModal" centered
                 title="风险提示"
                 ok-title="跳转到管理页面"
                 cancel-title="继续编辑"
                 cancel-variant="primary"
                 ok-variant="success"
                 @ok="jump"
                 @cancel="close">
            <p>操作成功，你可以继续留在此页面编辑文章或跳转到管理页面进行调整</p>
        </b-modal>
    </div>
    <section class="main-content">
        <textarea id="editor"></textarea>
    </section>
</div>

<#include "common/admin-js.ftl"/>
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
<script>
    var mde = new SimpleMDE({
        element: document.getElementById("editor"),
        autoDownloadFontAwesome: false,
        spellChecker: false,
        autosave: {
            enabled: true,
            unique_id: "editor",
        },
        placeholder: '在此编辑你的文章'
    });

    var getCategoryList = function (vm) {
        HttpUtils.get("/v1/category", null).done(function (data) {
            vm.categories = [];
            data.forEach(function (item) {
                vm.categories.push({value: item.id, text: item.name});
            })
        });
    };

    var getTopicList = function (vm) {
        HttpUtils.get("/v1/topic", null).done(function (data) {
            vm.topics = [];
            data.forEach(function (item) {
                vm.topics.push({value: item.id, text: item.name});
            })
        });
    };

    var getArticle = function (vm) {
        HttpUtils.get("/v1/article/${article_id}",null).done(function (data) {
            vm.article = data;
            if (!!data.id) {
                mde.value(data.content);
            }
        });
    }

    var checkArticle = function (vm) {
        if (!vm.article.title) {
            UiTools.alert('请输入文章标题!', 'error');
            return false;
        }
        if (!vm.article.image) {
            UiTools.alert('请输入描述图片地址!', 'error');
            return false;
        }
        if (!mde.value() || mde.value().length == 0) {
            UiTools.alert('请输入文章内容!', 'error');
            return false;
        }
        return true;
    }

    var app = new Vue({
        el: '#app',
        data: {
            article: {},
            categories: [],
            topics: []
        },
        created: function () {
            getArticle(this);
            getCategoryList(this);
            getTopicList(this);
        },
        methods: {
            saveDraft: function () {
                if (checkArticle(this)) {
                    this.article.content = mde.value();
                    HttpUtils.post('/v1/article/draft', this.article).done(function (data) {
                        UiTools.alert('保存草稿成功！', 'success');
                        app.article.id = data;
                        app.$refs.confirmModal.show();
                    });
                }
            },
            publish: function () {
                if (checkArticle(this)) {
                    this.article.content = mde.value();
                    HttpUtils.post('/v1/article/publish', this.article).done(function (data) {
                        UiTools.alert('文章发布成功！', 'success');
                        app.article.id = data;
                        app.$refs.confirmModal.show();
                    });
                }
            },
            close: function () {
                this.$refs.confirmModal.hide();
            },
            jump: function () {
                window.location.href = '/admin/article';
            },
        }
    });
</script>
</body>
</html>