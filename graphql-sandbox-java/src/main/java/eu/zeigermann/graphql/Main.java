package eu.zeigermann.graphql;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        final GraphQLSchema schema = CustomerSchema.createCustomerQuerySchema();

        final String query = "{customer(id: \"c2\") {id lastName address dateOfBirth {street city} phones {phoneNumber phoneType} }}";
        final ExecutionResult executionResult =
                GraphQL.newGraphQL(schema)
                        .build()
                        .execute(query);

//                new GraphQL(schema).execute(query);
        final List<GraphQLError> errors = executionResult.getErrors();
        for (GraphQLError error : errors) {
            System.err.println(error);
        }
        if (errors.size() == 0) {
            Map<String, Object> result = executionResult.getData();

            System.out.println(result);
        }
    }
}
