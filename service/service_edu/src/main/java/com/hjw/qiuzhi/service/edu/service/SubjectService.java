package com.hjw.qiuzhi.service.edu.service;

import com.hjw.qiuzhi.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hjw.qiuzhi.service.edu.entity.vo.SubjectVo;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
public interface SubjectService extends IService<Subject> {

    /**
     * Excel批量导入课程分类
     * @param inputStream
     */
    void batchImport(InputStream inputStream);

    /**
     * 获取课程分类嵌套数据列表
     * @return
     */
    List<SubjectVo> nestedList();
}
