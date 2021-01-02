package com.hjw.qiuzhi.service.edu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjw.qiuzhi.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjw.qiuzhi.service.edu.entity.vo.CoursePublishVo;
import com.hjw.qiuzhi.service.edu.entity.vo.CourseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 分页带条件查询课程列表
     * @param pageParam 分页参数
     * @param queryWrapper 条件构造器
     * @return 课程分页列表
     */
    List<CourseVo> selectPageByCourseQueryVo(
            // mp会自动组装分页参数
            Page<CourseVo> pageParam,
            // mp会自动组装queryWrapper：
            // @Param(Constants.WRAPPER) 和 xml文件中的 ${ew.customSqlSegment} 对应
            @Param(Constants.WRAPPER) QueryWrapper<CourseVo> queryWrapper);

    /**
     * 根据ID获取课程发布信息
     * @param id 课程ID
     * @return 课程发布信息
     */
    CoursePublishVo selectCoursePublishVoById(String id);
}
