package com.hjw.qiuzhi.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hjw.qiuzhi.service.edu.entity.Subject;
import com.hjw.qiuzhi.service.edu.entity.excel.ExcelSubjectData;
import com.hjw.qiuzhi.service.edu.entity.vo.SubjectVo;
import com.hjw.qiuzhi.service.edu.listener.ExcelSubjectDataListener;
import com.hjw.qiuzhi.service.edu.mapper.SubjectMapper;
import com.hjw.qiuzhi.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    /**
     * Excel批量导入课程分类
     * @param inputStream
     */
    @Override
    public void batchImport(InputStream inputStream) {

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectDataListener(baseMapper))
                .excelType(ExcelTypeEnum.XLS)
                .sheet()
                .doRead();
    }

    /**
     * 获取课程分类嵌套数据列表
     * @return
     */
    @Override
    public List<SubjectVo> nestedList() {
        return baseMapper.selectNestedListByParentId("0");
    }
}
