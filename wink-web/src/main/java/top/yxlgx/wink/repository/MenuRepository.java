package top.yxlgx.wink.repository;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphCrudRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphPagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import top.yxlgx.wink.entity.Menu;
import top.yxlgx.wink.entity.Role;

import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
@Repository
public interface MenuRepository extends EntityGraphCrudRepository<Menu,Long>, EntityGraphPagingAndSortingRepository<Menu,Long>,
        JpaSpecificationExecutor<Menu> {
    @Override
    default Optional<EntityGraph> defaultEntityGraph() {
        return NamedEntityGraph.loading("menu.all").execute(Optional::of);
    }
}
