import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private final int productId;
    private final BigDecimal priceAtPurchase;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.productId = product.getProductId();
        this.quantity = quantity;
        this.priceAtPurchase = product.getPrice();
    }

    public int getProductId(){
        return productId;
    }
    public BigDecimal getPriceAtPurchase(){
        return priceAtPurchase;
    }
    public int getQuantity(){
        return quantity;
    }
    public BigDecimal getSubTotal(){
        BigDecimal quantityBigDecimal = BigDecimal.valueOf(this.quantity);
        return priceAtPurchase.multiply(quantityBigDecimal);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return productId == orderItem.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId=" + productId +
                ", priceAtPurchase=" + priceAtPurchase +
                ", quantity=" + quantity +
                '}';
    }
}
