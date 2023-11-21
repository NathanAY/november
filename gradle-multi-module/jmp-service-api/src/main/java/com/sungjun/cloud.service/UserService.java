package com.sungjun.cloud.service;

import com.sungjun.dto.entity.UserEntity;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public interface UserService {

  UserEntity createUser(String name, String surname, LocalDate birthday);

  List<UserEntity> getAllUsers();

  default double getAverageUsersAge() {
    double averageAge = getAllUsers().stream()
        .mapToInt(user -> Period.between(user.getBirthday(), LocalDate.now()).getYears())
        .average()
        .orElse(0);
    return averageAge;
  }

  static boolean isPayableUserAge(UserEntity user) {
    return user.getBirthday().compareTo(LocalDate.now().minusYears(18)) <= 0;
  }

}
