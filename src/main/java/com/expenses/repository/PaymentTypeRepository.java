package com.expenses.repository;

import com.expenses.model.entity.PaymentTypeGatewayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hubinotech on 09/04/22.
 */
@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentTypeGatewayEntity,Integer> {

}
