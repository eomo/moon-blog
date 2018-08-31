### MOON博客
简单的Java博客程序，功能仅自用，不适合商业使用，后端使用`SpringBoot`，管理后台前端使用`bootstrap-vue.js`实现，入门的同学可以参考。

#### 如要部署使用，请注意：
1. 管理后台没有用户管理功能，仅在配置文件中配置一个用户当做管理员
2. 管理后台的每个接口均需要在`header`中添加`token`，服务端将`token`放在内存中，因此当重启应用后`token`会失效，需要重新登录，`token`有效期30分钟
3. 管理后台中所有需要图片的地方均使用文本框填写URL，但后台中有上传图片至七牛的功能，这样做的目的是，可以随时修改图片上传的`bucket`和路径，在我看来更方便一些
4. 文章内容的markdown解析放在服务端，但众所周知，Java端并没有非常好用的解析库，因此在后端使用SegmentFault的`HyperDown.js`作为解析库，然后配合SegmentFault的样式使用，达到了比较好的显示效果
5. 因担心Java后端使用js脚本来解析markdown的性能问题(后来发现并没有)，服务端会把每次解析后的接口放到内存里，因此，在第一次访问某篇文章的时候可能会比较慢，但这个慢你不一定能够感知到，毕竟Java8中使用JS的性能并没有我们想象那么不堪
6. 好像没有其他的了.....

#### 使用到的开源项目
1. 评论的emoji支持：https://github.com/mervick/emojionearea
2. 管理后台前端支持：https://bootstrap-vue.js.org/
3. Vue.js：https://cn.vuejs.org/index.html
4. Markdown代码高亮：https://highlightjs.org/
5. Markdown渲染：https://github.com/SegmentFault/HyperDown.js
6. 文章Markdown渲染效果采用segmentfault的样式
7. 前端提示插件：https://notifyjs.jpillora.com/
8. 文章Markdown编辑器：https://simplemde.com/

#### 版权声明
后端以及管理后台的前端源码，你可以随意使用，没有任何限制。

但是

**博客首页以及部分页面参考[bigfa](https://fatesinger.com/)，如果你在保留了前端页面的前提下公开部署使用，请向原主题制作者付费，购买链接：[Sierra主题](https://fatesinger.com/78021)，请尊重原作者的劳动成果**。

#### 演示
目前网站托管在腾讯云(1核1G内存40G云盘)上面，你可以访问：[www.hicsc.com](https://www.hicsc.com) 体验
