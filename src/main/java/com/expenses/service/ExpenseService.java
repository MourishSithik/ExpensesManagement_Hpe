package com.expenses.service;

import com.expenses.model.dao.ConsumerPaymentInfoRequest;
import com.expenses.model.dao.ConsumerPaymentListRequest;
import com.expenses.model.dao.ConsumerRegisterRequest;
import com.expenses.model.entity.ConsumerEntity;
import com.expenses.model.entity.ExpensesEntity;
import com.expenses.model.entity.PaymentTypeGatewayEntity;
import com.expenses.repository.ConsumerRepository;
import com.expenses.repository.ExpensesRepository;
import com.expenses.repository.PaymentTypeRepository;
import com.expenses.utils.Constants;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hubinotech on 09/04/22.
 */
@Service
public class ExpenseService {

    @Autowired
    ConsumerRepository consumerRepository;

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    @Autowired
    ExpensesRepository expensesRepository;

    public boolean isMobileNoExist(String mobile)  {
        return consumerRepository.existsByMobile(mobile);
    }

    public List<PaymentTypeGatewayEntity> getPaymentTypeInfoAvailable() {
        return paymentTypeRepository.findAll();
    }

    public void enrollConsumer(ConsumerRegisterRequest consumerRegisterRequest) {
        ConsumerEntity consumerEntity = new ConsumerEntity();
        BeanUtils.copyProperties(consumerRegisterRequest, consumerEntity);
        consumerEntity.setCreatedAt(currentDateTime());
        consumerRepository.save(consumerEntity);
    }

    public boolean consumerIdExist(int consumerId) {
        return consumerRepository.existsById(consumerId);
    }

    public boolean paymentGatewayIdExist(int paymentGatId) {
        return paymentTypeRepository.existsById(paymentGatId);
    }

    public void insertPaymentInfo(ConsumerPaymentInfoRequest consumerPaymentInfoRequest) {
        ExpensesEntity expensesEntity = new ExpensesEntity();
        expensesEntity.setAmount(consumerPaymentInfoRequest.getAmount());
        expensesEntity.setConsumerId(consumerPaymentInfoRequest.getConsumerId());
        expensesEntity.setPaymentGatewayId(consumerPaymentInfoRequest.getPaymentGatewayId());
        expensesEntity.setCreatedAt(currentDateTime());
        expensesRepository.save(expensesEntity);
    }

    public JSONObject getConsumerPaymentList(ConsumerPaymentListRequest consumerPaymentListRequest) {
       List<ExpensesEntity> list =  expensesRepository.findByConsumerIdAndCreatedAtBetween(consumerPaymentListRequest.getConsumerId(),consumerPaymentListRequest.getStartDate(),consumerPaymentListRequest.getEndDate());
        long totAmount = 0;
        for(ExpensesEntity exp: list) {
            totAmount = totAmount + Long.parseLong(exp.getAmount());
        }
        JSONObject obj = new JSONObject();
        obj.put(Constants.STATUS_CODE, 200);
        obj.put(Constants.MESSAGE, "Success");
        obj.put("paymentInfo", list);
        obj.put("totAmount", totAmount);
        return obj;
    }

    public String currentDateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateobj = new Date();
        return df.format(dateobj);
    }
}
