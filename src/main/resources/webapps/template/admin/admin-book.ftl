<ftl lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 图书管理 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <#include "common/admin-css.ftl"/>
</head>
<body>
<div v-cloak id="app" class="wrapper">
    <#include "common/admin-header.ftl"/>
    <header class="admin-header">
        <div class="single-column-layout single-column-layout-admin u-clearfix">
            <div class="u-float-left">
                <h1 class="admin-header-name">图书管理</h1>
                <div class="admin-header-desc"><p>添加书籍时只需要填写图书对应的豆瓣编号，服务端将自动从豆瓣获取书籍详细信息，请填写正确的豆瓣编号</p></div>
            </div>
            <div class="u-float-right">
                <b-button @click="showModal({})" variant="success">添加图书</b-button>
            </div>
        </div>
    </header>
    <div class="single-column-layout single-column-layout-admin admin-container">
        <form @submit.stop.prevent="handleSubmit">
        <b-row>
            <b-col cols="5">
                <b-form-group horizontal
                              :label-cols="2"
                              label="名称"
                              label-for="input">
                    <b-form-input type="text"
                                  placeholder="请输入图书名称进行查找"
                                  v-model="condition.title">
                    </b-form-input>
                </b-form-group>
            </b-col>
            <b-col cols="5">
                <b-form-group horizontal
                              :label-cols="2"
                              label="作者："
                              label-for="input">
                    <b-form-input type="text"
                                  placeholder="请输入图书作者进行查找"
                                  v-model="condition.author">
                    </b-form-input>
                </b-form-group>
            </b-col>
            <b-col cols="2">
                <b-button @click="search" variant="primary">搜索</b-button>
            </b-col>
        </b-row>
        </form>
        <b-table striped Fixed :items="books.list" :fields="fields" show-empty="true" empty-text="暂无数据">
            <template slot="title" slot-scope="row">
                <a :href="row.item.doubanUrl" target="_blank">
                    {{row.item.title}}
                </a>
            </template>
            <template slot="action" slot-scope="row">
                <b-button variant="link btn-sm" @click="showModal(row.item)">修改</b-button>
                <b-button variant="link btn-sm" @click="showConfirmModal(row.item.id)">删除</b-button>
            </template>
        </b-table>
        <b-pagination size="md" align="right"
                      :total-rows="books.total" v-model="books.pager" per-page="15">
        </b-pagination>
    </div>
    <b-modal ref="confirmModal" centered
             title="风险提示"
             header-bg-variant="danger"
             header-text-variant="light"
             ok-title="确认"
             cancel-title="取消"
             cancel-variant="success"
             ok-variant="danger"
             @ok="del"
             @cancel="close">
        <p>删除此图书后，书籍页面将不再显示，确认删除吗？</p>
    </b-modal>
    <b-modal ref="bookModal"
             :title="title"
             size="lg"
             ok-title="确认"
             cancel-title="取消"
             @ok="upsert"
             @cancel="close">
        <form @submit.stop.prevent="handleSubmit">
            <b-container fluid>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="图书豆瓣ID："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入图书对应的豆瓣编号"
                                          :disabled="isUpdated"
                                          v-model="book.doubanId"
                                          :state="state.doubanId"
                                          @change="changeDoubanId"
                                          aria-describedby="doubanIdFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="doubanIdFeedback">
                                请输入图书对应的豆瓣编号
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="阅读年份："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入你阅读此书的年份"
                                          required="true"
                                          v-model="book.year"
                                          :state="state.year"
                                          @change="changeYear"
                                          aria-describedby="yearFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="yearFeedback">
                                请输入你阅读此书的年份
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="你的简评："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入你阅读此书后的简单评论，不宜太长，否则页面显示不好看"
                                          required="true"
                                          v-model="book.remark"
                                          :state="state.remark"
                                          @change="changeRemark"
                                          aria-describedby="remarkFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="remarkFeedback">
                                请输入你阅读此书后的简单感想，尽量简洁
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="京东购买连接："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入图书京东购买连接"
                                          v-model="book.jingdongUrl">
                            </b-form-input>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="多看购买链接："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入图书多看购买链接"
                                          v-model="book.duokanUrl">
                            </b-form-input>
                        </b-form-group>
                    </b-col>
                </b-row>
            </b-container>
        </form>
    </b-modal>
