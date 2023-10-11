package com.jolly.phonics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.jolly.phonics.entity.User;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	
    User findByUsername(String username);
    
    User findByEmail(String email);
    
    @Override
    <S extends User> S save(S s);
}
