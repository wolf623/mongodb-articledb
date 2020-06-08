package cn.itcast.article.service;

import cn.itcast.article.dao.CommentRepository;
import cn.itcast.article.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wolf623
 * @create 2020-06-06-20:23
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存一个评论
     * @param comment
     */
    public void saveComment(Comment comment) {
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 更新评论
     * @param comment
     */
    public void updateComment(Comment comment) {
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 根据id删除评论
     * @param id
     */
    public void deleteCommentById(String id) {
        //调用dao
        commentRepository.deleteById(id);
    }

    /**
     * 查询所有评论
     * @return
     */
    public List<Comment> findCommentList() {
        //调用dao
        return commentRepository.findAll();
    }

    /**
     * 根据id查询评论
     * @param id
     * @return
     */
    public Comment findCommentById(String id) {
        //调用dao
        return commentRepository.findById(id).get();
    }

    public Page<Comment> findCommentListPageByParentId(String parentId, int page, int size) {
       return commentRepository.findByParentId(parentId, PageRequest.of(page-1, size));
    }

    /**
     * 点赞 - 效率低
     * @param id
     */
    public void updateCommentThumbupToIncrementingOld(String id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setLikeNum(comment.getLikeNum()+1);
        commentRepository.save(comment);
    }

    /**
     * 点赞 - 效率高
     * @param id
     */
    public void updateCommentLikeNum(String id) {
        //查询对象
        Query query = Query.query(Criteria.where("_id").is(id));

        //更新对象
        Update update = new Update();
        //局部更新，相当于$set
        update.inc("likeNum");

        //参数1：查询对象
        //参数2：更新对象
        //参数3：集合的名字或实例类的类型Comment.class
        mongoTemplate.updateFirst(query, update, "comment");
    }
}
