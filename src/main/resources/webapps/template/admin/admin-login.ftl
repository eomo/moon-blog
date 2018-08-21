<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 登录 - HICSC</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <#include "common/admin-css.ftl"/>
    <style>
        body {
            background-repeat: no-repeat;
            background-position: center 0;
            background-size: cover;
            -webkit-background-size: cover;
            -o-background-size: cover;
        }
        #app {
            margin-top: 20%;
            margin-left: calc(50% - 200px);
            width: 400px;
        }
    </style>
</head>
<body>
<div id="app" class="wrapper">
    <div class="single-column-layout single-column-layout-admin u-clearfix">
        <form @submit="onSubmit">
            <b-row>
                <b-col>
                    <b-form-group horizontal
                                  :label-cols="3"
                                  label="用户名："
                                  label-for="input">
                        <b-form-input type="text"
                                      v-model="user.username">
                        </b-form-input>
                    </b-form-group>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-group horizontal
                                  :label-cols="3"
                                  label="密码："
                                  label-for="input">
                        <b-form-input type="password"
                                      v-model="user.password">
                        </b-form-input>
                    </b-form-group>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <b-button type="submit" variant="success" style="float: right;">登 录</b-button>
                </b-col>
            </b-row>
        </form>
    </div>
</div>

<#include "common/admin-js.ftl"/>
<script>
    var app = new Vue({
        el: '#app',
        data: {
            user: {}
        },
        methods: {
            onSubmit: function (evt) {
                evt.preventDefault();
                if (!this.user.username) {
                    UiTools.alert('请输入用户名', 'error');
                    return;
                }
                if (!this.user.password) {
                    UiTools.alert('请输入密码', 'error');
                    return;
                }
                HttpUtils.post('/v1/user/login', this.user).done(function (data) {
                    localStorage.setItem("x-api-token", data);
                    window.location.href = '/admin/article';
                });
            }
        }
    });
</script>
</body>
</html>
