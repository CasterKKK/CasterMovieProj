package com.ra.casterpay.logic;

import com.ra.casterpay.logic.common.Result;

public interface PayLogic {
    double theaterGainRate = 0.90;

    Result<String> payOrder(String userId, String theaterId, String orderId, Integer money);

    Result<String> retrieveOrder(String orderId, double discount);

    Result<String> recharge(String userId, Integer money);

    Result<String> giveMoneyToTheater(String theaterId, Integer money);
}
