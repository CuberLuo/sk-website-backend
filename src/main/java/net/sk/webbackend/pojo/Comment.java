package net.sk.webbackend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comments")
public class Comment {
    private Long id;
    private String nickname;
    private String commentContent;
    private String commentDate;
    private String ip;
}
