package gift.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishes")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(name = "quantity")
    private Long quantity;

    public Wish() {

    }

    public Wish(Long id, Product product, Member member, Long quantity) {
        this.id = id;
        this.product = product;
        this.member = member;
        this.quantity = quantity;
    }

    public Wish(Product product, Member member, Long quantity) {
        this(null, product, member, quantity);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }

    public Long getQuantity() {
        return quantity;
    }
}