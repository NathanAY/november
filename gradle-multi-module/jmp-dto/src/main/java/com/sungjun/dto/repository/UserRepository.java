package com.sungjun.dto.repository;

import com.sungjun.dto.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByName(String name);
}
