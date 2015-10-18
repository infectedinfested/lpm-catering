package be.brickbit.lpm.catering.domain;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_STOCK_PRODUCT")
public class ProductReceiptLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "STOCK_PRODUCT_ID", nullable = false)
    public StockProduct stockProduct;

    @Column(name = "QUANTITY")
    public Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long someId) {
        id = someId;
    }

    public StockProduct getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(StockProduct someStockProduct) {
        stockProduct = someStockProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer someQuantity) {
        quantity = someQuantity;
    }
}
