package com.printerapp.interfaces.rest.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.printerapp.application.commands.momo.MomoPaymentRequest;
import com.printerapp.application.commands.momo.MomoStatusRequest;
import com.printerapp.application.results.momo.MomoPaymentResponse;
import com.printerapp.application.services.MomoService;
import com.printerapp.interfaces.rest.common.response.AppResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final MomoService momoService;

    @PostMapping("/create")
    public ResponseEntity<AppResponse> createPayment(@RequestBody MomoPaymentRequest request) throws Throwable {
        MomoPaymentResponse res = momoService.createPaymentRequest(request.getAmount());
        return AppResponse.initRespose(HttpStatus.CREATED, true, "Create QR successfully", res);
    }

    @PostMapping("/check-status")
    public ResponseEntity<AppResponse> checkStatus(@RequestBody MomoStatusRequest request) throws Exception {
        momoService.startTransactionStatusCheck(request);

        return AppResponse.initRespose(HttpStatus.OK, true, "Check Status Successfully",
                momoService.checkTransactionStatus(request.getOrderId()));
    }

    @PostMapping("/callback")
    public ResponseEntity<AppResponse> handleCallback(@RequestBody Map<String, String> requestBody) {
        System.out.println("callback:");
        System.out.println(requestBody);

        // Return a 204 No Content response
        return AppResponse.initRespose(HttpStatus.NO_CONTENT, true, "Callback Successfully", requestBody);
    }

}