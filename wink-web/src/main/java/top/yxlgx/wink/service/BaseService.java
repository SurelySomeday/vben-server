package top.yxlgx.wink.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import top.yxlgx.wink.entity.query.BaseQuery;

import java.util.List;
import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
public interface BaseService<T, ID> {

    List<T> findAll(@Nullable BaseQuery baseQuery);

    Page<T> findAll(@Nullable BaseQuery baseQuery, Pageable pageable);

    Optional<T> findOne(@Nullable Specification<T> spec);

    List<T> findAll(@Nullable Specification<T> spec);

    Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);

    List<T> findAll(@Nullable Specification<T> spec, Sort sort);

    <S extends T> S save(S entity);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
    void deleteAllById(Iterable<? extends ID> ids);
    void deleteById(ID ids);

    long delete(Specification<T> spec);


}
