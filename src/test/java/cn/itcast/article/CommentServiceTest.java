package cn.itcast.article;

import cn.itcast.article.po.Comment;
import cn.itcast.article.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wolf623
 * @create 2020-06-06-20:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApplication.class)
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Test
    public void testSaveComment(){
        Comment comment=new Comment();
        comment.setArticleId("100000");
        comment.setContent("测试添加的数据");
        comment.setCreateDateTime(LocalDateTime.now());
        comment.setUserId("1003");
        comment.setNickName("凯撒大帝");
        comment.setState("1");
        comment.setLikeNum(0);
        comment.setReplyNum(0);
        commentService.saveComment(comment);
    }

    @Test
    public void testFindCommentList() {
        List<Comment> commentList = commentService.findCommentList();
        if (commentList != null) {
            System.out.println(commentList);
        } else {
            System.out.println("Get nothing...");
        }
    }

    /**
     * 测试根据id查询
     */
    @Test
    public void testFindCommentById(){
        Comment comment = commentService.findCommentById("5edb9143ba1e0163340a7dcf");
        System.out.println(comment);
    }

    /**
     * 测试根据父id查询子评论的分页列表
     */
    @Test
    public void testFindCommentListPageByParentid(){
        Page<Comment> pageResponse = commentService.findCommentListPageByParentId("3", 1, 2);
        System.out.println("----总记录数："+pageResponse.getTotalElements());
        System.out.println("----当前页数据："+pageResponse.getContent());
    }

    /**
     * 点赞数+1
     */
    @Test
    public void testUpdateCommentLikeNum(){
//对3号文档的点赞数+1
        commentService.updateCommentLikeNum("5edb9143ba1e0163340a7dcf");
    }
}
