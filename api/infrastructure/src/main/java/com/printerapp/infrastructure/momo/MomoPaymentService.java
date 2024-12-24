package com.printerapp.infrastructure.momo;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.printerapp.application.commands.momo.MomoStatusRequest;
import com.printerapp.application.exceptions.NotFoundException;
import com.printerapp.application.results.auth.UserContext;
import com.printerapp.application.results.momo.MomoCheckStatusResponse;
import com.printerapp.application.results.momo.MomoPaymentResponse;
import com.printerapp.application.services.AuthService;
import com.printerapp.application.services.MomoService;
import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.domain.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MomoPaymentService implements MomoService {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final UserRepository customerRepository;
    private final AuthService authService;

    @NonFinal
    @Value("${momo.accessKey}")
    protected String ACCESS_KEY;

    @NonFinal
    @Value("${momo.secretKey}")
    protected String SECRET_KEY;

    @NonFinal
    @Value("${momo.partnerCode}")
    protected String PARTNER_CODE;

    @NonFinal
    @Value("${momo.endpoint}")
    protected String END_POINT;

    @NonFinal
    @Value("${momo.queryEndpoint}")
    protected String QUERY_ENDPOINT;

    public String generateHmacSHA256(Map<String, String> params, String secretKey) throws Exception {
        // Step 1: Construct the raw data string
        StringBuilder rawData = new StringBuilder();
        params.forEach((key, value) -> rawData.append(key).append("=").append(value).append("&"));

        // Remove the trailing "&"
        if (rawData.length() > 0) {
            rawData.setLength(rawData.length() - 1);
        }

        // Step 2: Generate HmacSHA256 hash
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);

        byte[] hashBytes = mac.doFinal(rawData.toString().getBytes(StandardCharsets.UTF_8));

        // Step 3: Convert hash bytes to hexadecimal string
        StringBuilder hashHex = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hashHex.append('0');
            }
            hashHex.append(hex);
        }

        return hashHex.toString();
    }

    public MomoPaymentResponse createPaymentRequest(String amount) throws Exception {

        String orderId = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        log.info(orderId);
        String requestType = "payWithMethod";
        String orderInfo = "Payment for " + orderId;

        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("accessKey", ACCESS_KEY);
        requestBody.put("amount", amount);
        requestBody.put("extraData", "eyJza3VzIjoiIn0=");
        requestBody.put("ipnUrl", "https://931a-14-169-112-143.ngrok-free.app/payment/callback");
        requestBody.put("orderId", orderId);
        requestBody.put("orderInfo", orderInfo);
        requestBody.put("partnerCode", PARTNER_CODE);
        requestBody.put("redirectUrl",
                "https://0fe6-183-81-126-19.ngrok-free.app/afterlogin");
        requestBody.put("requestId", orderId);
        requestBody.put("requestType", requestType);
        // requestBody.put("extraData",
        // Base64.getEncoder().encodeToString("{\"key\":\"123\"}".getBytes()));

        // Generate raw data string for signature
        String rawData = requestBody.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        log.info(rawData);
        String signature = generateHmacSHA256(requestBody, "at67qH6mk8w5Y1nAyMoYKMWACiEi2bsa");
        log.info(signature);
        requestBody.put("signature", signature);
        requestBody.put("lang", "en");

        // Send POST request to MOMO endpoint
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(END_POINT, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            log.info(jsonResponse.toString());

            MomoPaymentResponse momoResponse = objectMapper.treeToValue(jsonResponse, MomoPaymentResponse.class);
            // Handle fields
            String payUrl = jsonResponse.has("payUrl") ? jsonResponse.get("payUrl").asText() : null;
            String message = jsonResponse.has("message") ? jsonResponse.get("message").asText() : "No message provided";

            if (payUrl != null) {
                return momoResponse;
            } else {
                throw new RuntimeException("Pay URL is missing. Message: " + message);
            }
        } else {
            throw new RuntimeException("Failed to connect to MOMO API. HTTP Status: " + response.getStatusCode());
        }
    }

    public MomoCheckStatusResponse checkTransactionStatus(String orderId) throws Exception {

        // String rawSignature = String.format(
        // "accessKey=%s&orderId=%s&partnerCode=%s&requestId=%s",
        // ACCESS_KEY, orderId, PARTNER_CODE, requestId
        // );

        // String signature = computeHmacSha256(rawSignature, SECRET_KEY);

        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("accessKey", ACCESS_KEY);
        requestBody.put("orderId", orderId);
        requestBody.put("partnerCode", PARTNER_CODE);
        requestBody.put("requestId", orderId);

        String rawData = requestBody.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        log.info(rawData);
        String signature = generateHmacSHA256(requestBody, "at67qH6mk8w5Y1nAyMoYKMWACiEi2bsa");
        log.info(signature);

        requestBody.put("signature", signature);
        requestBody.put("lang", "vi");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(QUERY_ENDPOINT, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            log.info(jsonResponse.toString());

            MomoCheckStatusResponse momoResponse = objectMapper.treeToValue(jsonResponse,
                    MomoCheckStatusResponse.class);

            // Handle fields
            String resultCode = jsonResponse.has("resultCode") ? jsonResponse.get("resultCode").asText() : null;
            String message = jsonResponse.has("message") ? jsonResponse.get("message").asText() : "No message provided";

            if (resultCode != null) {
                return momoResponse;
            } else {
                throw new RuntimeException("resultCode is missing. Message: " + message);
            }
        } else {
            throw new RuntimeException("Failed to connect to MOMO API. HTTP Status: " + response.getStatusCode());
        }

    }

    public void stopTransactionStatusCheck() {
        scheduler.shutdown();
    }

    public void startTransactionStatusCheck(MomoStatusRequest request) {
        // java.util.List<String> types = request.getTypes();
        // java.util.List<String> quantities = request.getQuantities();
        java.util.List<MomoStatusRequest.Item> items = request.getItems();

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }

        String orderId = request.getOrderId();
        java.util.List<PaperQuantity> papers = new java.util.ArrayList<PaperQuantity>();

        for (MomoStatusRequest.Item item : items) {
            if (item.getType() == null || item.getType().isEmpty()) {
                throw new IllegalArgumentException("Item type cannot be null or empty");
            }
            if (item.getQuantity() == null || item.getQuantity().isEmpty()) {
                throw new IllegalArgumentException("Item quantity cannot be null or empty");
            }
            PaperType paperType = PaperType.valueOf(item.getType());
            Integer quantity = Integer.valueOf(item.getQuantity());

            PaperQuantity paperQuantity = new PaperQuantity(paperType, quantity);
            papers.add(paperQuantity);
        }
        log.info(papers.get(0).getType().toString());
        log.info(papers.get(0).getQuantity().toString());
        log.info(papers.get(1).getType().toString());
        log.info(papers.get(1).getQuantity().toString());
        log.info(papers.get(2).getType().toString());
        log.info(papers.get(2).getQuantity().toString());

        // for(int i = 0; i < types.size(); i++){
        // PaperType paperType = PaperType.valueOf(types.get(i));
        // Integer.valueOf(quantities.get(i));
        // PaperQuantity paperQuantity = new PaperQuantity(paperType,
        // Integer.valueOf(quantities.get(i)));

        // paper.add(paperQuantity);
        // }
        // PaperType paperType = PaperType.valueOf(type);
        // log.info(paperType.toString());
        // Integer.valueOf(quantity);
        // log.info(Integer.valueOf(quantity).toString())
        // PaperQuantity paper = new PaperQuantity(paperType,
        // Integer.valueOf(quantity));

        UserContext userContext = authService.getUserContext();
        UserId userId = new UserId(userContext.getId());

        if (scheduler.isShutdown() || scheduler.isTerminated() || scheduler == null) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
        }
        Runnable task = () -> {

            try {
                MomoCheckStatusResponse response = checkTransactionStatus(orderId);

                if (response.getResultCode() == 0) {
                    // Increase paper in here
                    for (PaperQuantity paper : papers) {
                        User customer = customerRepository.findById(userId)
                                .orElseThrow(() -> new NotFoundException("User not found"));
                        customer.getCustomer().addPaper(paper);
                    }
                    stopTransactionStatusCheck();
                }

                if (response.getResultCode() != 0 && response.getResultCode() != 1000
                        && response.getResultCode() != 7000) {
                    stopTransactionStatusCheck();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }
}