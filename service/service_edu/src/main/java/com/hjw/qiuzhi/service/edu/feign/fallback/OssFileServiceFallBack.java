package com.hjw.qiuzhi.service.edu.feign.fallback;

import com.hjw.qiuzhi.common.base.result.R;
import com.hjw.qiuzhi.service.edu.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * sentinel容错类
 * 在oss文件服务器挂掉，让程序能够正常运行
 */
@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {
    @Override
    public R test() {
        return R.error();
    }

    @Override
    public R removeFile(String url) {
        log.info("熔断保护");
        return R.error();
    }
}
