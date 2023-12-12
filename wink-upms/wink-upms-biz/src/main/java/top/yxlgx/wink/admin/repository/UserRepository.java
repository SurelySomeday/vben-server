package top.yxlgx.wink.admin.repository;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphCrudRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphPagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import top.yxlgx.wink.admin.entity.User;

import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
@Repository
public interface UserRepository extends EntityGraphCrudRepository<User,Long>, EntityGraphPagingAndSortingRepository<User,Long>,
        JpaSpecificationExecutor<User> {
    @Override
    default Optional<EntityGraph> defaultEntityGraph() {
        return NamedEntityGraph.loading("user.all").execute(Optional::of);
    }

    Optional<User> findByUsername(String username);
}
