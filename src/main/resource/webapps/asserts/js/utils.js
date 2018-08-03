var JsonTools = (function () {
    return {
        toJson: function (obj) {
            return JSON.parse(JSON.stringify(obj));
        }
    };
})();

var UiTools = (function () {
    return {
        alert: function (message, type) {
            $.notify(message, {className: type, globalPosition: 'top center'});
        }
    };
})();

var CookieUtils = (function () {
    return {
        set: function (key, value) {
            if (!!key && !!value) {
                var exdate = new Date();
                exdate.setDate(exdate.getDate() + 30);
                document.cookie = key + '=' + escape(value) + ';expires=' + exdate.toGMTString();
            }
        },
        get: function (key) {
            if (document.cookie.length > 0) {
                start = document.cookie.indexOf(key + '=')
                if (start != -1) {
                    start = start + key.length + 1;
                    end = document.cookie.indexOf(';', start)
                    if (end == -1) end = document.cookie.length
                    return unescape(document.cookie.substring(start, end))
                }
            }
            return "";
        },
        remove: function (key) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = get(key);
            if (!!cval)
                document.cookie = key + '=' + cval + ';expires=' + exp.toGMTString();
        }
    }
})();

var HttpUtils = (function ($) {
    var ajax = function (method, url, param, contentType) {
        var deferred = $.Deferred();
        $.ajax({
            url: url,
            type: method,
            data: param,
            contentType: contentType,
            beforeSend: function (request) {
                token = localStorage.getItem('x-api-token');
                request.setRequestHeader("x-api-token", token || "");
            }
        }).always(function (xhr) {
            if (xhr.status == 404) {
                UiTools.alert('您访问的API不存在，请联系管理员', 'error');
            } else if (xhr.status == 403) {
                UiTools.alert('您没有权限进行此操作，请您离开', 'error');
            } else if (xhr.status == 500) {
                UiTools.alert('系统出现错误，请稍后再试', 'error');
            }
        }).done(function (res) {
            if (res.result) {
                deferred.resolve(res.data);
            } else {
                errorMsg = !!res.message ? res.message : '系统异常,请稍后再试';
                UiTools.alert(errorMsg, 'error');
                deferred.reject(res);
            }
        }).fail(function () {
            UiTools.alert('系统异常,请稍后再试', 'error');
        });
        return deferred;
    };

    return {
        parameterByName: function (name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = null;
            if (location.search) {
                results = regex.exec(location.search);
            }
            else {
                results = regex.exec(location.href);
            }
            return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        },

        get: function (url, param) {
            return ajax("GET", url, param || null, "application/x-www-form-urlencoded");
        },

        post: function (url, param) {
            return ajax("POST", url, param ? JSON.stringify(param) : null, "application/json");
        },

        submit: function (url, param) {
            return ajax("POST", url, param || null, "application/x-www-form-urlencoded");
        },

        put: function (url, param) {
            return ajax("PUT", url, param ? JSON.stringify(param) : null, "application/json");
        },

        delete: function (url, param) {
            return ajax("DELETE", url, param || null, "application/x-www-form-urlencoded");
        }
    };
})(jQuery);