import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final int productId;
    private final String name;
    private int stockQuantity;
    private final Category category;
    private BigDecimal price;

    public Product(int id, String name, BigDecimal price, Category category){
        this.productId = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void addStock(int stockQuantity) {
        if(stockQuantity < 0)
            throw new IllegalArgumentException("invalid value");

        this.stockQuantity = stockQuantity + this.stockQuantity;
    }
    public void reduceStock(int stockQuantity){
        if(stockQuantity > this.stockQuantity)
            throw new IllegalArgumentException("invalid value");
        else if (stockQuantity < 0)
            throw new IllegalArgumentException("invalid value");

        this.stockQuantity =  this.stockQuantity - stockQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
