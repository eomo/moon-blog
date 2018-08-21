<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 文章分类管理 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <#include "common/admin-css.ftl"/>
</head>
<body>
<div v-cloak id="app" class="wrapper">
    <#include "common/admin-header.ftl"/>
    <header class="admin-header">
        <div class="single-column-layout single-column-layout-admin u-clearfix">
            <div class="u-float-left">
                <h1 class="admin-header-name">文章分类</h1>
                <div class="admin-header-desc"><p>足迹、图书、电影等为固定分类，不支持修改</p></div>
            </div>
            <div class="u-float-right">
                <b-button @click="showAddCategoryModal" variant="success">添加分类</b-button>
            </div>
        </div>
    </header>
    <div class="single-column-layout single-column-layout-admin admin-container">
        <b-table striped Fixed :items="categories" :fields="fields" show-empty="true" empty-text="暂无数据">
            <template slot="action" slot-scope="row">
                <b-button @click="showUpdateCategoryModal(row.item)" variant="outline-primary btn-sm">修改</b-button>
                <b-button @click="showDeleteAlert(row.item)" variant="outline-danger btn-sm">删除</b-button>
                <b-button @click="stickCategory(row.item.id)" variant="outline-success btn-sm">置顶</b-button>
            </template>
            <template slot="menu" slot-scope="row">
                {{row.item.menu == 1 ? '是' : '否'}}
            </template>
            <template slot="image" slot-scope="row">
                <a v-bind:href="row.item.image" target="_blank">
                    <img  style="width: 60px;height: 60px;" v-bind:src="row.item.image + (!!row.item.format2?row.item.format2:'')">
                </a>
            </template>
            <template slot="url" slot-scope="row">
                {{!!row.item.url? '指定页面' : '默认分类页'}}
            </template>
        </b-table>
    </div>
    <b-modal ref="confirmModal" centered
             title="风险提示"
             header-bg-variant="danger"
             header-text-variant="light"
             ok-title="确认"
             cancel-title="取消"
             cancel-variant="success"
             ok-variant="danger"
             @ok="deleteCategory"
             @cancel="close">
        <p>删除此分类后，导航菜单将不再显示，确认删除吗？</p>
    </b-modal>
    <b-modal ref="categoryModal"
             :title="title"
             size="lg"
             ok-title="确认"
             cancel-title="取消"
             @ok="upsertCategory"
             @cancel="close">
        <form @submit.stop.prevent="handleSubmit">
            <b-container fluid>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="分类编号："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="建议使用一个意义的英文单词，比如旅行分类，可以使用travel"
                                          v-model="category.code"
                                          :state="state.id"
                                          @change="changeId"
                                          aria-describedby="categoryIdFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="categoryIdFeedback">
                                请输入正确的分类编号，只能是数字和字母
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="分类名称："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入文章分类名称"
                                          v-model="category.name"
                                          :state="state.name"
                                          @change="changeName"
                                          aria-describedby="categoryNameFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="categoryNameFeedback">
                                请输入正确的分类名称，限制10个字符数以内
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="分类描述："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入文章分类描述"
                                          v-model="category.desc"
                                          :state="state.desc"
                                          @change="changeDesc"
                                          aria-describedby="categoryDescFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="categoryDescFeedback">
                                请输入文章分类描述，字符数在128以内
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="分类LOGO："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入分类图片地址"
                                          v-model="category.image"
                                          :state="state.image"
                                          @change="changeImage"
                                          aria-describedby="categoryImageFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="categoryImageFeedback">
                                请输入正确的图片URL，图片URL需要以http或者https开头
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="图片处理："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="分类页图片显示,建议尺寸:194x194，示例:?both/80x80"
                                          v-model="category.format1">
                            </b-form-input>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="图片处理："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="所有分类页图片显示,建议尺寸:80x80，示例:?both/80x80"
                                          v-model="category.format2">
                            </b-form-input>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="跳转链接："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="点击分类后跳转的页面链接"
                                          v-model="category.url"
                                          :state="state.url">
                            </b-form-input>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="2">菜单显示：</b-col>
                    <b-col>
                        <b-form-radio-group v-model="category.menu"
                                            :options="options"
                                            name="radioInline">
                        </b-form-radio-group>
                    </b-col>
                </b-row>
            </b-container>
        </form>
    </b-modal>
