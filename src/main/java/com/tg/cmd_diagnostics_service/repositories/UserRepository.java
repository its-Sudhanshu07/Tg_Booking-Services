package com.tg.cmd_diagnostics_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tg.cmd_diagnostics_service.models.User;



public interface UserRepository extends JpaRepository<User,String>{

}
