package top.yxlgx.wink.repository;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphCrudRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphPagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import top.yxlgx.wink.entity.Dept;
import top.yxlgx.wink.entity.Menu;

import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
@Repository
public interface DeptRepository extends EntityGraphCrudRepository<Dept,Long>, EntityGraphPagingAndSortingRepository<Dept,Long>,
        JpaSpecificationExecutor<Dept> {
    @Override
    default Optional<EntityGraph> defaultEntityGraph() {
        return NamedEntityGraph.loading("dept.all").execute(Optional::of);
    }
}
