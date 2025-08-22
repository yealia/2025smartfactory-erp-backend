package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRePository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
