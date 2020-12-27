package com.hjw.qiuzhi.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjw.qiuzhi.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hjw.qiuzhi.service.edu.entity.vo.TeacherQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 讲师条件查询列表
     * @param pageParam
     * @param teacherQueryVo
     * @return
     */
    IPage<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVo teacherQueryVo);

    /**
     * 根据左关键字查询讲师名列表
     * @param key
     * @return
     */
    List<Map<String,Object>> selectNameListByKey(String key);

    /**
     * 根据id删除讲师头像
     * @param id 讲师id
     * @return
     */
    boolean removeAvatarById(String id);
}
