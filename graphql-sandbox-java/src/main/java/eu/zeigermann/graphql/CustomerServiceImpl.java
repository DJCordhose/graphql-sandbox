package eu.zeigermann.graphql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class CustomerServiceImpl {
    public Customer findCustomerById(String id) {
        User user = new User("1", "olli", LocalDateTime.now());
        Customer customer = new Customer(id, "Oliver", "Zeigermann", LocalDate.of(1970, Month.DECEMBER, 22), user);
        return customer;
    }
}
