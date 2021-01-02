package com.hjw.qiuzhi.service.edu.controller.admin;


import com.hjw.qiuzhi.common.base.result.R;
import com.hjw.qiuzhi.common.base.result.ResultCodeEnum;
import com.hjw.qiuzhi.common.base.util.ExceptionUtils;
import com.hjw.qiuzhi.service.base.exception.QiuzhiException;
import com.hjw.qiuzhi.service.edu.entity.vo.SubjectVo;
import com.hjw.qiuzhi.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Api(description = "课程类别管理")
@CrossOrigin // 允许跨域
@RestController
@RequestMapping("/admin/edu/subject")
@Slf4j
public class SubjectController {

    /**
     * 注入课程类标管理service
     */
    @Autowired
    private SubjectService subjectService;

    /**
     * Excel批量导入课程类别数据
     * @param file excel文件
     * @return
     */
    @ApiOperation("Excel批量导入课程类别数据")
    @PostMapping("import")
    public R batchImport(
            @ApiParam(value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            subjectService.batchImport(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            // 将异常写入日志文件
            log.error(ExceptionUtils.getMessage(e));
            // 抛出自定义异常
            throw new QiuzhiException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }

    }

    /**
     * 获取课程分类嵌套数据列表
     * @return
     */
    @ApiOperation("嵌套数据列表")
    @GetMapping("nested-list")
    public R nestedList() {
        List<SubjectVo> subjectVoList = subjectService.nestedList();
        return R.ok().data("items", subjectVoList);
    }

}

