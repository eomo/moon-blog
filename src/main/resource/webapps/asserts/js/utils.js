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


var HttpUtils = (function ($) {
    var ajax = function (method, url, param, contentType) {
        var deferred = $.Deferred();
        $.ajax({
            url: url,
            type: method,
            data: param,
            contentType: contentType,
            beforeSend: function (request) {
                // var user = store.get("USER") || {};
                // request.setRequestHeader("X-Api-Token", user.token || "");
            }
        }).done(function (res) {
            if (res.result) {
                deferred.resolve(res.data);
            } else {
                UiTools.alert(res.message,'error');
                deferred.reject(res);
            }
        }).fail(function () {
            alert("网络异常,请稍后再试", "warning");
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