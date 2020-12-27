package com.hjw.qiuzhi.service.edu.service.impl;

import com.hjw.qiuzhi.service.edu.entity.Comment;
import com.hjw.qiuzhi.service.edu.mapper.CommentMapper;
import com.hjw.qiuzhi.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author hjw
 * @since 2020-12-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
