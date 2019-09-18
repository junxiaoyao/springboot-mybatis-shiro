package com.jxy.study.service;

import com.jxy.study.dao.RoleDao;
import com.jxy.study.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-10 10:24
 */
@Service
@Transactional
public class RoleService {

    @Autowired
    RoleDao roleDao;

    public Role getRole(Integer id) {
        return roleDao.getRoleById(id);
    }
}
