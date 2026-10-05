import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();


    @Override
    public void add(Product product) {
        boolean exist = products.stream()
                .anyMatch(p -> p.getProductId() == product.getProductId());
        if(exist) {
            throw new IllegalArgumentException("Product already exists");
        }
        products.add(product);
    }
    @Override
    public Optional<Product> findById(int id) {
       return products.stream()
               .filter(p -> p.getProductId() == id)
               .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(this.products);
    }
}
