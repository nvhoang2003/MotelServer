// PaymentHistoryRepository.java
package com.app.motelappproject4.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentHistoryRepository extends CrudRepository<PaymentHistory, Integer> {
    List<PaymentHistory> getPaymentHistoriesByCreatedBy_Id(int CreatedBy_Id);
}