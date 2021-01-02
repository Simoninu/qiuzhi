package com.hjw.qiuzhi.service.edu.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjw.qiuzhi.common.base.result.R;
import com.hjw.qiuzhi.service.edu.entity.Teacher;
import com.hjw.qiuzhi.service.edu.entity.vo.TeacherQueryVo;
import com.hjw.qiuzhi.service.edu.feign.OssFileService;
import com.hjw.qiuzhi.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OssFileService ossFileService;
    /**
     * 获取教师列表
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("list")
    public R listAll() {
        List<Teacher> list = teacherService.list();
        return R.ok().data("items", list);
    }

    /**
     * 根据id删除讲师
     * @param id
     */
    @ApiOperation(value = "根据ID删除讲师", notes = "根据ID删除讲师，逻辑删除")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam(value = "讲师ID", required = true) @PathVariable String id) {
        // 删除讲师头像
        teacherService.removeAvatarById(id);

        // 删除讲师
        boolean result = teacherService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 分页查询讲师列表
     * @param page 当前页码
     * @param limit 每页记录数
     * @return 讲师列表
     */
    @ApiOperation("讲师分页列表")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
                      @ApiParam("讲师列表查询对象") TeacherQueryVo teacherQueryVo) {
        // 通过参数构建Page对象
        Page<Teacher> pageParam = new Page<>(page, limit);
        // 查询
        IPage<Teacher> pageModel = teacherService.selectPage(pageParam, teacherQueryVo);
        // 获取查询到的记录数
        List<Teacher> records = pageModel.getRecords();
        // 获取总记录数
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 新增讲师
     * @param teacher
     * @return
     */
    @ApiOperation("新增讲师")
    @PostMapping("save")
    public R save(@ApiParam(value = "讲师对象", required = true) @RequestBody Teacher teacher) {
        boolean result = teacherService.save(teacher);
        if (result) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    /**
     * 更新讲师
     * @param teacher
     * @return
     */
    @ApiOperation("修改讲师")
    @PutMapping("update")
    public R updateById(@ApiParam(value = "讲师对象", required = true) @RequestBody Teacher teacher) {
        boolean result = teacherService.updateById(teacher);
        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 根据id获取讲师信息
     * @param id
     * @return
     */
    @ApiOperation("根据id获取讲师信息")
    @GetMapping("get/{id}")
    public R getById(@ApiParam(value = "讲师id", required = true) @PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return R.ok().data("item", teacher);
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 根据ids批量删除讲师
     */
    @ApiOperation(value = "根据ID列表删除讲师")
    @DeleteMapping("batch-remove")
    public R removeRows(
            @ApiParam(value = "讲师ID列表", required = true)
            @RequestBody List<String> idList) {
        boolean result = teacherService.removeByIds(idList);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 根据左关键字查询讲师名列表
     * @return
     */
    @ApiOperation(value = "根据左关键字查询讲师名列表")
    @GetMapping("list/name/{key}")
    public R selectNameListByKey(
            @ApiParam(value = "查询关键字", required = true)
            @PathVariable String key ) {
        List<Map<String, Object>> nameList = teacherService.selectNameListByKey(key);
        return R.ok().data("nameList", nameList);
    }

    /**
     * 测试服务调用
     * @return
     */
    @ApiOperation("测试服务调用")
    @GetMapping("test")
    public R test() {
        ossFileService.test();
        return R.ok();
    }

    /**
     * 测试并发
     * @return
     */
    @ApiOperation("测试并发")
    @GetMapping("test_concurrent")
    public R testConcurrent(){
        log.info("test_concurrent");
        return R.ok();
    }

    /**
     * 测试服务容错
     * @return
     */
    @GetMapping("/message1")
    public String message1() {
        return "message1";
    }

    /**
     * 测试服务容错
     * @return
     */
    @GetMapping("/message2")
    public String message2() {
        return "message2";
    }
}

