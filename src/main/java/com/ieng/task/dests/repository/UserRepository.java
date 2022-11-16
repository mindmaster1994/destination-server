package com.ieng.task.dests.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ieng.task.dests.model.User;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}