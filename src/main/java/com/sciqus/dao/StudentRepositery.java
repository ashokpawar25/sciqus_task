package com.sciqus.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sciqus.entity.Student;

public interface StudentRepositery extends JpaRepository<Student,Integer> {

	Student findByUsernameAndPassword(String username, String password);

	Student findByUsername(String username);

}
