package eu.zeigermann.graphql;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.servlet.GraphQLMutationProvider;
import graphql.servlet.GraphQLQueryProvider;
import graphql.servlet.GraphQLServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.Collection;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class SandboxGraphQLServlet extends GraphQLServlet {

    public void init(ServletConfig config) throws ServletException {
        // {"query": "{customerQuery {customer(id: \"1\") {id dateOfBirth lastName user {login}}}}"}
        bindQueryProvider(new GraphQLQueryProvider() {
            @Override
            public GraphQLObjectType getQuery() {
                return Schema.getGraphQLObjectType();
            }

            @Override
            public Object context() {
                return new Object();
            }
        });
        bindMutationProvider(new GraphQLMutationProvider() {
            @Override
            public Collection<GraphQLFieldDefinition> getMutations() {
                return Arrays.asList(newFieldDefinition().name("addCustomer")
                        .type(Schema.customer)
                        .argument(new GraphQLArgument("firstName", new GraphQLNonNull(GraphQLString)))
                        .argument(new GraphQLArgument("lastName", new GraphQLNonNull(GraphQLString)))
                        .dataFetcher(environment -> {
                            final String firstName = environment.getArgument("firstName");
                            final String lastName = environment.getArgument("lastName");
                            System.out.println(firstName);
                            return new Customer(firstName, lastName);
                        })
                        .build()
                );
            }
        });
    }

}
