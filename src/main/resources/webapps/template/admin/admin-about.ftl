<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 关于我 - HICSC</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "common/admin-css.ftl"/>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="/webapps/asserts/editor.css" media="screen">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
</head>
<body>
<#include "common/admin-header.ftl"/>
<div class="single-column-layout single-column-layout-admin admin-container">
    <div v-cloak id="app" class="wrapper">
        <div class="publish-btn">
            <b-button @click="publish" variant="success u-margin-left10">保 存</b-button>
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
        </form>
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
            enabled: false,
            unique_id: "editor",
        },
        placeholder: '在此编辑你的文章'
    });

    var getArticle = function (vm) {
        HttpUtils.get("/v1/article/badge/ABOUTME", null).done(function (data) {
            if (!!data) {
                vm.article = data;
                if (!!data.id) {
                    mde.value(data.content);
                }
            }
        });
    }

    var checkArticle = function (vm) {
        if (!vm.article.title) {
            UiTools.alert('请输入文章标题!', 'error');
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
            article: {}
        },
        created: function () {
            getArticle(this);
        },
        methods: {
            publish: function () {
                if (checkArticle(this)) {
                    this.article.content = mde.value();
                    this.article.badge = 'ABOUTME';
                    this.article.summary = '';
                    this.article.image = '';
                    HttpUtils.post('/v1/article/badge', this.article).done(function (data) {
                        UiTools.alert('文章发布成功！', 'success');
                    });
                }
            }
        }
    });
</script>
</body>
</html>
