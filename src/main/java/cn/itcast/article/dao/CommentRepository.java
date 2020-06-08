package cn.itcast.article.dao;

import cn.itcast.article.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author wolf623
 * @create 2020-06-06-20:47
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    Page<Comment> findByParentId(String parentId, Pageable pageable);
}
