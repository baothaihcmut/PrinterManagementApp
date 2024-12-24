package com.printerapp.application.results.momo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true) // Add this annotation
public class MomoCheckStatusResponse {
    private String partnerCode;
    private int resultCode;
    private String message;
    private String orderId;
    private String requestId;
    private String signature;
    private String extraData;
    private String payType;
    private String transId;
    private String amount;
    private String promotionInfo;
    private String responseTime;
}
