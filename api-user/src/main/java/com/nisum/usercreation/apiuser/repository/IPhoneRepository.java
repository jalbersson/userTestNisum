package com.nisum.usercreation.apiuser.repository;

import com.nisum.usercreation.apiuser.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhoneRepository extends JpaRepository<Phone, Long> {
}
