package eu.zeigermann.graphql.model;

import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLNonNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Address {

    @GraphQLField
    private String street;
    @GraphQLField
    private String city;

    @GraphQLField
    @GraphQLNonNull
    private String email;

    public Address(String street, String city, String email) {
        this.street = street;
        this.city = city;
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
