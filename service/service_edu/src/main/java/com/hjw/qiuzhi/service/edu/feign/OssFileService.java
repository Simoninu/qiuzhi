package com.hjw.qiuzhi.service.edu.feign;

import com.hjw.qiuzhi.common.base.result.R;
import com.hjw.qiuzhi.service.edu.feign.fallback.OssFileServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Oss文件服务调用接口
 */
@Service
@FeignClient(value = "service-oss", fallback = OssFileServiceFallBack.class)
public interface OssFileService {

    /**
     * 测试oss文件服务调用
     * @return
     */
    @GetMapping("/admin/oss/file/test")
    R test();

    /**
     * 测试oss文件删除
     * @param url
     */
    @DeleteMapping("/admin/oss/file/remove")
    R removeFile(@RequestBody String url);
}
