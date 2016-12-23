package eu.zeigermann.graphql;

import graphql.annotations.GraphQLAnnotations;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;

import java.util.Arrays;

import static graphql.Scalars.*;
import static graphql.Scalars.GraphQLID;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class Schema {
    private static CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
    public static GraphQLObjectType user = newObject()
            .name("User")
            .fields(
                    Arrays.asList(
                            newFieldDefinition()
                                    .name("id")
                                    .type(new GraphQLNonNull(GraphQLID))
                                    .build(),
                            newFieldDefinition()
                                    .name("login")
                                    .type(GraphQLString)
                                    .build(),
                            newFieldDefinition()
                                    .name("lastLogin")
                                    .type(GraphQLString)
                                    .build()))
            .build();

    /*
    String input = "
    type Customer {
      id: Id,
      firstName: String,
      user: User
    }
    ";
     */

//    public static GraphQLObjectType customer = new Parser().parse(input);
//    public static GraphQLObjectType customer = new Parser().parse(input).getType('customer');
//
//    public static GraphQLObjectType customer = newObject()
//            .name("Customer")
//            .fields(
//                    Arrays.asList(
//                            newFieldDefinition()
//                                    .name("id")
//                                    .type(new GraphQLNonNull(GraphQLID))
//                                    .build(),
//                            newFieldDefinition()
//                                    .name("firstName")
//                                    .type(GraphQLString)
//                                    .build(),
//                            newFieldDefinition()
//                                    .name("lastName")
//                                    .type(GraphQLString)
//                                    .build(),
//                            newFieldDefinition()
//                                    .name("dateOfBirth")
//                                    .type(GraphQLString)
//                                    .build(),
//                            newFieldDefinition()
//                                    .name("user")
//                                    .type(user)
//                                    .build()))
//            .build();

    public static GraphQLObjectType customer;
    static {
        try {
            customer = GraphQLAnnotations.object(Customer.class);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public static GraphQLObjectType getGraphQLObjectType() {

        GraphQLObjectType queryType = newObject()
                .name("customerQuery")
                .field(newFieldDefinition()
                        .type(customer)
                        .name("customer")
                        .argument(new GraphQLArgument("id", new GraphQLNonNull(GraphQLID)))
                        .dataFetcher(environment -> {
                            final String id = environment.getArgument("id");
                            return customerServiceImpl.findCustomerById(id);
                        })
                        .build())
                .build();
        return queryType;
    }

}
