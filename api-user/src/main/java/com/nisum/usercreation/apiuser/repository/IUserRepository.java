package com.nisum.usercreation.apiuser.repository;

import com.nisum.usercreation.apiuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
}
