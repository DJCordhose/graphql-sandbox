package eu.zeigermann.graphql;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        GraphQLObjectType queryType = Schema.getGraphQLObjectType();
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();
        final String query = "{customer(id: \"1\") {id dateOfBirth lastName user {login}}}";
        final ExecutionResult executionResult = new GraphQL(schema).execute(query);
        final List<GraphQLError> errors = executionResult.getErrors();
        for (GraphQLError error : errors) {
            System.err.println(error);
        }
        if (errors.size() == 0) {
            Map<String, Object> result = (Map<String, Object>) executionResult.getData();

            System.out.println(result);
        }
    }
}