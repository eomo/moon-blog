<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,minimal-ui">
    <title>不用到处翻啦，所有的文章都在这里啦 - HICSC</title>
    <link rel="icon" type="image/x-icon" href="/webapps/asserts/image/favicon.ico" />
    <link rel="stylesheet" href="/webapps/asserts/header.css" type="text/css" media="screen">
    <link rel="stylesheet" href="/webapps/asserts/archive.css" type="text/css" media="screen">
    <#include "common/app-baidu.ftl"/>
</head>

<body>
<#include "common/app-header.ftl"/>
<div class="single-column-layout single-column-layout-wide archive-wrapper">
    <div class="archive-list-wrapper">
        <#if amap??>
            <#list amap as key, articles>
            <div class="archive-year-wrapper">
                <h2 class="archive-year">${key!}</h2>
                <ul class="archive-list">
                    <#list articles as item>
                    <li class="archive-item">
                        <img class="u-float-right archive-list-image" src="${item.image}${item.aformat}">
                        <a class="archive-item-title" href="/post/${item.id}">${item.title}</a>
                        <div class="archive-item-meta">${item.viewCount} 阅读 / ${item.commentCount} 回应</div>
                    </li>
                    </#list>
                </ul>
            </div>
            </#list>
        </#if>
    </div>
</div>
</body>

</html>