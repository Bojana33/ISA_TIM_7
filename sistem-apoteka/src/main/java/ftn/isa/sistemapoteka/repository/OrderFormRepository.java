package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.OrderForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderFormRepository extends JpaRepository<OrderForm, Long> {
    List<OrderForm> findAll();
}
