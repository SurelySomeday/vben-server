package top.yxlgx.wink.admin;

import com.alibaba.fastjson2.JSON;
import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.query.Query;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import top.yxlgx.wink.admin.entity.*;
import top.yxlgx.wink.admin.repository.RoleRepository;
import top.yxlgx.wink.admin.repository.UserRepository;
import top.yxlgx.wink.admin.service.MenuService;
import top.yxlgx.wink.admin.vo.MenuVO;
import top.yxlgx.wink.admin.vo.UserVO;
import top.yxlgx.wink.common.jpa.orm.JpaResultTransformer;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
class LearnDemoApplicationTests {

    @Resource
    UserRepository userRepository;
    @Resource
    MenuService menuService;
    @Resource
    RoleRepository roleRepository;
    @PersistenceContext
    EntityManager entityManager;


    @Test
    public void testDsl() {
        QMenu qMenu = QMenu.menu;
        // Dynamically gathering fields from QMainBean
        final List<Expression<?>> columns = Arrays.stream(QMenu.class.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(field -> {
                    try {
                        return (Expression<?>) field.get(qMenu);
                    } catch (final Exception e) {
                        log.trace("should never happen", e);
                        return null;
                    }
                })
                .collect(Collectors.toList());
        Expression<?>[] columnsArray = columns.toArray(new Expression<?>[0]);
        JPAQuery<?> query = new JPAQuery<Void>(entityManager);
        List<?> fetch = query
                .from(qMenu)
/*                .leftJoin(qMenu.children)
                .leftJoin(qMenu.roles)*/
/*                .where(qMenu.children.any().createTime.lt(
                        Expressions.dateTimeTemplate(Timestamp.class, "{0}", new Date())))
                .where(qMenu.pid.in(
                        JPAExpressions.select(qMenu.menuId)
                                .from(qMenu)
                                .where(qMenu.pid.isNotNull())
                                .limit(1)
                ))*/
                .fetch();
        //System.out.println(fetch);
        JPAQuery<?> query2 = new JPAQuery<Void>(entityManager);
        QUser qUser=QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.userId.eq(1L));
        List<UserVO> fetch2 = query2
                .select(Projections.bean(UserVO.class, qUser.userId))
                .from(qUser)
                .where(builder)
                .fetch();
        System.out.println(JSON.toJSON(fetch2));

    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Test
    void test() {
        TypedQuery<Menu> query = entityManager.createQuery("""
                        SELECT
                                        ROW_NUMBER() OVER(
                                            PARTITION BY at.id
                                            ORDER BY at.createTime
                                        ) AS menuId
                                     FROM Menu at
                                     ORDER BY at.id
                                                       
                         """, Menu.class)
                .unwrap(Query.class)
                .setTupleTransformer(new JpaResultTransformer(Menu.class));
        List<Menu> resultList = query.getResultList();
        System.out.println(resultList);
        Query query1 = entityManager.createNativeQuery("""
                        SELECT
                                        ROW_NUMBER() OVER(
                                            PARTITION BY at.menu_id
                                            ORDER BY at.create_time
                                        ) AS menu_id
                                     FROM sys_menu at
                                     ORDER BY at.menu_id
                                                       
                         """)
                .unwrap(NativeQueryImpl.class)
                .setTupleTransformer(new JpaResultTransformer(MenuVO.class));
        resultList = query1.getResultList();
        System.out.println(resultList);
    }


    @SuppressWarnings("unchecked")
    @Test

    void contextLoads() {
//        Session session = entityManager.unwrap(Session.class);
//        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
//        List<UserVO> list =session.createNativeQuery(
//                "SELECT {pr.*}, {pt.*} " +
//                "FROM sys_user pr LEFT JOIN sys_dept pt ON pr.dept_id = pt.dept_id", UserVO.class)
//                .addEntity("pr", User.class)
//                .addEntity("pt", Dept.class)
//                .list();
//        System.out.println(JSON.toJSON(list));
//
//        List<UserVO> userVOS = userRepository.findUsersByDept_DeptId(1L);
//        System.out.println(JSON.toJSON(userVOS));

    }




}
