package top.yxlgx.wink.admin.service;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.yxlgx.wink.admin.query.BaseQuery;
import top.yxlgx.wink.common.jpa.util.QueryHelp;

import java.util.List;
import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
@SuppressWarnings("unchecked")
public class BaseServiceImpl<R extends CrudRepository<T, ID> & JpaSpecificationExecutor<T>, T,ID> implements BaseService<T,ID> {

    @Autowired
    protected R repository;


    @Override
    public List<T> findAll(BaseQuery baseQuery) {
        return repository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = QueryHelp.getPredicate(root, baseQuery, criteriaQuery,criteriaBuilder);
            return criteriaQuery.where(predicate).getRestriction();
        });
    }

    @Override
    public Page<T> findAll(BaseQuery baseQuery, Pageable pageable) {
        return repository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = QueryHelp.getPredicate(root, baseQuery,criteriaQuery, criteriaBuilder);
            return criteriaQuery.where(predicate).getRestriction();
        },pageable);
    }

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return repository.findOne(spec);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return repository.findAll(spec);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec,pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return repository.findAll(spec,sort);
    }

    @Override
    public <S extends T> S save(S entity) {
        return this.repository.save(entity);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return this.repository.saveAll(entities);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public long delete(Specification<T> spec) {
        return repository.delete(spec);
    }
}
