package com.example.cafe;
import com.example.cafe.model.*;
import com.example.cafe.repository.*;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class DataFakerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private CartRepository cartRepository;


    private final Faker faker = new Faker();

    @PostConstruct
    public void initData() {
        generateUsers(50);
        generateMenuItems(30);
        generateCarts(20);
        generateOrders(20);
        generatePayments(20);
    }

    public void generateUsers(int count) {
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setAdmin(false);
            user.setPhone(faker.phoneNumber().phoneNumber());
            userRepository.save(user);
        }
    }

//    public void generateMenuItems(int count) {
//        for (int i = 0; i < count; i++) {
//            MenuItem menuItem = new MenuItem();
//            menuItem.setName(faker.food().dish());
//            menuItem.setDescription(faker.lorem().sentence());
//            menuItem.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 50)));
//            menuItem.setAvailable(faker.bool().bool());
//            menuItem.setImg(faker.internet().image());
//            // menuItem.setImg("https://w7.pngwing.com/pngs/785/836/png-transparent-bee-drawing-bee-honey-bee-food-photography.png");
//            menuItemRepository.save(menuItem);
//        }
//    }
public void generateMenuItems(int count) {
    for (int i = 0; i < count; i++) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(faker.food().dish());
        menuItem.setDescription(faker.lorem().sentence());
        menuItem.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 50)));
        menuItem.setAvailable(faker.bool().bool());

        // Генерация случайного URL картинки
        String randomImageUrl = "https://picsum.photos/200/300?random=" + faker.number().randomNumber();
        menuItem.setImg(randomImageUrl);

        menuItemRepository.save(menuItem);
    }
}
//    public void generateCartItems(int count) {
//        for (int i = 0; i < count; i++) {
//            CartItem cartItem = new CartItem();
//
//            // Привязываем случайную корзину к элементу корзины
//            Cart randomCart = cartRepository.findAll().get(faker.random().nextInt(0, (int) cartRepository.count() - 1));
//            cartItem.setCart(randomCart);
//
//            // Привязываем случайный пункт меню к элементу корзины
//            MenuItem randomMenuItem = menuItemRepository.findAll().get(faker.random().nextInt(0, (int) menuItemRepository.count() - 1));
//            cartItem.setMenuItem(randomMenuItem);
//
//            // Устанавливаем случайное количество
//            int quantity = faker.number().numberBetween(1, 10);
//            cartItem.setQuantity(quantity);
//
//            // Устанавливаем общую стоимость элемента корзины (цена меню * количество)
//            BigDecimal totalPrice = randomMenuItem.getPrice().multiply(BigDecimal.valueOf(quantity));
//            cartItem.setTotalPrice(totalPrice);
//
//            cartItemRepository.save(cartItem);
//        }
//    }


    public void generateCarts(int count) {
        for (int i = 0; i < count; i++) {
            Cart cart = new Cart();

            // Привязываем случайного пользователя к корзине
            User randomUser = userRepository.findAll().get(faker.random().nextInt(0, (int) userRepository.count() - 1));
            cart.setUser(randomUser);

            // Генерируем случайную общую стоимость для корзины
            cart.setTotalPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));

            // Устанавливаем временные метки
            Timestamp now = Timestamp.from(Instant.now());
            cart.setCreatedAt(now);
            cart.setUpdatedAt(now);

            // Устанавливаем флаг активности корзины
            cart.setActive(faker.bool().bool());

            cartRepository.save(cart);
        }
    }



    public void generateOrders(int count) {
        for (int i = 0; i < count; i++) {
            Order order = new Order();
            order.setOrderNumber(faker.number().digits(10));
            order.setStatus(faker.options().option("Pending", "Completed", "Cancelled"));
            order.setCreatedAt(Timestamp.from(Instant.now()));


            User randomUser = userRepository.findAll().get(faker.random().nextInt(0, (int) userRepository.count() - 1));
            order.setUser(randomUser);

            Cart randomCart = cartRepository.findAll().get(faker.random().nextInt(0, (int) cartRepository.count() - 1));
            order.setCart(randomCart);

            //Payment randomPayment = paymentRepository.findAll().get(faker.random().nextInt(0, (int) paymentRepository.count() -1);

            orderRepository.save(order);
        }
    }

    public void generatePayments(int count) {
        for (int i = 0; i < count; i++) {
            Payment payment = new Payment();
            payment.setPaymentMethod(faker.options().option("Credit Card", "Cash", "PayPal"));
            payment.setTotalAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 100)));
            payment.setPaidAt(Timestamp.from(Instant.now()));

            // Связываем платеж с заказом
            Order randomOrder = orderRepository.findAll().get(faker.random().nextInt(0, (int) orderRepository.count() - 1));
            payment.setOrder(randomOrder);

            paymentRepository.save(payment);
        }
    }
}

