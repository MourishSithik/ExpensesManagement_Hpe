package com.expenses.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by hubinotech on 09/04/22.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsumerRegisterRequest {
    public String fname;
    public String lname;
    public String mobile;
    public String email;
}
