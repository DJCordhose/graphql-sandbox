package eu.zeigermann.graphql.model;

import graphql.annotations.GraphQLDescription;
import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLName;
import graphql.annotations.GraphQLNonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by olli on 15/09/16.
 */
public class Customer {
    @GraphQLField
    @GraphQLNonNull
    public String id;

    public String getId() {
        return id;
    }

    @GraphQLField
    @GraphQLDescription("The first name of this customer")
    private String firstName;
    @GraphQLDescription("The last name of this customer")
    @GraphQLField
    private String lastName;

    @GraphQLField
    @GraphQLNonNull
    @GraphQLDescription("The Address of this customer")
    private Address address;

    @GraphQLField
    @GraphQLNonNull
    @GraphQLDescription("Returns all known Phone numbers of this Customer")
    private List<Phone> phones = new LinkedList<>();

    @GraphQLField
    @GraphQLDescription("Returns all phone numbers of the specified type")
    public List<Phone> phonesWithType(@GraphQLName("type") PhoneType type) {
        // DOES NOT WORK CURRENTLY DUE TO THIS BUG: https://github.com/graphql-java/graphql-java-annotations/issues/77
        return phones.stream().filter(phone -> phone.getPhoneType() == type).collect(Collectors.toList());
    }

    public Customer(String id, String firstName, String lastName, Address address, List<Phone> phones) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phones = phones;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
