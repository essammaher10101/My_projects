import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();
        StoreService storeService = new StoreService(productRepository, orderRepository);

        // Products
        Product laptop = new Product(1, "Laptop", new BigDecimal("999.99"), Category.ELECTRONICS);
        Product mouse  = new Product(2, "Wireless Mouse", new BigDecimal("25.50"), Category.ELECTRONICS);
        Product tshirt = new Product(3, "T-Shirt", new BigDecimal("15.00"), Category.CLOTHING);
        Product novel  = new Product(4, "Novel - Sci-Fi", new BigDecimal("12.99"), Category.BOOKS);
        Product coffee = new Product(5, "Coffee Beans 1kg", new BigDecimal("18.75"), Category.FOOD);

        laptop.addStock(10);
        mouse.addStock(50);
        tshirt.addStock(100);
        novel.addStock(30);
        coffee.addStock(20);

        productRepository.add(laptop);
        productRepository.add(mouse);
        productRepository.add(tshirt);
        productRepository.add(novel);
        productRepository.add(coffee);

        // Orders
        Map<Integer, Integer> order1Items = new HashMap<>();
        order1Items.put(1, 1); // 1 laptop
        order1Items.put(2, 2); // 2 mice
        storeService.placeOrder(1001, 501, order1Items);

        Map<Integer, Integer> order2Items = new HashMap<>();
        order2Items.put(3, 3); // 3 t-shirts
        order2Items.put(4, 1); // 1 novel
        storeService.placeOrder(1002, 502, order2Items);

        Map<Integer, Integer> order3Items = new HashMap<>();
        order3Items.put(2, 5); // 5 mice
        order3Items.put(5, 2); // 2 coffee
        storeService.placeOrder(1003, 501, order3Items); // same customer as order 1

        // --- Demonstrate every service method ---

        System.out.println("--- Total revenue ---");
        System.out.println(storeService.getTotalRevenue());

        System.out.println("\n--- Low stock products (threshold = 10) ---");
        storeService.getLowStockProducts(10).forEach(p -> System.out.println(p.getName() + ": " + p.getStockQuantity()));

        System.out.println("\n--- Best selling products (top 3) ---");
        storeService.getBestSellingProducts(3).forEach(e -> System.out.println("Product " + e.getKey() + ": " + e.getValue() + " units"));

        System.out.println("\n--- Revenue by category ---");
        storeService.getRevenueByCategory().forEach((cat, revenue) -> System.out.println(cat + ": " + revenue));

        System.out.println("\n--- Orders for customer 501 ---");
        storeService.getOrdersForCustomer(501).forEach(o -> System.out.println("Order " + o.getOrderId() + " - total: " + o.getTotal()));
    }
}