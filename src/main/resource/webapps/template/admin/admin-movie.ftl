<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 图书管理</title>
    <#include "common/admin-css.ftl"/>
</head>
<body>
<div v-cloak id="app" class="wrapper">
    <#include "common/admin-header.ftl"/>
    <header class="admin-header">
        <div class="single-column-layout single-column-layout-admin u-clearfix">
            <div class="u-float-left">
                <h1 class="admin-header-name">电影管理</h1>
                <div class="admin-header-desc"><p>添加电影时只需要填写电影对应的豆瓣编号，服务端将自动从豆瓣获取电影详细信息，请填写正确的豆瓣编号</p></div>
            </div>
            <div class="u-float-right">
                <b-button @click="showModal({})" variant="success">添加影片</b-button>
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
                                      placeholder="请输入影片名称进行查找"
                                      v-model="condition.title">
                        </b-form-input>
                    </b-form-group>
                </b-col>
                <b-col cols="5">
                    <b-form-group horizontal
                                  :label-cols="2"
                                  label="人物："
                                  label-for="input">
                        <b-form-input type="text"
                                      placeholder="请输入导演、主演、编剧进行查找"
                                      v-model="condition.author">
                        </b-form-input>
                    </b-form-group>
                </b-col>
                <b-col cols="2">
                    <b-button @click="search" variant="primary">搜索</b-button>
                </b-col>
            </b-row>
        </form>
        <b-table striped Fixed :items="movies.list" :fields="fields" show-empty="true" empty-text="暂无数据">
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
        <b-pagination size="md" align="right" @input="step"
                      :total-rows="movies.total" v-model="movies.pager" per-page="15">
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
        <p>删除此电影后，观影记录页面将不再显示，确认删除吗？</p>
    </b-modal>
    <b-modal ref="movieModal"
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
                                      label="影片豆瓣ID："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入影片对应的豆瓣编号"
                                          :disabled="isUpdated"
                                          v-model="movie.doubanId"
                                          :state="state.doubanId"
                                          @change="changeDoubanId"
                                          aria-describedby="doubanIdFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="doubanIdFeedback">
                                请输入影片对应的豆瓣编号
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
                                          placeholder="请输入你观看影片后的简单评论，不宜太长，否则页面显示不好看"
                                          required="true"
                                          v-model="movie.remark"
                                          :state="state.remark"
                                          @change="changeRemark"
                                          aria-describedby="remarkFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="remarkFeedback">
                                请输入你观影后的简单感想，尽量简洁
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
            </b-container>
        </form>
    </b-modal>
</div>
<#include "common/admin-js.ftl"/>
<script>
    var getMovieList = function (vm) {
        var condition = vm.condition || {};
        condition.pager = vm.movies ? vm.movies.pager : 1;
        condition.size = vm.movies ? vm.movies.size : 15;
        HttpUtils.post('/v1/movie/page', condition).done(function (data) {
            vm.movies = data;
        });
    };

    var upsert = function (e, vm) {
        e.preventDefault();
        if (!vm.movie.doubanId) {
            vm.state.doubanId = false;
            return;
        }
        if (!vm.movie.remark) {
            vm.state.remark = false;
            return;
        }
        if (!!vm.movie.id) {
            HttpUtils.post('/v1/movie', vm.movie).done(function (data) {
                UiTools.alert('影片修改成功 !', 'success');
                getMovieList(vm);
                vm.movie = {};
                vm.$refs.movieModal.hide();
            })
        } else {
            HttpUtils.put('/v1/movie', vm.movie).done(function (data) {
                UiTools.alert('新的影片已添加!', 'success');
                getMovieList(vm);
                vm.movie = {};
                vm.$refs.movieModal.hide();
            })
        }
    };

    var deleteMovie = function (vm, id) {
        HttpUtils.delete('/v1/movie/' + id, null).done(function (data) {
            UiTools.alert('影片已删除!', 'success');
            getMovieList(vm);
        });
    };

    var app = new Vue({
        el: '#app',
        data: {
            title: null,
            condition: {},
            fields: [
                {key: 'title', label: '影片名称'},
                {key: 'genres', label: '类型'},
                {key: 'countries', label: '地区'},
                {key: 'directors', label: '导演'},
                {key: 'casts', label: '主演'},
                {key: 'year', label: '年份'},
                {key: 'action', label: '操作'}
            ],
            isUpdated: false,
            movies: null,
            movie: {},
            state: {
                doubanId: null,
                remark: null
            }
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.post('/v1/movie/page', {pager: 1, size: 15}).done(function (data) {
                    self.movies = data;
                });
            });
        },
        methods: {
            showModal: function (item) {
                if (!!item.id) {
                    this.title = '修改影片';
                    this.isUpdated = true;
                    this.movie = {
                        doubanId: item.doubanId,
                        remark: item.remark
                    }
                } else {
                    this.isUpdated = false;
                    this.title = '添加影片';
                }
                this.$refs.movieModal.show();
            },
            showConfirmModal: function (id) {
                this.movie = {id: id};
                this.$refs.confirmModal.show();
            },
            upsert: function (e) {
                upsert(e, this);
            },
            del: function () {
                deleteMovie(this, this.movie.id);
            },
            close: function () {
                this.movie = {};
                this.$refs.movieModal.hide();
                this.$refs.comfirmModal.hide();
            },
            changeDoubanId: function () {
                if (!!this.movie.doubanId) {
                    this.state.doubanId = true;
                }
            },
            changeRemark: function () {
                if (!!this.movie.remark) {
                    this.state.remark = true;
                }
            },
            step: function () {
                getMovieList(this);
            },
            search: function () {
                this.movies.pager = 1;
                getMovieList(this);
            }
        }
    });
</script>
</body>
</html>