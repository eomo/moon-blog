<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 图片文件上传 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico"/>
    <#include "common/admin-css.ftl"/>
</head>
<body>
<div class="wrapper">
    <#include "common/admin-header.ftl"/>
    <header class="admin-header">
        <div class="single-column-layout single-column-layout-admin u-clearfix" style="width: 1000px">
            <div class="u-float-left">
                <h1 class="admin-header-name">文件上传</h1>
                <div class="admin-header-desc"><p>所以文件均上传至七牛云存储，如要使用，请按照对应云平台的开发文档修改(注：bucket已在服务端代码写死)</p></div>
            </div>
        </div>
    </header>
    <div  v-cloak id="app" class="single-column-layout single-column-layout-admin admin-container" style="width: 1000px">
        <b-row>
            <b-col>
                <b-form-group horizontal
                              :label-cols="3"
                              label="设置路径前缀"
                              label-for="input">
                    <b-form-input type="text"
                                  placeholder="image/jpg/"
                                  v-model="key">
                    </b-form-input>
                </b-form-group>
            </b-col>
            <b-col>
                <b-form-file v-model="file" accept="image/*" @input="upload"
                             placeholder="请选择图片">
                </b-form-file>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <p class="prefix-desc">路径前缀可以用来分类文件，例如： <span class="prfix-example describe-info">image/jpg/</span>your-file-name.jpg
                </p>
                <div v-if="!!fileDownloadUrl" class="prefix-desc" style="color: forestgreen">文件下载地址：{{fileDownloadUrl}}</div>
            </b-col>
        </b-row>
    </div>
</div>
<#include "common/admin-js.ftl"/>
<script>
    var app = new Vue({
        el: '#app',
        data: {
            file: null,
            key: null,
            fileDownloadUrl: null
        },
        methods: {
            upload: function () {
                if (!this.key) {
                    UiTools.alert('请设置路径前缀后，重新选择文件', 'error');
                    return;
                }
                if (!!this.file) {
                    HttpUtils.upload(this.file, this.key).done(function (url) {
                        UiTools.alert('文件上传成功','success');
                        app.fileDownloadUrl = url;
                    });
                }
            }
        }
    });
</script>
</body>
</html>