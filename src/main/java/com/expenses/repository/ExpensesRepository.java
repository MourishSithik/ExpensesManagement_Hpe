package com.expenses.repository;

import com.expenses.model.entity.ExpensesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hubinotech on 10/04/22.
 */
public interface ExpensesRepository extends JpaRepository<ExpensesEntity , Integer> {
    List<ExpensesEntity> findByConsumerIdAndCreatedAtBetween(int consumerId, String startDate, String endDate);
}
