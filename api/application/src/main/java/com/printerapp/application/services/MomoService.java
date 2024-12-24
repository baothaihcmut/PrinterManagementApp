package com.printerapp.application.services;

import java.util.Map;

import com.printerapp.application.commands.momo.MomoStatusRequest;
import com.printerapp.application.results.momo.MomoCheckStatusResponse;
import com.printerapp.application.results.momo.MomoPaymentResponse;

public interface MomoService {
    public String generateHmacSHA256(Map<String, String> params, String secretKey) throws Exception;

    public MomoPaymentResponse createPaymentRequest(String amount) throws Exception;

    public MomoCheckStatusResponse checkTransactionStatus(String orderId) throws Exception;

    public void stopTransactionStatusCheck();

    public void startTransactionStatusCheck(MomoStatusRequest request);

}