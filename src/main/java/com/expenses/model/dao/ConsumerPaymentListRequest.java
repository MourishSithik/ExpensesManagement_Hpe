package com.expenses.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by hubinotech on 10/04/22.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsumerPaymentListRequest {
    private int consumerId;
    private String startDate;
    private String endDate;
}
