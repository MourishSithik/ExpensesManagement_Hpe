package com.expenses.repository;

import com.expenses.model.entity.ConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hubinotech on 09/04/22.
 */
@Repository
public interface ConsumerRepository extends JpaRepository<ConsumerEntity,Integer> {

    boolean existsByMobile(String mobile);
}
