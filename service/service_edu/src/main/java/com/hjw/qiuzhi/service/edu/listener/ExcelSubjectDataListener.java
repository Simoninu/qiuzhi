package com.hjw.qiuzhi.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjw.qiuzhi.service.edu.entity.Subject;
import com.hjw.qiuzhi.service.edu.entity.excel.ExcelSubjectData;
import com.hjw.qiuzhi.service.edu.mapper.SubjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Ecxel数据导入监听器
 */
@Slf4j
@NoArgsConstructor // 无参构造函数
@AllArgsConstructor // 全参构造函数
public class ExcelSubjectDataListener extends AnalysisEventListener<ExcelSubjectData> {

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private SubjectMapper subjectMapper;

    /**
     * 遍历每一行的记录
     * @param data
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext analysisContext) {
        log.info("解析到一条记录: {}", data);

        // 处理获取到的数据
        String levelOneTitle = data.getLevelOneTitle();
        String levelTwoTitle = data.getLevelTwoTitle();
        log.info("levelOneTitle: {}", levelOneTitle);
        log.info("levelTwoTitle: {}", levelTwoTitle);

        // 判断一级分类是否重复
        Subject subjectLevelOne = this.getByTitle(levelOneTitle);
        String parentId = null;
        if (subjectLevelOne == null) {
            // 组装数据：Subject
            Subject subject = new Subject();
            subject.setParentId("0");
            subject.setTitle(levelOneTitle); // 一级分类名称
            // 存入数据库：
            subjectMapper.insert(subject);
            parentId =  subject.getId();
        } else {
            parentId = subjectLevelOne.getId();
        }

        // 判断一级分类下二级分类是否重复
        Subject subjectLevelTwo = this.getSubByTitle(levelTwoTitle, parentId);
        if (subjectLevelTwo == null) {
            // 将二级分类存入数据库
            Subject subject = new Subject();
            subject.setTitle(levelTwoTitle);
            subject.setParentId(parentId);
            // 将二级分类添加到数据库
            subjectMapper.insert(subject);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }

    /**
     * 根据分类名称查询这个一级分类是否存在
     * @param title 一级分类名称
     * @return subject对象
     */
    private Subject getByTitle(String title) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0"); // 一级分类

        return subjectMapper.selectOne(queryWrapper);
    }

    /**
     * 根据分类名称和父id查询这个二级分类是否存在
     * @param title 一级分类名称
     * @param parentId 一级分类id
     * @return subject对象
     */
    private Subject getSubByTitle(String title, String parentId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId); // 父id

        return subjectMapper.selectOne(queryWrapper);
    }
}
