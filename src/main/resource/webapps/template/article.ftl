<html>
<link href="/webapps/asserts/vue.css" rel="stylesheet" />
<link href="https://cdn.bootcss.com/highlight.js/9.12.0/styles/default.min.css" rel="stylesheet">
<body>
    <article id="article" class="markdown-section"></article>
</body>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script src="http://apps.moondev.cn/js/hyperdown.js"></script>
<script src="https://cdn.bootcss.com/highlight.js/9.12.0/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
<script type="text/javascript">
    var markdownText = "# 详解JVM内存管理与垃圾回收机制2 - 何为垃圾\n" +
        "随着编程语言的发展，GC的功能不断增强，性能也不断提高，作为语言背后的无名英雄，GC离我们的工作似乎越来越远。作为Java程序员，对这一点也许会有更深的体会，我们不需要了解太多与GC相关的知识，就能很好的完成工作。那还有必要深入了解GC吗？学习GC的意义在哪儿？\n" +
        "\n" +
        "不管性能提高到何种程度，GC都需要花费一定的时间，对于实时性要求较高的场景，就必须尽量压低GC导致的最大暂停时间 (GC会导致应用线程处于暂停状态)，举两个例子：\n" +
        "- 实时对战游戏：如果因为GC导致玩家频繁卡顿，任谁都会想摔手机吧。\n" +
        "- 金融交易：在某些对价格非常敏感的交易场景下(比如，外汇交易中价格的变动非常频繁)，如果因为GC导致没有按照交易者指定的价格进行交易，相信我，这些交易者非生吃了你。\n" +
        "\n" +
        "但也有许多场景，GC的最大暂停时间没那么重要，比如，离线分析、视频网站等等。因此，知道**这个GC算法有这样的特征，所以它适合这个场景**，对程序员来说非常有价值，这就是我们学习GC最重要的意义。接下来，我们将一步步走进GC的世界。\n" +
        "\n" +
        "从诞生之初，人们就在思考GC需要完成的3件事情：何为垃圾？何时回收？如何回收？垃圾收集器在对内存进行回收前，第一件事就是要确定这些对象之中哪些还”活着“，哪些已经”死去“，而这些”死去“的对象，也就是我们所说的垃圾。\n" +
        "\n" +
        "## 引用计数法\n" +
        "判断对象是否存活，其中一种方法是给对象添加一个引用计数器，每当有一个地方引用它，计数器的值就加1，当引用失效时，计数器的值减1，任一时刻，如果对象的计数器值为0，那么这个对象就不会再被使用，这种方法被称为引用计数法。在整个回收过程中，引用计数器的值会以极快的速度更新，因而计数值的更新任务变得繁重，而且需要给计数器预留足够大的内存空间，以确保它不会溢出。因此，引用计数法的算法很简单，但在实际运用中要考虑非常多的因素，所以它的实现往往比较复杂，更为重要的是它不能解决对象之间的循环引用问题。\n" +
        "\n" +
        "举个栗子，下面的代码片段展示了为什么引用计数法无法解决循环引用的问题。\n" +
        "```\n" +
        "public class GcDemo {\n" +
        "    public static void main(String[] args) {\n" +
        "        // 在栈中分配内存空间给obj1，然后在堆中创建GcObject对象A\n" +
        "        // 将obj1指向A实例，这时A的引用计数值 = 1\n" +
        "        GcObject obj1 = new GcObject();\n" +
        "        // 同理，GcObject实例B的引用计数值 = 1\n" +
        "        GcObject obj2 = new GcObject();\n" +
        "        // GcObject实例2被引用，所以B引用计数值 = 2\n" +
        "        obj1.instance = obj2;\n" +
        "        // 同理A的引用计数值 = 2\n" +
        "        obj2.instance = obj1;\n" +
        "        // 栈中的obj1不再指向堆中A，这时A的计数值减1，变成1\n" +
        "        obj1 = null;\n" +
        "        // 栈中的obj2不再指向堆中B，这时B的计数值减1，变成1\n" +
        "        obj2 = null;\n" +
        "    }\n" +
        "}\n" +
        "\n" +
        "class GcObject {\n" +
        "    public Object instance = null;\n" +
        "}\n" +
        "\n" +
        "```\n" +
        "仔细阅读代码中的注释，并结合下面的内存结构示意图，应该可以很好的理解其中的原因：如果JVM垃圾收集器采用引用计数法，当obj1和obj2不再指向堆中的实例A、B时，虽然A、B已经不可能再被访问，但彼此间相互引用导致计数器的值不为0，最终导致无法回收A和B。\n" +
        "![图1 引用计数示意图](http://pic.moondev.cn/ReferenceCounting.jpg)\n" +
        "\n" +
        "## 可达性分析\n" +
        "引用计数法有一个致命的问题，即无法释放有循环引用的垃圾，因此，主流的Java虚拟机都没有选用引用计数法来管理内存，而是通过可达性分析 (Reachability Analysis)来判定对象是否存活。\n" +
        "\n" +
        "可达性分析的基本思路是找到一系列被称为”GC Roots“的对象引用 (Reference) 作为起始节点，通过引用关系向下搜索，能被遍历到的 (可到达的) 对象就被判定为存活，其余对象 (也就是没有被遍历到的) 自然被判定为死亡。这里需要着重理解的是：可达性分析本质是找出活的对象来把其余空间判定为“无用”，而不是找出所有死掉的对象并回收它们占用的空间，简略的示意图如下所示。\n" +
        "![图2 可达性算法示意图](http://pic.moondev.cn/reachability_analysis.jpg)\n" +
        "\n" +
        "从图中可以看出，经过可达性分析后，有不少对象没有在GC Roots的引用链条上，其中还包含一些相互引用的对象，这些对象在不久以后都会被垃圾收集器回收，因此，可达性分析算法可以有效解决引用计数法存在的致命问题。\n" +
        "\n" +
        "但是，首次被标记的对象并一定会被回收，它还有自救的机会。一个对象真正的死亡至少需要经历两次标记过程：\n" +
        "1. 标记所有不可达对象，并进行筛选，筛选的标准是该对象覆盖了`finalize()`方法且`finalize()`方法没有被虚拟机调用过，选出的对象将被放置在一个“即将被回收”的队列中。稍后虚拟机会创建一个低优先级的Finalizer线程去遍历队列中的所有对象并执行`finalize()`方法\n" +
        "2. 对队列中的对象进行第二次标记，如果对象在`finalize()`方法中重新与引用链上的任何一个对象建立关联，那么这个对象将被移除队列，而还留在队列中的对象，就会被回收了。\n" +
        "\n" +
        "要正确的实现可达性分析算法，就必须完整地枚举出所有的GC Roots，否则就有可能会漏掉本应存活的对象，如果垃圾收集器错误的回收了这些被漏掉的活对象，将会造成严重的bug。GC Roots作为垃圾回收的起点，必须是一些列活的引用 (Reference) 集合，那这个集合中究竟包含哪些引用？为什么这些引用可以作为GC Roots？要回答好这两个问题，需要对Java对象在内存中布局有一些初步的了解，所以，在下节会对相关知识进行补充。\n" +
        "\n" +
        "## 参考资料\n" +
        "1. 周志明 著; 深入理解Java虚拟机(第2版); 机械工业出版社,2013\n" +
        "2. [知乎上关于GC ROOTS的问题](https://www.zhihu.com/question/53613423/answer/135743258)";
    var parser = new HyperDown,
        html = parser.makeHtml(markdownText);
    document.getElementById("article").innerHTML = html;
</script>

</html>