package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.PaymentHistory;
import com.app.motelappproject4.models.PaymentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PaymentHistoriesController {

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    // Lấy tất cả lịch sử thanh toán
    @GetMapping("/api/paymentHistories")
    public ResponseEntity<List<PaymentHistory>> getAllPaymentHistories() {
        List<PaymentHistory> paymentHistories = (List<PaymentHistory>) paymentHistoryRepository.findAll();
        return ResponseEntity.ok(paymentHistories);
    }

    @GetMapping("/api/paymentHistories/owner/{id}")
    public ResponseEntity<List<PaymentHistory>> getPaymentHistoriesByCreatedBy_Id(@PathVariable int id) {
        List<PaymentHistory> paymentHistories = (List<PaymentHistory>) paymentHistoryRepository.getPaymentHistoriesByCreatedBy_Id(id);
        return ResponseEntity.ok(paymentHistories);
    }

    @GetMapping("/api/paymentHistories/motel/{id}")
    public ResponseEntity<List<PaymentHistory>> getPaymentHistoriesByMotel_Id(@PathVariable int id) {
        List<PaymentHistory> paymentHistories = (List<PaymentHistory>) paymentHistoryRepository.getPaymentHistoriesByMotel_Id(id);
        return ResponseEntity.ok(paymentHistories);
    }

    // Lấy lịch sử thanh toán theo ID
    @GetMapping("/api/paymentHistories/{id}")
    public ResponseEntity<PaymentHistory> getPaymentHistoryById(@PathVariable int id) {
        Optional<PaymentHistory> paymentHistory = paymentHistoryRepository.findById(id);
        if (paymentHistory.isPresent()) {
            return ResponseEntity.ok(paymentHistory.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Tạo mới lịch sử thanh toán
    @PostMapping("/api/paymentHistories")
    public ResponseEntity<PaymentHistory> createPaymentHistory(@RequestBody PaymentHistory paymentHistory) {
        PaymentHistory savedPaymentHistory = paymentHistoryRepository.save(paymentHistory);
        return new ResponseEntity<>(savedPaymentHistory, HttpStatus.CREATED);
    }

    // Cập nhật lịch sử thanh toán
    @PutMapping("/api/paymentHistories/{id}")
    public ResponseEntity<PaymentHistory> updatePaymentHistory(@PathVariable int id, @RequestBody PaymentHistory updatedPaymentHistory) {
        return paymentHistoryRepository.findById(id).map(paymentHistory -> {
            // Cập nhật các trường thông tin ở đây
            paymentHistory.setElectricMoney(updatedPaymentHistory.getElectricMoney());
            paymentHistory.setName(updatedPaymentHistory.getName());
            paymentHistory.setPaymentMethod(updatedPaymentHistory.getPaymentMethod());
            paymentHistory.setWaterMoney(updatedPaymentHistory.getWaterMoney());
            paymentHistory.setWifiMoney(updatedPaymentHistory.getWifiMoney());
            paymentHistory.setState(updatedPaymentHistory.getState());
            PaymentHistory savedPaymentHistory = paymentHistoryRepository.save(paymentHistory);
            return ResponseEntity.ok(savedPaymentHistory);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Xóa lịch sử thanh toán
    @DeleteMapping("/api/paymentHistories/{id}")
    public ResponseEntity<Void> deletePaymentHistory(@PathVariable int id) {
        if (paymentHistoryRepository.existsById(id)) {
            paymentHistoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
