package com.pd.authuser.services;

import com.pd.authuser.dtos.UserDto;
import com.pd.authuser.models.UserModel;
import com.pd.authuser.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    UserModel findById(UUID userId);

    void deleteById(UUID userId);

    UserModel save(UserDto userDto);

    UserModel update(UserDto userDto);

    void updatePassword(UserDto userDto);

    void updateImage(UserDto userDto);
}
