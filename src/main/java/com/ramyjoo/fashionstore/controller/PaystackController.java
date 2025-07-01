package com.ramyjoo.fashionstore.controller;

import com.ramyjoo.fashionstore.model.PaymentRequest;
import com.ramyjoo.fashionstore.service.PaystackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/paystack")
@RequiredArgsConstructor
public class PaystackController {

    private final PaystackService paystackService;

    @PostMapping("/initialize")
    public ResponseEntity<?> initializePayment(@RequestBody PaymentRequest request){
        return ResponseEntity.ok(paystackService.initializeTransaction(request));
    }

    @GetMapping("/verify/{reference}")
    public ResponseEntity<?> verifyPayment(@PathVariable String reference) {
        return ResponseEntity.ok(paystackService.verifyTransaction(reference));
    }
}