</div>
<#include "common/admin-js.ftl"/>
<script>
    var getCategoryList = function (vm) {
        HttpUtils.get('/v1/category', null).done(function (data) {
            vm.categories = data;
        });
    };

    var upsertCategory = function (e, vm) {
        e.preventDefault();
        if (!vm.category.code || !/^[0-9a-zA-Z]*$/.test(vm.category.code)) {
            vm.state.id = false;
            return;
        }
        if (!vm.category.name || vm.category.name.length > 10) {
            vm.state.name = false;
            return;
        }
        if (!vm.category.desc || vm.category.desc.length > 128) {
            vm.state.desc = false;
            return;
        }
        if (!vm.category.image || !(vm.category.image.startsWith('http://', 0) || vm.category.image.startsWith('https://', 0))) {
            vm.state.image = false;
            return;
        }
        if (!!vm.category.id) {
            HttpUtils.post('/v1/category', vm.category).done(function (data) {
                UiTools.alert('新的文章分类已添加!', 'success');
                getCategoryList(vm);
                vm.category = {};
                vm.$refs.categoryModal.hide();
            })
        } else {
            HttpUtils.put('/v1/category', vm.category).done(function (data) {
                UiTools.alert('文章分类修改成功!', 'success');
                getCategoryList(vm);
                vm.category = {};
                vm.$refs.categoryModal.hide();
            })
        }
    };

    var deleteCatetory = function (vm, id) {
        HttpUtils.delete('/v1/category/' + id, null).done(function (data) {
            UiTools.alert('文章分类已删除!', 'success');
            getCategoryList(vm);
        });
    };

    var stickCategory = function (vm, id) {
        HttpUtils.post('/v1/category/stick/' + id, null).done(function (data) {
            getCategoryList(vm);
        });
    };

    var app = new Vue({
        el: '#app',
        data: {
            title: null,
            categories: null,
            category: {},
            state: {
                id: null,
                name: null,
                desc: null,
                image: null
            },
            fields: [
                {key: 'code', label: '分类编号'},
                {key: 'name', label: '分类名称'},
                {key: 'menu', label: '菜单显示'},
                {key: 'url', label: '跳转'},
                {key: 'image', label: '分类图片'},
                {key: 'action', label: '操作'}
            ],
            options: [
                {text: '显示', value: '1'},
                {text: '不显示', value: '0'}
            ]
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.get('/v1/category', null).done(function (data) {
                    self.categories = data;
                });
            });
        },
        methods: {
            showAddCategoryModal: function () {
                this.title = '添加文章分类';
                this.category.menu = '0';
                this.$refs.categoryModal.show();
            },
            showUpdateCategoryModal: function (item) {
                this.title = '修改文章分类';
                this.category = JsonTools.toJson(item);
                this.$refs.categoryModal.show();
            },
            showDeleteAlert: function (item) {
                this.category = JsonTools.toJson(item);
                this.$refs.confirmModal.show();
            },
            upsertCategory: function (e) {
                upsertCategory(e, this);
            },
            deleteCategory: function () {
                deleteCatetory(this, this.category.id);
            },
            stickCategory: function (id) {
                stickCategory(this, id);
            },
            close: function () {
                this.category = {};
                this.$refs.categoryModal.hide();
                this.$refs.comfirmModal.hide();
            },
            changeName: function () {
                if (!!this.category.name && this.category.name.length <= 10) {
                    this.state.name = true;
                }
            },
            changeDesc: function () {
                if (!!this.category.desc && this.category.desc.length <= 10) {
                    this.state.desc = true;
                }
            },
            changeImage: function () {
                if (!!this.category.image && (this.category.image.startsWith('http://', 0) || this.category.image.startsWith('https://', 0))) {
                    this.state.image = true;
                }
            },
            changeId: function () {
                if (!!this.category.code && /^[0-9a-zA-Z]*$/.test(this.category.code)) {
                    this.state.id = true;
                }
            }
        }
    });
</script>
</body>
</html>