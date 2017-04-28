package eu.zeigermann.graphql.model;

import java.util.*;

public class CustomerService {
    private static int customerCounter = 0;

    private static String newId() {
        return "c" + (++customerCounter);
    }

    private final Map<String, Customer> database = new Hashtable<>();


    public CustomerService() {

        final Address addressPeter = new Address("Haupstrasse 3", "Hamburg", "some@where.net");
        final Address addressSusi = new Address("Bahnhofstraße 19", "Hamburg", "susi@mail.com");
        final Address addressNadine = new Address("Lindenallee 49", "Berlin", "n@dine.com");

        final Customer peter = new Customer(newId(), "Peter", "Mueller", addressPeter,
                Arrays.asList(Phone.businessPhone("123"), Phone.privatePhone("456"), Phone.businessPhone("999")));
        final Customer susi = new Customer(newId(), "Susi", "Hansen", addressSusi,
                Arrays.asList(Phone.privatePhone("555")));
        final Customer nadine = new Customer(newId(), "Nadine", "Knudsen", addressNadine,
                new LinkedList<>());


        database.put(peter.getId(), peter);
        database.put(susi.getId(), susi);
        database.put(nadine.getId(), nadine);
    }

    public Customer findCustomerById(String id) {
        Customer c = database.get(id);
        System.out.println("C " + c);
        return c;
    }

    public Customer newCustomer(String firstName, String lastName, String email) {
        Address newAddress = new Address(null, null, email);
        Customer newCustomer = new Customer(newId(), firstName, lastName, newAddress, new LinkedList<>());
        database.put(newCustomer.getId(), newCustomer);
        return newCustomer;
    }

    public Collection<Customer> findAll() {
        return Collections.unmodifiableCollection(database.values());

    }
}
