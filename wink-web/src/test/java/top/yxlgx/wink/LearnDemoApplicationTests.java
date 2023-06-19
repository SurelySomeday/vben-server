package top.yxlgx.wink;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.sql.JPASQLQuery;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.JpaSubQuery;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import top.yxlgx.wink.config.orm.jpa.JpaResultTransformer;
import top.yxlgx.wink.entity.Menu;
import top.yxlgx.wink.entity.QMenu;
import top.yxlgx.wink.entity.Role;
import top.yxlgx.wink.entity.User;
import top.yxlgx.wink.entity.vo.MenuVO;
import top.yxlgx.wink.repository.RoleRepository;
import top.yxlgx.wink.repository.UserRepository;
import top.yxlgx.wink.service.MenuService;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
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
        List<MenuVO> fetch = query.select(Projections.bean(MenuVO.class, qMenu.pid.min().as("pid")))
                .from(qMenu)
                .leftJoin(qMenu.children)
                .leftJoin(qMenu.roles)
                .where(qMenu.children.any().createTime.eq(
                        Expressions.dateTimeTemplate(Date.class, "{0}", new Date())))
                .where(qMenu.pid.in(
                        JPAExpressions.select(qMenu.menuId)
                                .from(qMenu)
                                .where(qMenu.pid.isNotNull())
                                .limit(1)
                ))
                .fetch();
        System.out.println(fetch);

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
        Optional<Role> roleOptional = roleRepository.findById(1L);
        Optional<User> userOptional = userRepository.findById(1L);

        User user = userOptional.get();
        Role role = roleOptional.get();
        user.setRoles(Set.of(role));
        //roleRepository.save(role);
        userRepository.save(user);
        Iterable<User> all = userRepository.findAll();
        System.out.println(all);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.printf(encode);

    }


}
