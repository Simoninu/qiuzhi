package com.hjw.qiuzhi.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjw.qiuzhi.common.base.result.R;
import com.hjw.qiuzhi.service.edu.entity.*;
import com.hjw.qiuzhi.service.edu.entity.form.CourseInfoForm;
import com.hjw.qiuzhi.service.edu.entity.vo.CoursePublishVo;
import com.hjw.qiuzhi.service.edu.entity.vo.CourseQueryVo;
import com.hjw.qiuzhi.service.edu.entity.vo.CourseVo;
import com.hjw.qiuzhi.service.edu.feign.OssFileService;
import com.hjw.qiuzhi.service.edu.mapper.*;
import com.hjw.qiuzhi.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    /**
     * 注入课程描述mapper
     */
    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    /**
     * 注入OSS文件service
     */
    @Autowired
    private OssFileService ossFileService;

    /**
     * 注入Video课时mapper
     */
    @Autowired
    private VideoMapper videoMapper;

    /**
     * 注入课程章节mapper
     */
    @Autowired
    private ChapterMapper chapterMapper;

    /**
     * 注入课程评论mapper
     */
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 注入课程收藏mapper
     */
    @Autowired
    private CourseCollectMapper courseCollectMapper;

    /**
     * 保存课程
     * @param courseInfoForm 获取的课程和课程详情信息
     * @return 课程id
     */
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {

        // 课程信息
        Course course = new Course();
        // 赋值操作
        BeanUtils.copyProperties(courseInfoForm, course);
        // 设置课程状态
        course.setStatus(Course.COURSE_DRAFT);
        // 保存课程
        baseMapper.insert(course);

        // 课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        // 赋值
        courseDescription.setDescription(courseInfoForm.getDescription());
        // 设置课程id
        courseDescription.setId(course.getId());
        // 保存课程描述
        courseDescriptionMapper.insert(courseDescription);

        return course.getId();
    }

    /**
     * 根据ID查询课程
     * @param id 课程id
     * @return courseInfoForm 课程信息
     */
    @Override
    public CourseInfoForm getCourseInfoById(String id) {

        // 获取course
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

        // 获取courseDescription
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);

        // 组装courseInfoForm
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;
    }

    /**
     * 更新课程
     * @param courseInfoForm 课程基本信息
     */
    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        // 课程信息
        Course course = new Course();
        // 赋值操作
        BeanUtils.copyProperties(courseInfoForm, course);

        // 更新课程
        baseMapper.updateById(course);

        // 课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        // 赋值
        courseDescription.setDescription(courseInfoForm.getDescription());
        // 设置课程id
        courseDescription.setId(course.getId());
        // 保存课程描述
        courseDescriptionMapper.updateById(courseDescription);

    }

    /**
     * 分页带条件查询课程列表
     * @param page 当前页码
     * @param limit 每页记录数
     * @param courseQueryVo 课程列表查询对象
     * @return 课程分页列表
     */
    @Override
    public IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo) {

        // 组装条件构造器
        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();
        // 根据创建时间倒序
        queryWrapper.orderByDesc("c.gmt_create");

        // 获取查询条件
        // 课程标题
        String title = courseQueryVo.getTitle();
        // 讲师id
        String teacherId = courseQueryVo.getTeacherId();
        // 二级课程分类id
        String subjectId = courseQueryVo.getSubjectId();
        // 一级课程分类id
        String subjectParentId = courseQueryVo.getSubjectParentId();

        // 拼接条件
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }

       // 创建分页参数
        Page<CourseVo> pageParam = new Page<>(page, limit);
        // 执行分页查询
        // 放入分页参数和查询条件参数，mp会自动组装
        List<CourseVo> records = baseMapper.selectPageByCourseQueryVo(pageParam, queryWrapper);
        // 将records设置到pageParam中
        pageParam.setRecords(records);
        return pageParam;
    }

    /**
     * 删除课程封面
     * @param id 课程id
     * @return 删除结果
     */
    @Override
    public boolean removeCoverById(String id) {
        // 获取id获取课程对象
        Course course = baseMapper.selectById(id);
        // 判断课程是否为空
        if (course != null) {
            // 获取封面地址
            String cover = course.getCover();
            // 判断地址是否为空
            if (!StringUtils.isEmpty(cover)) {
                // 删除封面
                R r = ossFileService.removeFile(cover);
                return r.getSuccess();
            }
        }

        return false;
    }

    /**
     * 删除课程
     * @param id 课程id
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCourseById(String id) {

        // 根据courseId删除Video（课时）
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoMapper.delete(videoQueryWrapper);

        // 根据courseId删除Chapter（章节）
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        // 根据courseId删除Comment（评论）
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", id);
        commentMapper.delete(commentQueryWrapper);

        // 根据courseId删除course_collect（收藏）
        QueryWrapper<CourseCollect> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id", id);
        courseCollectMapper.delete(courseCollectQueryWrapper);

        // 根据courseId删除course_description（描述）
        courseDescriptionMapper.deleteById(id);

        // 根据courseId删除课程
        return this.removeById(id);
    }

    /**
     * 根据ID获取课程发布信息
     * @param id 课程ID
     * @return 课程发布信息
     */
    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    /**
     * 根据id发布课程
     * @param id 课程ID
     * @return 发布结果
     */
    @Override
    public boolean publishCourseById(String id) {
        // 改变课程发布状态
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }
}
