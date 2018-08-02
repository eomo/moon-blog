package cn.moondev.blog.configuration;

import cn.moondev.blog.service.UserService;
import cn.moondev.framework.annotation.Permit;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private final ConcurrentMap<Method, Boolean> methodPermitionMap = new ConcurrentHashMap();
    private final ConcurrentMap<Class<?>, Boolean> typePermitionMap = new ConcurrentHashMap();

    private static final String ERROR_URL = "/error";
    private static final String LOGIN_URL = "/admin/ddl";

    private final UserService userService;

    public AuthenticationInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getDispatcherType() == DispatcherType.ASYNC) {
            return true;
        }
        String uri = request.getRequestURI();
        // 错误提示页面
        if (LOGIN_URL.equalsIgnoreCase(uri) || uri.startsWith(ERROR_URL)) {
            return true;
        }
        // 资源文件
        if (handler == null || handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        // Permit注解
        if (handler instanceof HandlerMethod) {
            boolean permit = methodPermit(((HandlerMethod) handler).getMethod()) ||
                    typePermit(((HandlerMethod) handler).getBeanType());
            if (permit) {
                return true;
            }
        }
        return handleAuth(request, response);
    }

    private boolean handleAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = getToken(request);
        if (Strings.isNullOrEmpty(token)) {
            response.sendRedirect("/admin/ddl");
            return false;
        }
        if (!userService.checkToken(token)) {
            response.setStatus(403);
            return false;
        }
        return true;
    }

    private boolean methodPermit(Method method) {
        Boolean permit = methodPermitionMap.get(method);
        if (null == permit) {
            permit = null != method.getAnnotation(Permit.class);
            methodPermitionMap.put(method, permit);
        }
        return permit;
    }

    private boolean typePermit(Class<?> beanType) {
        Boolean permit = typePermitionMap.get(beanType);
        if (null == permit) {
            permit = null != beanType.getAnnotation(Permit.class);
            typePermitionMap.put(beanType, permit);
        }
        return permit;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("x-api-token");
        if (Strings.isNullOrEmpty(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
