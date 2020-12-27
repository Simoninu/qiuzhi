package com.hjw.qiuzhi.service.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.hjw.qiuzhi.service.oss.service.FileService;
import com.hjw.qiuzhi.service.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * 文件服务实现类
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 注入oss配置类
     */
    @Autowired
    private OssProperties ossProperties;

    /**
     * 阿里云oss文件上传
     * @param inputStream 输入流
     * @param module 文件夹名称
     * @param originalFilename 原始文件名
     * @return 文件在oss服务器上的url地址
     */
    @Override
    public String upload(InputStream inputStream, String module, String originalFilename) {

        // 获取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // 判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        if (!ossClient.doesBucketExist(bucketname)) {
            // 创建bucket
            ossClient.createBucket(bucketname);
            // 设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        // 构建日期路径：avatar/2019/02/26/文件名
        String folder = new DateTime().toString("yyyy/MM/dd");

        //文件名：uuid.扩展名
        String fileName = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String key = module + "/" + folder + "/" + fileName + fileExtension;

        //文件上传至阿里云
        ossClient.putObject(bucketname , key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        // https://qiuzhi-file.oss-cn-beijing.aliyuncs.com/default.jpg
        // 返回url地址
        return "https://" + bucketname + "." + endpoint + "/" + key;
    }

    /**
     * 阿里云oss 文件删除
     * @param url 文件的url地址
     */
    @Override
    public void removeFile(String url) {

        // 获取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // 创建oss实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);

        // 组装文件地址
        // https://qiuzhi-file.oss-cn-beijing.aliyuncs.com/avatar/02.jpg
        String host = "https://" + bucketname + "." + endpoint + "/";
        String objectName = url.substring(host.length());

        // 删除文件。
        ossClient.deleteObject(bucketname, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
