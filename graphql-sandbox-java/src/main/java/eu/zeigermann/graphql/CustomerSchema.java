package eu.zeigermann.graphql;

import eu.zeigermann.graphql.model.Customer;
import eu.zeigermann.graphql.model.CustomerService;
import graphql.annotations.GraphQLAnnotations;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import static graphql.Scalars.GraphQLID;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLList.list;
import static graphql.schema.GraphQLObjectType.newObject;

public class CustomerSchema {

    private final static CustomerService customerService = new CustomerService();

    private static GraphQLObjectType buildCustomerType() {
        try {
            return GraphQLAnnotations.object(Customer.class);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException("Could not create customer schema: " + e, e);
        }
    }

    private static GraphQLObjectType buildCustomerQueryType(GraphQLObjectType customerType) {

        GraphQLObjectType queryType = newObject()
                .name("customerQuery")
                .description("The Customer Endpoint")
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .description("The classic hello world")
                        .name("hello")
                        .dataFetcher(environment -> "Hello, World")
                        .build())
                .field(newFieldDefinition()
                        .type(customerType)
                        .description("Find a single customer by it's unique id")
                        .name("customer")
                        .argument(new GraphQLArgument("id", new GraphQLNonNull(GraphQLID)))
                        .dataFetcher(environment -> {
                            final String id = environment.getArgument("id");
                            return customerService.findCustomerById(id);
                        })
                        .build())
                .field(newFieldDefinition()
                        .type(list(customerType))
                        .name("allCustomers")
                        .description("Returns all customers")
                        .dataFetcher(env -> customerService.findAll())
                        .build())
                .build();

        return queryType;
    }

    private static GraphQLObjectType createMutationType(GraphQLObjectType customerType) {
        GraphQLObjectType mutationType = newObject()
                .name("customerMutation")
                .description("Modifications for Customers")
                .field(newFieldDefinition().name("addCustomer")
                        .description("Add a new Customer")
                        .type(customerType)
                        .argument(new GraphQLArgument("firstName", GraphQLString))
                        .argument(new GraphQLArgument("lastName", GraphQLString))
                        .argument(new GraphQLArgument("email", new GraphQLNonNull(GraphQLString)))
                        .dataFetcher(environment -> {
                            final String firstName = environment.getArgument("firstName");
                            final String lastName = environment.getArgument("lastName");
                            final String email = environment.getArgument("email");

                            final Customer newCustomer = customerService.newCustomer(firstName, lastName, email);
                            return newCustomer;
                        })
                        .build())
                .build();
        return mutationType;
    }


    public static GraphQLSchema createCustomerQuerySchema() {
        final GraphQLObjectType customerType = buildCustomerType();

        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(buildCustomerQueryType(customerType))
                .mutation(createMutationType(customerType))
                .build();
        return schema;
    }

}
