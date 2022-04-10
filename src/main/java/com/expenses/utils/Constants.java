package com.expenses.utils;

/**
 * Created by hubinotech on 09/04/22.
 */
public class Constants {

    public static final String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

    //Endpoint
    public static final String BASE_ROOT = "/api/v1";
    public static final String CONSUMER_REGISTER_ENDPOINT = "/consumer/register";
    public static final String CONSUMER_PAYMENT_ENDPOINT = "/add/payment";
    public static final String CONSUMER_PAYMENT_LIST_ENDPOINT = "/consumer/payment/list";
    public static final String CONSUMER_PAYMENT_TYPE_ENDPOINT = "/paymentType/list";

    public static final String STATUS_CODE = "StatusCode";
    public static final String MESSAGE = "Message";
    public static final String ENROLL_SUCCESS = "User enrolled successfully";




    //Error Messages
    public static final String EMAIL_EMPTY = "Invalid email address found";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String MOBILE_EMPTY = "Invalid mobile no";
    public static final String MOBILE_EXIST = "Consumer already registered, Pls login";



    //Error codes
    public static final int ERROR_CODE_400 = 400;

}
