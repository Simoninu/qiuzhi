package com.hjw.qiuzhi.service.edu.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjw.qiuzhi.common.base.result.R;
import com.hjw.qiuzhi.service.edu.entity.form.CourseInfoForm;
import com.hjw.qiuzhi.service.edu.entity.vo.CoursePublishVo;
import com.hjw.qiuzhi.service.edu.entity.vo.CourseQueryVo;
import com.hjw.qiuzhi.service.edu.entity.vo.CourseVo;
import com.hjw.qiuzhi.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Api(description = "课程管理")
@CrossOrigin // 跨越
@RestController
@RequestMapping("/admin/edu/course")
@Slf4j
public class CourseController {

    /**
     * 注入课程service
     */
    @Autowired
    private CourseService courseService;

    /**
     * 新增课程
     * @param courseInfoForm 新增课程表单对象
     * @return 课程id
     */
    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId).message("保存成功");
    }

    /**
     * 根据ID查询课程
     * @param id 课程id
     * @return courseInfoForm 课程信息
     */
    @ApiOperation("根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(
            @ApiParam(value = "课程id", required = true)
            @PathVariable("id") String id) {

        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        if (courseInfoForm != null) {
            return R.ok().data("item", courseInfoForm);
        } else {
            return R.ok().message("数据不存在");
        }
    }

    /**
     * 更新课程
     * @param courseInfoForm 课程基本信息
     * @return 更新结果
     */
    @ApiOperation("更新课程")
    @PutMapping("update-course-info")
    public R updateCourseInfoById(
            @ApiParam(value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {

        courseService.updateCourseInfoById(courseInfoForm);

        return R.ok().message("修改成功");
    }

    /**
     * 分页带条件查询课程列表
     * @param page 当前页码
     * @param limit 每页记录数
     * @param courseQueryVo 课程列表查询对象
     * @return 课程分页列表
     */
    @ApiOperation("课程分页列表")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
                      @ApiParam("课程列表查询对象") CourseQueryVo courseQueryVo) {
        // 查询
        IPage<CourseVo> pageModel = courseService.selectPage(page, limit, courseQueryVo);
        // 获取查询到的记录数
        List<CourseVo> records = pageModel.getRecords();
        // 获取总记录数
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 根据id删除课程
     * @param id 课程ID
     */
    @ApiOperation("根据ID删除课程")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id) {
        // TODO:删除课程视频
        // 在此处调用vod中的删除视频文件的接口

        // 删除课程封面 OSS
        courseService.removeCoverById(id);

        // 删除课程
        boolean result = courseService.removeCourseById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 根据ID获取课程发布信息
     * @param id 课程ID
     * @return 课程发布信息
     */
    @ApiOperation("根据ID获取课程发布信息")
    @GetMapping("course-publish/{id}")
    public R getCoursePublishVoById(
        @ApiParam(value = "课程ID", required = true)
        @PathVariable String id){

        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);

        if (coursePublishVo != null) {
            return R.ok().data("item", coursePublishVo);
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 根据id发布课程
     * @param id 课程ID
     * @return 发布结果
     */
    @ApiOperation("根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
        @ApiParam(value = "课程ID", required = true)
        @PathVariable String id){

        boolean result = courseService.publishCourseById(id);

        if (result) {
            return R.ok().message("发布成功");
        } else {
            return R.error().message("数据不存在");
        }
    }
}

