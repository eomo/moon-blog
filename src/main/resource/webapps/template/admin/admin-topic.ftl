<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 专题管理</title>
    <#include "common/admin-css.ftl"/>
</head>
<body>
<div id="app" class="wrapper">
    <#include "common/admin-header.ftl"/>
    <header class="admin-header">
        <div class="single-column-layout single-column-layout-admin u-clearfix">
            <div class="u-float-left">
                <h1 class="admin-header-name">专题</h1>
                <div class="admin-header-desc"><p>所有专题将显示在首页左栏，因此专题数量不宜太多且专题描述图片尽量小些，以免影响首页加载速度</p></div>
            </div>
            <div class="u-float-right">
                <b-button @click="showModal({})" variant="success">添加专题</b-button>
            </div>
        </div>
    </header>
    <div class="single-column-layout single-column-layout-admin admin-container">
        <b-table striped Fixed :items="topics" :fields="fields">
            <template slot="action" slot-scope="row">
                <b-button @click="showModal(row.item)" variant="outline-primary btn-sm">修改</b-button>
                <b-button @click="showConfirmModal(row.item)" variant="outline-danger btn-sm">删除</b-button>
            </template>
            <template slot="image" slot-scope="row">
                <a v-bind:href="row.item.image" target="_blank">
                    <img v-bind:src="row.item.image + row.item.format">
                </a>
            </template>
        </b-table>



        <#--<b-container fluid>-->
            <#--<b-row class="u-margin-button15" v-for="item in topics">-->
                <#--<b-col cols="1"><img :src="item.image" width="40" height="40"></b-col>-->
                <#--<b-col cols="2">{{item.name}}</b-col>-->
                <#--<b-col>{{item.desc}}</b-col>-->
                <#--<b-col cols="2" align-self="end">-->
                    <#--<b-button variant="outline-primary btn-sm" @click="showModal(item)">修改</b-button>-->
                    <#--<b-button variant="outline-danger btn-sm" @click="showConfirmModal(item)">删除</b-button>-->
                <#--</b-col>-->
            <#--</b-row>-->
        <#--</b-container>-->
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
        <p>删除此专题后，首页将不再显示，确认删除吗？</p>
    </b-modal>
    <b-modal ref="topicModal"
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
                                      label="专题编号："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="建议使用一个意义的英文单词，比如MySQL专题，可以使用mysql"
                                          v-model="topic.id"
                                          :state="state.id"
                                          @change="changeId"
                                          aria-describedby="topicIdFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="topicIdFeedback">
                                请输入正确的专题编号，只能是数字和字母
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="专题名称："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入专题名称"
                                          v-model="topic.name"
                                          :state="state.name"
                                          @change="changeName"
                                          aria-describedby="topicNameFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="topicNameFeedback">
                                请输入正确的专题名称，限制10个字符数以内
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="专题描述："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入专题描述"
                                          v-model="topic.desc"
                                          :state="state.desc"
                                          @change="changeDesc"
                                          aria-describedby="topicDescFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="topicDescFeedback">
                                请输入专题描述，字符数在128以内
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <b-form-group horizontal
                                      :label-cols="2"
                                      label="专题LOGO："
                                      label-for="input">
                            <b-form-input type="text"
                                          placeholder="请输入专题图片地址"
                                          v-model="topic.image"
                                          :state="state.image"
                                          @change="changeImage"
                                          aria-describedby="topicImageFeedback">
                            </b-form-input>
                            <b-form-invalid-feedback id="topicImageFeedback">
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
                                          placeholder="首页专题图片显示,建议尺寸:60x60，示例:?both/80x80"
                                          v-model="topic.format">
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
    var getTopicList = function (vm) {
        HttpUtils.get('/v1/topic', null).done(function (data) {
            vm.topics = data;
        });
    };

    var upsert = function (e, vm) {
        e.preventDefault();
        if (!vm.topic.id || !/^[0-9a-zA-Z]*$/.test(vm.topic.id)) {
            vm.state.id = false;
            return;
        }
        if (!vm.topic.name || vm.topic.name.length > 32) {
            vm.state.name = false;
            return;
        }
        if (!vm.topic.desc || vm.topic.desc.length > 128) {
            vm.state.desc = false;
            return;
        }
        if (!vm.topic.image || !(vm.topic.image.startsWith('http://', 0) || vm.topic.image.startsWith('https://', 0))) {
            vm.state.image = false;
            return;
        }
        if (!!vm.topic.id) {
            HttpUtils.post('/v1/topic', vm.topic).done(function (data) {
                UiTools.alert('专题修改成功!', 'success');
                getTopicList(vm);
                vm.topic = {};
                vm.$refs.topicModal.hide();
            })
        } else {
            HttpUtils.put('/v1/topic', vm.topic).done(function (data) {
                UiTools.alert('新的专题已添加!', 'success');
                getTopicList(vm);
                vm.topic = {};
                vm.$refs.topicModal.hide();
            })
        }
    };

    var deleteTopic = function (vm, id) {
        HttpUtils.delete('/v1/topic/' + id, null).done(function (data) {
            UiTools.alert('专题已删除!', 'success');
            getTopicList(vm);
        });
    };

    var app = new Vue({
        el: '#app',
        data: {
            title: null,
            topics: null,
            topic: {},
            state: {
                id: null,
                name: null,
                desc: null,
                image: null
            },
            fields: [
                {key: 'id', label: '专题编号'},
                {key: 'name', label: '专题名称'},
                {key: 'desc', label: '专题描述'},
                {key: 'image', label: '专题图片'},
                {key: 'action', label: '操作'}
            ]
        },
        created: function () {
            let self = this;
            self.$nextTick(function () {
                HttpUtils.get('/v1/topic', null).done(function (data) {
                    self.topics = data;
                });
            });
        },
        methods: {
            showModal: function (item) {
                if (!!item.id) {
                    this.title = '修改专题';
                    this.topic = JsonTools.toJson(item);
                } else {
                    this.title = '添加专题';
                }
                this.$refs.topicModal.show();
            },
            showConfirmModal: function (item) {
                this.topic = item;
                this.$refs.confirmModal.show();
            },
            upsert: function (e) {
                upsert(e, this);
            },
            del: function () {
                deleteTopic(this, this.topic.id);
            },
            close: function () {
                this.topic = {};
                this.$refs.topicModal.hide();
                this.$refs.comfirmModal.hide();
            },
            changeName: function () {
                if (!!this.topic.name && this.topic.name.length <= 10) {
                    this.state.name = true;
                }
            },
            changeDesc: function () {
                if (!!this.topic.desc && this.topic.desc.length <= 10) {
                    this.state.desc = true;
                }
            },
            changeImage: function () {
                if (!!this.topic.image && (this.topic.image.startsWith('http://', 0) || this.topic.image.startsWith('https://', 0))) {
                    this.state.image = true;
                }
            },
            changeId: function () {
                if (!!this.topic.id && /^[0-9a-zA-Z]*$/.test(this.topic.id)) {
                    this.state.id = true;
                }
            }
        }
    });
</script>
</body>
</html>