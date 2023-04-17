package top.yxlgx.wink.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.yxlgx.wink.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author yanxin.
 * @Date 2023/3/6 9:57.
 * Created by IntelliJ IDEA
 * File Description:
 */
@NamedEntityGraph(
        name = "user.all",
        attributeNodes =  {
                @NamedAttributeNode("roles")
        }
)
@Getter
@Setter
@Entity

@Table(name = "sys_user", indexes = {
        @Index(name = "sys_user_username",columnList = "username", unique = true)
    }
)
@SQLDelete(sql = "update sys_user set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
public class User extends BaseEntity implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;

    @Column(name = "username")
    String username;
    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private Integer deleted=0;

    @ManyToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "sys_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;


    /**
     * 用户部门
     */
    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    @PreRemove
    public void deleteUser() {
        this.deleted = 1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(item->new SimpleGrantedAuthority(item.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.deleted==0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
