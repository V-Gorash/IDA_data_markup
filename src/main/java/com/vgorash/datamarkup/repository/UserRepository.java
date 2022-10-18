package com.vgorash.datamarkup.repository;

import com.vgorash.datamarkup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
