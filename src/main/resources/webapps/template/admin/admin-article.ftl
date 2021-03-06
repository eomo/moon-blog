<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 文章管理 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico"/>
    <#include "common/admin-css.ftl"/>
</head>
<body>
<div v-cloak id="app" class="wrapper">
    <#include "common/admin-header.ftl"/>
    <header class="admin-header">
        <div class="single-column-layout single-column-layout-admin u-clearfix">
            <div class="u-float-left">
                <h1 class="admin-header-name">文章管理</h1>
                <div class="admin-header-desc"><p>Web端的文本编辑器体验不是很好，建议在客户端写好后，直接复制过来</p></div>
            </div>
            <div class="u-float-right">
                <a class="btn btn-success" :href="jump('id_NuLlaRtIclE')" target="_blank">写文章</a>
            </div>
        </div>
    </header>
    <div class="single-column-layout single-column-layout-admin admin-container">
        <form>
            <b-row>
                <b-col cols="5">
                    <b-form-group horizontal
                                  :label-cols="2"
                                  label="创作年份"
                                  label-for="input">
                        <b-form-input type="text"
                                      placeholder="请输入年份进行查找：2018"
                                      v-model="condition.year">
                        </b-form-input>
                    </b-form-group>
                </b-col>
                <b-col cols="5">
                    <b-form-group horizontal
                                  :label-cols="2"
                                  label="分类："
                                  label-for="input">
                        <b-form-input type="text"
                                      placeholder="请输入文章分类进行查找"
                                      v-model="condition.categoryId">
                        </b-form-input>
                    </b-form-group>
                </b-col>
                <b-col cols="2">
                    <b-button @click="search" variant="primary">搜索</b-button>
                </b-col>
            </b-row>
        </form>
        <b-table striped Fixed :items="articles.list" :fields="fields" show-empty="true" empty-text="暂无数据">
            <template slot="title" slot-scope="row">
                <a :href="jumpPost(row.item.id)" target="_blank">{{row.item.title}}</a>
            </template>
            <template slot="status" slot-scope="row">
                {{row.item.status == 1 ? '已发布' : '草稿'}}
            </template>
            <template slot="action" slot-scope="row">
                <a :href="jump(row.item.id)" target="_blank">修改</a>
                <a @click="showConfirmModal(row.item.id)" style="color: #fa2037;cursor: pointer">删除</a>
                <a v-if="row.item.shows == 1" @click="hidenOrShow(row.item.id, 0)"
                   style="color: orangered;cursor: pointer">隐藏</a>
                <a v-if="row.item.shows == 0" @click="hidenOrShow(row.item.id, 1)"
                   style="color: forestgreen;cursor: pointer">显示</a>
            </template>
        </b-table>
        <b-pagination size="md" align="right" @input="step"
                      :total-rows="articles.total" v-model="articles.pager" :per-page="10">
        </b-pagination>
    </div>
    <b-modal ref="confirmModal" centered
             title="风险提示"
             header-bg-variant="danger"
             header-text-variant="light"
             ok-title="狠心删除"
             cancel-title="再想想"
             cancel-variant="success"
             ok-variant="danger"
             @ok="del"
             @cancel="close">
        <p>这是你的心血呀，确认要删除吗？</p>
    </b-modal>
</div>
<#include "common/admin-js.ftl"/>
<script>
    var getArticleList = function (vm) {
        var condition = vm.condition || {};
        condition.pager = vm.articles ? vm.articles.pager : 1;
        condition.size = vm.articles ? vm.articles.size : 15;
        HttpUtils.post('/v1/article/back/page', condition).done(function (data) {
            vm.articles = data;
        });
    };

    var deleteArticle = function (vm, id) {
        HttpUtils.delete('/v1/article/' + id, null).done(function (data) {
            UiTools.alert('文章已删除!', 'success');
            getArticleList(vm);
        });
    };

    var hidenOrShow = function (vm, id, flag) {
        HttpUtils.post('/v1/article/show/' + id + '/' + flag, null).done(function (data) {
            UiTools.alert('操作成功!', 'success');
            getArticleList(vm);
        });
    }

    var app = new Vue({
        el: '#app',
        data: {
            condition: {},
            fields: [
                {key: 'title', label: '文章标题', thStyle: {width: '40%'}},
                {key: 'categoryName', label: '分类目录', thStyle: {width: '10%'}},
                {key: 'topicName', label: '专题', thStyle: {width: '10%'}},
                {key: 'status', label: '类别', thStyle: {width: '10%'}},
                {key: 'updatedTime', label: '最后修改日期', thStyle: {width: '15%'}},
                {key: 'action', label: '操作', thStyle: {width: '15%'}}
            ],
            article: {},
            articles: {}
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/article/back/page', {pager: 1, size: 15}).done(function (data) {
                    self.articles = data;
                });
            });
        },
        methods: {
            showConfirmModal: function (id) {
                this.article = {id: id};
                this.$refs.confirmModal.show();
            },
            del: function () {
                deleteArticle(this, this.article.id);
            },
            close: function () {
                this.$refs.comfirmModal.hide();
            },
            search: function () {
                this.articles.pager = 1;
                getArticleList(this);
            },
            jump: function (id) {
                return '/admin/write/article/' + id + '?token=' + localStorage.getItem('x-api-token');
            },
            jumpPost: function (id) {
                return '/post/' + id;
            },
            step: function () {
                getArticleList(this);
            },
            hidenOrShow: function (id, flag) {
                hidenOrShow(this, id, flag);
            }
        }
    });
</script>
</body>
</html>