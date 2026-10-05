import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StoreService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    public StoreService(ProductRepository productRepository, OrderRepository orderRepository){
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public void placeOrder(int orderId, int costumerId, Map<Integer, Integer> productQuantities){
            Order order = new Order(orderId, costumerId, LocalDateTime.now());
        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

            if (quantity > product.getStockQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + productId);
            }

            OrderItem orderItem  = new OrderItem(product, quantity);
            product.reduceStock(quantity);
            order.addItem(orderItem);
        }
        orderRepository.add(order);
    }
    public BigDecimal getTotalRevenue() {
        return orderRepository.getAll().stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public List<Product> getLowStockProducts(int threshold){
        return productRepository.getAll().stream()
                   .filter(p -> p.getStockQuantity() < threshold)
                            .collect(Collectors.toList());
    }
    public List<Map.Entry<Integer, Integer>> getBestSellingProducts(int topN) {
        Map<Integer, Integer> unitsSoldByProduct = orderRepository.getAll().stream()
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getProductId,
                        Collectors.summingInt(OrderItem::getQuantity)
                ));

        return unitsSoldByProduct.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }
    // Total revenue grouped by category
    public Map<Category, BigDecimal> getRevenueByCategory() {
        return orderRepository.getAll().stream()
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.groupingBy(
                        item -> productRepository.findById(item.getProductId())
                                .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                .getCategory(),
                        Collectors.mapping(OrderItem::getSubTotal,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
    }

    // All orders placed by one customer — thin wrapper, no real Stream logic needed
    public List<Order> getOrdersForCustomer(int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }


}
