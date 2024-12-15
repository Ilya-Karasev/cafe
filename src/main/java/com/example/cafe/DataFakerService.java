package com.example.cafe;
import com.example.cafe.model.*;
import com.example.cafe.repository.*;
import com.example.cafe.service.CartService;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

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
    @Autowired
    private CartService cartService;

    private final Faker faker = new Faker();

    @PostConstruct
    public void initData() {
        generateUsers(50);
        generateMenuItems(30);
        generateCarts(20);
        generateOrders(20);
        generatePayments(20);

        testCartFunctionality(); // Тестируем функционал корзины
    }

    public void generateUsers(int count) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // Создайте экземпляр BCryptPasswordEncoder

        for (int i = 0; i < count; i++) {
            User user = new User();

            String username = faker.name().username(); // Генерация имени пользователя
            String password = username; // Пароль совпадает с именем пользователя

            // Шифруем пароль перед сохранением
//            String encodedPassword = passwordEncoder.encode(password);

            user.setUsername(username);
            user.setEmail(faker.internet().emailAddress());
//            user.setPassword(encodedPassword);  // Сохраняем зашифрованный пароль
            user.setAdmin(false);
            user.setPhone(faker.phoneNumber().phoneNumber());

            userRepository.save(user); // Сохраняем пользователя с зашифрованным паролем
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

    // КОРЗИНА

    public void testCartFunctionality() {
        System.out.println("=== Тестирование функционала корзины ===");

        // Получение случайной корзины
        Cart randomCart = cartRepository.findAll().get(faker.random().nextInt(0, (int) cartRepository.count() - 1));
        System.out.println("Случайная корзина:");
        printCartDetails(randomCart);

        // Добавление нового товара в корзину
        MenuItem randomMenuItem = menuItemRepository.findAll().get(faker.random().nextInt(0, (int) menuItemRepository.count() - 1));
        int quantityToAdd = faker.number().numberBetween(1, 5);
        addItemToCart(randomCart, randomMenuItem, quantityToAdd);

        System.out.println("\nКорзина после добавления товара:");
        printCartDetails(randomCart);

        // Удаление товара из корзины
//        removeItemFromCart(randomCart, randomMenuItem);
//
//        System.out.println("\nКорзина после удаления товара:");
//        printCartDetails(randomCart);

        System.out.println("\nВсе товары в корзине:");
        cartService.printAllItemsInCart(randomCart.getId());
        System.out.println("=== Завершение тестирования ===");
    }

    private void addItemToCart(Cart cart, MenuItem menuItem, int quantity) {
        // Проверяем, есть ли уже запись для этого товара в корзине
        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItem.getId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // Обновляем количество и общую стоимость, если запись уже существует
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setTotalPrice(existingCartItem.getTotalPrice()
                    .add(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity))));
        } else {
            // Создаём новую запись в таблице CartItem
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setMenuItem(menuItem);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity)));

            // Добавляем в корзину
            cart.getCartItems().add(cartItem);
        }

        // Обновляем общую стоимость корзины
        cart.setTotalPrice(cart.getTotalPrice().add(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity))));
        cart.setUpdatedAt(Timestamp.from(Instant.now()));

        // Сохраняем изменения в базе данных
        cartRepository.save(cart);
        System.out.println("Добавлен товар: " + menuItem.getName() + " (количество: " + quantity + ")");
    }

    private void removeItemFromCart(Cart cart, MenuItem menuItem) {
        // Ищем элемент в корзине
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItem.getId()))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            // Уменьшаем общую стоимость корзины
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getTotalPrice()).max(BigDecimal.ZERO));
            cart.setUpdatedAt(Timestamp.from(Instant.now()));

            // Удаляем элемент из корзины
            cart.getCartItems().remove(cartItem);

            // Сохраняем изменения
            cartRepository.save(cart);
            System.out.println("Удалён товар: " + menuItem.getName());
        } else {
            System.out.println("Товар не найден в корзине: " + menuItem.getName());
        }
    }
    private void printAllCartItems(Cart cart) {
        // Получение всех элементов корзины
        List<CartItem> cartItems = cart.getCartItems(); // Предполагается, что связь уже настроена

        if (cartItems.isEmpty()) {
            System.out.println("Корзина пуста.");
        } else {
            for (CartItem cartItem : cartItems) {
                System.out.println("Товар: " + cartItem.getMenuItem().getName() +
                        ", количество: " + cartItem.getQuantity() +
                        ", цена за единицу: " + cartItem.getMenuItem().getPrice() +
                        ", общая стоимость: " + cartItem.getTotalPrice());
            }
        }
    }

    private void printCartDetails(Cart cart) {
        System.out.println("ID: " + cart.getId());
        System.out.println("Пользователь: " + cart.getUser().getUsername());
        System.out.println("Общая стоимость: " + cart.getTotalPrice());
        System.out.println("Активная: " + cart.isActive());
        System.out.println("Создана: " + cart.getCreatedAt());
        System.out.println("Обновлена: " + cart.getUpdatedAt());
    }
}