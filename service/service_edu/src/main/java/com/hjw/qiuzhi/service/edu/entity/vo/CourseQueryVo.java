package com.hjw.qiuzhi.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程搜索对象
 */
@Data
public class CourseQueryVo implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 课程标题
     */
    private String title;

    /**
     * 讲师id
     */
    private String teacherId;

    /**
     * 课程分类一级id
     */
    private String subjectParentId;

    /**
     * 课程二级分类id
     */
    private String subjectId;

}
