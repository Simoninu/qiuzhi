package com.hjw.qiuzhi.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjw.qiuzhi.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hjw.qiuzhi.service.edu.entity.form.CourseInfoForm;
import com.hjw.qiuzhi.service.edu.entity.vo.CoursePublishVo;
import com.hjw.qiuzhi.service.edu.entity.vo.CourseQueryVo;
import com.hjw.qiuzhi.service.edu.entity.vo.CourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程和课程详情信息
     * @param courseInfoForm 获取的课程和课程详情信息
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 根据ID查询课程
     * @param id 课程id
     * @return courseInfoForm 课程信息
     */
    CourseInfoForm getCourseInfoById(String id);

    /**
     * 更新课程
     * @param courseInfoForm 课程基本信息
     */
    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    /**
     * 分页带条件查询课程列表
     * @param page 当前页码
     * @param limit 每页记录数
     * @param courseQueryVo 课程列表查询对象
     * @return 课程分页列表
     */
    IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo);

    /**
     * 删除课程封面
     * @param id 课程id
     */
    boolean removeCoverById(String id);

    /**
     * 删除课程
     * @param id 课程id
     * @return 删除结果
     */
    boolean removeCourseById(String id);

    /**
     * 根据ID获取课程发布信息
     * @param id 课程ID
     * @return 课程发布信息
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据id发布课程
     * @param id 课程ID
     * @return 发布结果
     */
    boolean publishCourseById(String id);
}
