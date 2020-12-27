package com.hjw.qiuzhi.service.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("一级分类id")
    private String id;

    @ApiModelProperty("一级分类名称")
    private String title;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("二级分类")
    private List<SubjectVo> children = new ArrayList<>();
}
