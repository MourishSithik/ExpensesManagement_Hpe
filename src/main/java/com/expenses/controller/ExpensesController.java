package com.expenses.controller;

import com.expenses.model.dao.ConsumerPaymentInfoRequest;
import com.expenses.model.dao.ConsumerPaymentListRequest;
import com.expenses.model.dao.ConsumerRegisterRequest;
import com.expenses.model.dao.ErrorResponse;
import com.expenses.service.ExpenseService;
import com.expenses.utils.Constants;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hubinotech on 09/04/22.
 */
@CrossOrigin(origins = "*")
@RequestMapping(path = Constants.BASE_ROOT)
@RestController
public class ExpensesController extends ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpensesController.class);

    private ErrorResponse errorResponse = null;

    @ApiOperation(value = "Health Check")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> check() throws Exception {
        return ResponseEntity.ok("Renderer");
    }

    @ApiOperation(value = "To get the payment type info list Ex:[Gpay,Phonepe,ByCash,Parents]")
    @RequestMapping(value = Constants.CONSUMER_PAYMENT_TYPE_ENDPOINT, method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentTypeInfo() throws Exception {
        return ResponseEntity.ok(getPaymentTypeInfoAvailable());
    }

    @ApiOperation(value = "To register a consumer")
    @RequestMapping(value = Constants.CONSUMER_REGISTER_ENDPOINT, method = RequestMethod.POST)
    public ResponseEntity<?> consumerRegister(@RequestBody ConsumerRegisterRequest registerRequest) throws Exception {
        logger.info("Service started with user register:" + registerRequest.toString());
        if (isNullOrEmpty(registerRequest.getEmail())) {
            Pattern pattern = Pattern.compile(Constants.regex);
            Matcher matcher = pattern.matcher(registerRequest.getEmail());
            if (matcher.matches()) {
                if (isNullOrEmpty(registerRequest.getMobile())) {
                    if (!isMobileNoExist(registerRequest.getMobile())) {
                        enrollConsumer(registerRequest);
                        JSONObject obj = new JSONObject();
                        obj.put(Constants.STATUS_CODE, 200);
                        obj.put(Constants.MESSAGE, Constants.ENROLL_SUCCESS);
                        return new ResponseEntity<Object>(obj.toMap(), new HttpHeaders(), HttpStatus.OK);
                    } else {
                        logger.error("Service ended with already enrolled : " + registerRequest.getEmail());
                        return getErrorResponseEntity(Constants.MOBILE_EXIST, Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    logger.error("Service ended with invalid mobile : " + registerRequest.getEmail());
                    return getErrorResponseEntity(Constants.MOBILE_EMPTY, Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
                }
            } else {
                logger.error("Service ended with invalid email format: " + registerRequest.getEmail());
                return getErrorResponseEntity(Constants.INVALID_EMAIL_FORMAT, Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.error("Service ended with email empty: " + registerRequest.getEmail());
            return getErrorResponseEntity(Constants.EMAIL_EMPTY, Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "To insert consumer payment info")
    @RequestMapping(value = Constants.CONSUMER_PAYMENT_ENDPOINT, method = RequestMethod.POST)
    public ResponseEntity<?> consumerPayment(@RequestBody ConsumerPaymentInfoRequest paymentInfoRequest) throws Exception {
        logger.info("Service started with user register:" + paymentInfoRequest.toString());
        try {
            if (Long.parseLong(paymentInfoRequest.getAmount()) > 0) {
                if(consumerIdExist(paymentInfoRequest.getConsumerId())) {
                    if(paymentGatewayIdExist(paymentInfoRequest.getPaymentGatewayId())) {
                        insertPaymentInfo(paymentInfoRequest);
                        JSONObject obj = new JSONObject();
                        obj.put(Constants.STATUS_CODE, 200);
                        obj.put(Constants.MESSAGE, "Payment details added");
                        return new ResponseEntity<Object>(obj.toMap(), new HttpHeaders(), HttpStatus.OK);
                    } else {
                        return getErrorResponseEntity("Invalid payment gateway Id", Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return getErrorResponseEntity("Invalid consumer Id", Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
                }
            } else {
                return getErrorResponseEntity("Not valid amount", Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
            }
        } catch(NumberFormatException e) {
            return getErrorResponseEntity("Not valid amount", Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return getErrorResponseEntity("Something went wrong, pls try again later", Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "To get consumer payment list by corresponding date")
    @RequestMapping(value = Constants.CONSUMER_PAYMENT_LIST_ENDPOINT, method = RequestMethod.POST)
    public ResponseEntity<?> paymentList(@RequestBody ConsumerPaymentListRequest consumerPaymentListRequest) throws Exception {
        logger.info("Service started with user register:" + consumerPaymentListRequest.toString());
        if(consumerIdExist(consumerPaymentListRequest.getConsumerId())) {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date stDate = sdformat.parse(consumerPaymentListRequest.getStartDate());
            Date endDate = sdformat.parse(consumerPaymentListRequest.getEndDate());
            if(stDate.before(endDate) ) {
                return ResponseEntity.ok(getConsumerPaymentList(consumerPaymentListRequest).toMap());
            } else {
                return getErrorResponseEntity("Invalid start and end date ", Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
            }

        } else {
            return getErrorResponseEntity("Invalid consumer Id", Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getErrorResponseEntity(String errorMessage, int errorCode, HttpStatus httpStatus) {
        errorResponse = new ErrorResponse();
        errorResponse.setErroMessage(errorMessage);
        errorResponse.setErroCode(errorCode);
        return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), httpStatus);
    }

    // True returns not empty or not null
    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty()) return true;
        return false;
    }
}
