package net.sk.webbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.sk.webbackend.pojo.Comment;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    Map<String,Object> selectMapByIp(String ip);
}