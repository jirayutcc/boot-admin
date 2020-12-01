package com.tutofox.bootadmin.repository;

import java.util.List;
import java.util.Optional;

import com.tutofox.bootadmin.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAll();
    Role findById(int role);
    Role findByRole(String role);

}
