package com.tutofox.bootadmin.service;

import com.tutofox.bootadmin.model.Role;
import com.tutofox.bootadmin.model.User;
import com.tutofox.bootadmin.repository.RoleRepository;
import com.tutofox.bootadmin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Role findRoleById(int role) {
        return roleRepository.findById(role);
    }
    

    public int getRoleId(Set<Role> role) {
        int i = 0;
        for (Role ro : role) {
            i = ro.getId();
        }
        return i;
    }

    public String getRoleName(Set<Role> role) {
        String n = "";
        for (Role ro : role) {
            n = ro.getRole();
        }
        return n;
    }

    public User saveUser(User user) {
        int roleId = this.getRoleId(user.getRoles());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = this.findRoleById(roleId);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

}
