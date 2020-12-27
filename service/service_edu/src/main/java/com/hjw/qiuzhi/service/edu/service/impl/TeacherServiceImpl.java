package com.hjw.qiuzhi.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjw.qiuzhi.common.base.result.R;
import com.hjw.qiuzhi.service.edu.entity.Teacher;
import com.hjw.qiuzhi.service.edu.entity.vo.TeacherQueryVo;
import com.hjw.qiuzhi.service.edu.feign.OssFileService;
import com.hjw.qiuzhi.service.edu.mapper.TeacherMapper;
import com.hjw.qiuzhi.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    /**
     * 注入oss文件service
     */
    @Autowired
    private OssFileService ossFileService;

    /**
     * 讲师条件查询
     * @param pageParam
     * @param teacherQueryVo
     * @return
     */
    @Override
    public IPage<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVo teacherQueryVo) {

        // 显示分页查询列表
        // 1，排序，按照sort字段排序
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        // 2，分页查询
        if (teacherQueryVo == null) {
            return baseMapper.selectPage(pageParam, queryWrapper);
        }

        // 3，条件查询
        // 获取查询条件
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();

        // 查询
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.likeRight("name", name);
        }

        if (level != null) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(joinDateBegin)) {
            queryWrapper.ge("join_date", joinDateBegin);
        }

        if (!StringUtils.isEmpty(joinDateEnd)) {
            queryWrapper.le("join_date", joinDateEnd);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 根据左关键字查询讲师名列表
     * @param key 关键字
     * @return 讲师名列表
     */
    @Override
    public List<Map<String, Object>> selectNameListByKey(String key) {
        // 组装条件构造器
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        // 查询name列
        queryWrapper.select("name");
        // 模糊查询
        queryWrapper.likeRight("name", key);

        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);

        return list;
    }

    /**
     * 根据id删除讲师头像
     * @param id 讲师id
     * @return
     */
    @Override
    public boolean removeAvatarById(String id) {
        // 获取讲师对象
        Teacher teacher = baseMapper.selectById(id);
        // 判断讲师是否为空
        if (teacher != null) {
            // 获取头像地址
            String avatar = teacher.getAvatar();
            // 判断地址是否为空
            if (!StringUtils.isEmpty(avatar)) {
                // 删除图片
                R r = ossFileService.removeFile(avatar);
                return r.getSuccess();
            }
        }

        return false;
    }


}
