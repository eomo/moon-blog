<header class="header header-border">
    <div class="u-float-left">
        <h1 class="site-title u-float-left">
            <a href="/" class="logo" title="no pursuit coder">
                <img src="/webapps/asserts/image/logo.png" width=65 alt="HICSC.COM"/>
            </a>
        </h1>
    </div>
    <div class="header-center">
        <nav class="navs">
            <div class="single-column-layout single-column-layout-wide menu">
                <ul id="menu" class="menu-ul" style="padding: 0">

                </ul>
            </div>
        </nav>
    </div>
</header>
<script src="https://resources.hicsc.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://resources.hicsc.com/ajax/libs/vue/2.5.16/vue.min.js"></script>
<script src="/webapps/asserts/js/notify.js"></script>
<script src="/webapps/asserts/js/utils.js"></script>
<script>
    (function ($) {
        var appendMenu = function (category) {
            $("#menu").empty();
            category.forEach(function (item) {
                var li = "<li class=\"menu-li\">" +
                    "          <a class=\"menu-item\" href=\" " + item.url + " \">" + item.name + "</a>" +
                    "     </li>"
                $("#menu").append(li);
            });
        }

        var data = function () {
            category = JSON.parse(sessionStorage.getItem('x-app-menu'));
            if (!category || category.length == 0) {
                $.ajax({
                    async: false,
                    type: 'GET',
                    url: '/v1/user/menu',
                    success: function (res) {
                        category = res.data;
                        sessionStorage.setItem('x-app-menu',JSON.stringify(category));
                    }
                });
            }
            return category;
        }
        appendMenu(data());
    }(jQuery));
</script>

