package eu.zeigermann.graphql;

import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLNonNull;

import java.time.LocalDate;

/**
 * Created by olli on 15/09/16.
 */
public class Customer {
    static int idCnt = 0;

    @GraphQLField
    @GraphQLNonNull
    public String id;
    @GraphQLField
    public String firstName;
    @GraphQLField
    public String lastName;
//    @GraphQLField
    public LocalDate dateOfBirth;

    @GraphQLField
    public User user;

    public Customer(String id, String firstName, String lastName, LocalDate dateOfBirth, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }

    public Customer(String firstName, String lastName) {
        this.id = "id" + ++idCnt;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
