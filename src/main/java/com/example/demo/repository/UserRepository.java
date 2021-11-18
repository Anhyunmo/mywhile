package com.example.demo.repository;

import com.example.demo.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Board, Long> {

}
