package br.com.erudio.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.authserver.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}