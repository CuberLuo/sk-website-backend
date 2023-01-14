package net.sk.webbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sk.webbackend.mapper.CommentMapper;
import net.sk.webbackend.pojo.Comment;
import net.sk.webbackend.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>implements CommentService {
}
