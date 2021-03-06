package cn.moondev.blog.provider;

import cn.moondev.blog.dto.QiniuSignatureDTO;
import cn.moondev.framework.provider.random.RandomStringUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QiniuOperations {

    private static final Logger LOG = LoggerFactory.getLogger(QiniuOperations.class);

    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String host;

    public QiniuOperations(String accessKey, String secretKey, String endpoint, String host) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.endpoint = endpoint;
        this.host = host;
    }

    /**
     * 上传第三方资源到七牛云
     *
     * @param sourceRemoteUrl 资源地址
     * @param bucket          存储空间名称
     * @param prefix          文件名称前缀，最终的资源名称：前缀 + fileName，需要以'/'结尾
     * @param domain          存储空间绑定的域名，需要以'/'结尾
     * @return
     */
    public String fetch(String sourceRemoteUrl, String bucket, String prefix, String domain) {
        Configuration cfg = new Configuration(Zone.autoZone());
        // bucket资源管理工具类
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        // 获取文件名称，新的文件名与原来的文件名称一致
        String fileName = RandomStringUtils.randomAlphabetic(2).toLowerCase() + "_" +
                sourceRemoteUrl.substring(sourceRemoteUrl.lastIndexOf('/') + 1);
        try {
            FetchRet fetchRet = bucketManager.fetch(sourceRemoteUrl, bucket, prefix + fileName);
            return domain + fetchRet.key;
        } catch (QiniuException e) {
            LOG.error("上传至七牛云失败", e);
            return null;
        }
    }

    /**
     * 获取上传的Token
     *
     * @param bucket 存储空间名称
     * @return
     */
    public QiniuSignatureDTO getUploadToken() {
        QiniuSignatureDTO dto = new QiniuSignatureDTO();
        Auth auth = Auth.create(accessKey, secretKey);
        dto.token = auth.uploadToken("hicsc", null, 30, null);
        dto.endpoint = endpoint;
        dto.host = host;
        return dto;
    }
}
