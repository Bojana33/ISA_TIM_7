package ftn.isa.sistemapoteka.Repository;

import ftn.isa.sistemapoteka.Entity.OrderForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderFormRepository extends JpaRepository<OrderForm,Long> {
}
