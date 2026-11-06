package com.upiiz.ejercicio_09.repositories;

import org.springframework.stereotype.Repository;

import com.upiiz.ejercicio_09.entities.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    // Requerimos otro método
    Optional<User> findByUsername(String username);
}
