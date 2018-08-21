<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>分类 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/category_list.css" type="text/css" media="screen">
</head>
<body>
<#include "common/app-header.ftl"/>
<header class="single-column-layout single-column-layout-wide category-header">
    <h1 class="topic-header-title">分类</h1>
</header>
<div class="single-column-layout single-column-layout-wide">
    <#list categories as item>
        <div class="list list--borderedBottom">
            <a class="listItem u-clearfix u-block" href="${item.url!}">
                <img class="list-avatar" src="${item.image}${item.format2!}" width="40" height="40">
                <div class="list-meta">
                    <div class="list-title">${item.name}</div>
                    <div class="list-description">${item.desc!}</div>
                </div>
            </a>
        </div>
    </#list>
</div>
</body>
</html>