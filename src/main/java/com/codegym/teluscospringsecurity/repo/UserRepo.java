package com.codegym.teluscospringsecurity.repo;

import com.codegym.teluscospringsecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
}
