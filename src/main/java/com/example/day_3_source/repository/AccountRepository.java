package com.example.day_3_source.repository;

import com.example.day_3_source.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>  {
    Optional<Account> findById(Long id);

    @Query("select a from Account a where a.username = :username")
    Optional<Account> findByUsername(@PathParam("username") String username);
}
