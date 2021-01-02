package com.hjw.qiuzhi.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程发布信息类
 */
@Data
public class CoursePublishVo implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    private String id;

    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程封面
     */
    private String cover;

    /**
     * 课程课时
     */
    private Integer lessonNum;

    /**
     * 课程一级分类标题
     */
    private String subjectParentTitle;

    /**
     * 课程二级分类标题
     */
    private String subjectTitle;

    /**
     * 课程讲师名
     */
    private String teacherName;

    /**
     * 课程价格
     */
    private String price;//只用于显示
}
