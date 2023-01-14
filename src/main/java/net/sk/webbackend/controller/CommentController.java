package net.sk.webbackend.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import net.sk.webbackend.entity.Result;
import net.sk.webbackend.entity.StatusCode;
import net.sk.webbackend.mapper.CommentMapper;
import net.sk.webbackend.pojo.Comment;
import net.sk.webbackend.utils.SensitiveWordUtil;
import net.sk.webbackend.utils.timeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    private final Gson gson=new Gson();


    @GetMapping("/queryAll")
    public String getAllComment(){
        List<Comment> commentsList = commentMapper.selectList(null);
        return gson.toJson(commentsList);
    }

    @GetMapping("/getTotalCount")
    public Result getTotalCount(){
        Long count = commentMapper.selectCount(null);
        System.out.println(count);
        return new Result(StatusCode.OK,"查询总记录数成功",count);
    }

    @GetMapping("/getPageSizeComments")
    public Result getPageSizeComments(@RequestParam("current") String current){
        int currentPage=Integer.parseInt(current);//页码
        int pageSize = 5;//每页的记录数
        Page<Comment> page = new Page<>(currentPage, pageSize);
        page.addOrder(OrderItem.desc("commentDate"));//按留言时间排序
        commentMapper.selectPage(page,null);
        List<Comment> commentsList =page.getRecords();
        return new Result(StatusCode.OK,"查询成功",commentsList);
    }

    @PostMapping("/setComment")
    public Result setComment(@RequestBody Map<String, Object> map){
        String nickname = map.get("nickname") == null ? "" : (String)map.get("nickname");
        String commentContent = map.get("commentContent") == null ? "" : (String)map.get("commentContent");
        String commentDate = map.get("commentDate") == null ? "" : (String)map.get("commentDate");
        String ip = map.get("ip") == null ? "" : (String)map.get("ip");

        //String类型的评论日期转换为LocalDateTime类型
        LocalDateTime commentLocalDateTime=timeUtil.strToLocalDateTime(commentDate);
        Long comment_timestamp=timeUtil.localDateTimeToTimestamp(commentLocalDateTime);
        Long now_timestamp = timeUtil.localDateTimeToTimestamp(LocalDateTime.now());
        System.out.println(comment_timestamp+"    "+now_timestamp);
        //System.out.println("内容长度:"+commentContent.length());
        //检测参数是否为空
        if(nickname.trim().equals("")||
            commentContent.trim().equals("")||
            commentDate.trim().equals("")||
            ip.trim().equals("")||
            Math.abs(comment_timestamp-now_timestamp)>60*1000){
            return new Result(StatusCode.ERROR,"参数有误",null);
        }else if(nickname.length()>8||commentContent.length()>400||commentDate.length()>25||ip.length()>15){
            return new Result(StatusCode.ERROR,"参数长度超出限制",null);
        }else{
            Map<String, Object> ip_latest_map = commentMapper.selectMapByIp(ip);
            if(ip_latest_map!=null){//此ip非首次留言
                LocalDateTime ipLatestLocalDateTime = (LocalDateTime) ip_latest_map.get("commentDate");
                Long ip_latest_timestamp = timeUtil.localDateTimeToTimestamp(ipLatestLocalDateTime);
                int gap_time= (int) (comment_timestamp-ip_latest_timestamp);
                if (gap_time<60*1000){//同一个ip一分钟之内之内留言一次
                    return new Result(StatusCode.ERROR,"您留言的速度过快,请稍后留言",null);
                }
            }
            Comment comment = new Comment();
            nickname= SensitiveWordUtil.replace(nickname);
            commentContent=SensitiveWordUtil.replace(commentContent);
            comment.setNickname(nickname);
            comment.setCommentContent(commentContent);
            comment.setCommentDate(commentDate);
            comment.setIp(ip);
            int result = commentMapper.insert(comment);
            Map<String, Object> successMap = new HashMap<>();
            successMap.put("nickname",nickname);
            successMap.put("commentContent",commentContent);
            successMap.put("commentDate",commentDate);
            successMap.put("ip",ip);
            if(result>0){
                return new Result(StatusCode.OK,"留言成功",successMap);
            }else{
                return new Result(StatusCode.ERROR,"留言失败",null);
            }


        }
    }



}
