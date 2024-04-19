package com.practice.mysource.repository;


import com.practice.mysource.entity.Confirmation;
import com.practice.mysource.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation,Long> {

    Optional<Confirmation> findByKey(String key);
    Optional<Confirmation> findByUser(User user);

}
