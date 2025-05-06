package com.toms.toms.repository;

import com.toms.toms.model.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {
    /* TODO: custom query methods */
}
