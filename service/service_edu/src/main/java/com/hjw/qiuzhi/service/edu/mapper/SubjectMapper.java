package com.hjw.qiuzhi.service.edu.mapper;

import com.hjw.qiuzhi.service.edu.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjw.qiuzhi.service.edu.entity.vo.SubjectVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Repository
public interface SubjectMapper extends BaseMapper<Subject> {

    /**
     * 获取课程分类嵌套数据列表
     * @param parentId 父id
     * @return 课程分类嵌套数据列表
     */
    List<SubjectVo> selectNestedListByParentId(String parentId);
}
