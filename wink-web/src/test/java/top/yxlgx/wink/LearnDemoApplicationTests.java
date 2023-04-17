package top.yxlgx.wink;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.yxlgx.wink.entity.Role;
import top.yxlgx.wink.entity.User;
import top.yxlgx.wink.repository.RoleRepository;
import top.yxlgx.wink.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
class LearnDemoApplicationTests {

    @Resource
    UserRepository userRepository;
    @Resource
    RoleRepository roleRepository;


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



}
