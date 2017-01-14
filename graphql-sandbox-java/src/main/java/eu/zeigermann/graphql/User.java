package eu.zeigermann.graphql;

import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLNonNull;

import java.time.LocalDateTime;

public class User {
    @GraphQLField
    @GraphQLNonNull
    public String id;
    @GraphQLField
    public String login;
//    @GraphQLField
    public LocalDateTime lastLogin;

    public User(String id, String login, LocalDateTime lastLogin) {
        this.id = id;
        this.login = login;
        this.lastLogin = lastLogin;
    }

}
