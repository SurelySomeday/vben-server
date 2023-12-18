package top.yxlgx.wink.admin.service.impl;

import org.springframework.stereotype.Service;
import top.yxlgx.wink.admin.entity.Dept;
import top.yxlgx.wink.admin.repository.DeptRepository;
import top.yxlgx.wink.common.jpa.service.BaseServiceImpl;
import top.yxlgx.wink.admin.service.DeptService;

/**
 * @author yanxin
 * @description
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptRepository, Dept, Long> implements DeptService {
}
