package com.hjw.qiuzhi.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程查询对象
 */
@Data
public class CourseVo implements Serializable {

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
     * 课程一级分类名称
     */
    private String subjectParentTitle;

    /**
     * 课程二级分类名称
     */
    private String subjectTitle;

    /**
     * 讲师名称
     */
    private String teacherName;

    /**
     * 课程课时数
     */
    private Integer lessonNum;

    /**
     * 课程价格
     */
    private String price;

    /**
     * 课程封面
     */
    private String cover;

    /**
     * 课程购买数量
     */
    private Long buyCount;

    /**
     * 课程浏览数量
     */
    private Long viewCount;

    /**
     * 课程状态
     */
    private String status;

    /**
     * 课程创建时间
     */
    private String gmtCreate;
}
