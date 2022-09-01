package com.pd.authuser.repositories;

import com.pd.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
