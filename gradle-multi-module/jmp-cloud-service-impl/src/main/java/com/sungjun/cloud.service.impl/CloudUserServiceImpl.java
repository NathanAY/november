package com.sungjun.cloud.service.impl;

import com.sungjun.cloud.service.UserService;
import com.sungjun.dto.entity.UserEntity;
import com.sungjun.dto.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CloudUserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Override
  public UserEntity createUser(String name, String surname, LocalDate birthday) {
    UserEntity user = UserEntity.builder()
        .name(name)
        .surname(surname)
        .birthday(birthday)
        .build();

    return userRepository.save(user);
    // return null;
  }


  @Override
  public List<UserEntity> getAllUsers() {
    return userRepository.findAll()
        .stream()
        .toList();
  }
}
