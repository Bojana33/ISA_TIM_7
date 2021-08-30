package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private float totalPrice;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    private LocalDateTime deliver_due_date;

    @ManyToOne
    @JoinColumn(name = "OrderForm_id", nullable = false, referencedColumnName = "id")
    private OrderForm orderForm;

    @ManyToOne
    @JoinColumn(name = "SupplierId", nullable = false, referencedColumnName = "id")
    private Supplier supplier;

    @Column
    private OfferStatus offerStatus = OfferStatus.ON_HOLD;

}
