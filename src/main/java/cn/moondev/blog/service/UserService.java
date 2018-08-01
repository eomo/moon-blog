package cn.moondev.blog.service;

import cn.moondev.blog.configuration.MessageCode;
import cn.moondev.blog.dto.UserDTO;
import cn.moondev.framework.utils.RandomStringUtils;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Value("${admin.user.name}")
    private String userName;
    @Value("${admin.user.password}")
    private String password;
    @Autowired
    private Cache tokenCache;

    public String login(UserDTO user) {
        if (Strings.isNullOrEmpty(user.username) || Strings.isNullOrEmpty(user.password)) {
            throw MessageCode.ex(MessageCode.LOGIN_PARAMS_ERROR);
        }
        if (!user.username.equals(userName) || !user.password.equals(password)) {
            throw MessageCode.ex(MessageCode.LOGIN_PARAMS_ERROR);
        }
        String token = RandomStringUtils.randomAlphanumeric(64);
        tokenCache.put("token", token);
        return token;
    }

    public boolean checkToken(String token) {
        try {
            Object cacheToken = tokenCache.get("token", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "";
                }
            });
            return Objects.isNull(cacheToken) ? false : token.equals(cacheToken.toString());
        } catch (ExecutionException e) {
            LOG.error("验证token出现异常", e);
            return false;
        }
    }
}
