// PaymentHistoryController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.PaymentHistory;
import com.app.motelappproject4.models.PaymentHistoryRepository;
import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.UsersRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class PaymentHistoriesController {
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/api/paymentHistories")
    public List<PaymentHistory> index() {
        return (List<PaymentHistory>) paymentHistoryRepository.findAll();
    }

    @GetMapping("/api/paymentHistories/{id}")
    public Optional<PaymentHistory> find(@PathVariable int id) {
        return paymentHistoryRepository.findById(id);
    }

    @PostMapping("/api/paymentHistories")
    public PaymentHistory create(@RequestBody PaymentHistory paymentHistory) {
        return paymentHistoryRepository.save(paymentHistory);
    }

    @PutMapping("/api/paymentHistories/{id}")
    public int update(@PathVariable int id, @RequestBody PaymentHistory updatedPaymentHistory) {
        Optional<PaymentHistory> optionalPaymentHistory = paymentHistoryRepository.findById(id);
        if (optionalPaymentHistory.isPresent()) {
            PaymentHistory existingPaymentHistory = optionalPaymentHistory.get();
            // Update fields here
            // For example: existingPaymentHistory.setName(updatedPaymentHistory.getName());
            paymentHistoryRepository.save(existingPaymentHistory);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/paymentHistories/{id}")
    public int delete(@PathVariable int id) {
        if (paymentHistoryRepository.existsById(id)) {
            paymentHistoryRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }

    @GetMapping("/api/seed/paymentHistories")
    public List<PaymentHistory> seedPaymentHistoriesData() {
        Faker faker = new Faker();
        List<User> users = (List<User>) usersRepository.findAll();
        List<PaymentHistory> list = new ArrayList<PaymentHistory>();
        for (int i = 0; i < 10; i++) {
            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setName(faker.lorem().word());
            paymentHistory.setElectricMoney(faker.number().numberBetween(1000, 5000));
            paymentHistory.setWaterMoney(faker.number().numberBetween(1000, 5000));
            paymentHistory.setWifiMoney(faker.number().numberBetween(1000, 5000));
            paymentHistory.setPaymentMethod(faker.lorem().word());
            if (!users.isEmpty()) {
                paymentHistory.setCreatedBy(users.get(faker.number().numberBetween(0, users.size())));
                paymentHistory.setTenant(users.get(faker.number().numberBetween(0, users.size())));
            }
            paymentHistoryRepository.save(paymentHistory);
            list.add(paymentHistory);
        }
        paymentHistoryRepository.saveAll(list);
        return list;
    }
}
