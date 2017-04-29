package eu.zeigermann.graphql;

import graphql.servlet.SimpleGraphQLServlet;

public class CustomerGraphQLServlet extends SimpleGraphQLServlet {
    public CustomerGraphQLServlet() {
        super(CustomerSchema.createCustomerQuerySchema(), null);
    }
}