</div>
<#include "common/admin-js.ftl"/>
<script>
    var getBookList = function (vm) {
        var condition = vm.condition || {};
        condition.pager = vm.books ? vm.books.pager : 1;
        condition.size = vm.books ? vm.books.size : 15;
        HttpUtils.post('/v1/book/page', condition).done(function (data) {
            vm.books = data;
        });
    };

    var upsert = function (e, vm) {
        e.preventDefault();
        if (!vm.book.doubanId) {
            vm.state.doubanId = false;
            return;
        }
        if (!vm.book.year || vm.book.year.length != 4) {
            vm.state.year = false;
            return;
        }
        if (!vm.book.remark) {
            vm.state.remark = false;
            return;
        }
        if (!!vm.book.id) {
            HttpUtils.post('/v1/book', vm.book).done(function (data) {
                UiTools.alert('图书修改成功 !', 'success');
                getTopicList(vm);
                vm.book = {};
                vm.$refs.bookModal.hide();
            })
        } else {
            HttpUtils.put('/v1/book', vm.book).done(function (data) {
                UiTools.alert('新的图书已添加!', 'success');
                getBookList(vm);
                vm.book = {};
                vm.$refs.bookModal.hide();
            })
        }
    };

    var deleteBook = function (vm, id) {
        HttpUtils.delete('/v1/book/' + id, null).done(function (data) {
            UiTools.alert('图书已删除!', 'success');
            getBookList(vm);
        });
    };

    var app = new Vue({
        el: '#app',
        data: {
            title: null,
            isUpdated: false,
            condition: {},
            fields: [
                {key: 'title', label: '图书名称'},
                {key: 'author', label: '作者'},
                {key: 'tag', label: '标签'},
                {key: 'publisher', label: '出版社'},
                {key: 'pubdate', label: '出版日期',thStyle: {width:'10%'}},
                {key: 'action', label: '操作',thStyle: {width:'10%'}}
            ],
            books: {},
            book: {},
            state: {
                doubanId: null,
                year: null,
                remark: null
            }
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/book/page', {pager: 1, size: 15}).done(function (data) {
                    self.books = data;
                });
            });
        },
        methods: {
            showModal: function (item) {
                if (!!item.id) {
                    this.title = '修改图书';
                    this.isUpdated = true;
                    this.book = {
                        doubanId: item.doubanId,
                        year: item.year,
                        remark: item.remark,
                        jingdongUrl: item.jingdongUrl,
                        duokanUrl: item.duokanUrl
                    }
                } else {
                    this.isUpdated = false;
                    this.title = '添加图书';
                }
                this.$refs.bookModal.show();
            },
            showConfirmModal: function (id) {
                this.book = {id: id};
                this.$refs.confirmModal.show();
            },
            upsert: function (e) {
                upsert(e, this);
            },
            del: function () {
                deleteBook(this, this.book.id);
            },
            close: function () {
                this.book = {};
                this.$refs.bookModal.hide();
                this.$refs.comfirmModal.hide();
            },
            changeDoubanId: function () {
                if (!!this.book.doubanId) {
                    this.state.doubanId = true;
                }
            },
            changeYear: function () {
                if (!!this.book.year && this.book.year.length == 4) {
                    this.state.year = true;
                }
            },
            changeRemark: function () {
                if (!!this.book.remark) {
                    this.state.remark = true;
                }
            },
            search: function () {
                this.books.pager = 1;
                getBookList(this);
            }
        }
    });
</script>
</body>
</html>