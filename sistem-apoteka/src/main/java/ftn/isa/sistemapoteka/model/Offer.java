package ftn.isa.sistemapoteka.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float totalPrice;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private LocalDateTime deliver_due_date;

    @ManyToOne
    @JoinColumn(name = "OrderForm_id", nullable = false, referencedColumnName = "id")
    private OrderForm orderForm;

    @ManyToOne
    @JoinColumn(name = "SupplierId", nullable = false, referencedColumnName = "id")
    private Supplier supplier;

    @Column
    private OfferStatus offerStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDeliver_due_date() {
        return deliver_due_date;
    }

    public void setDeliver_due_date(LocalDateTime deliver_due_date) {
        this.deliver_due_date = deliver_due_date;
    }

    public OrderForm getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Offer() {
    }
}
