package eu.zeigermann.graphql;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by olli on 15/09/16.
 */
public class Jetty {
    public static void main(String[] args) throws Exception{
        String webappDirLocation = "web/";

        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
//        root.setDescriptor(webappDirLocation+"/WEB-INF/web.xml");
        final GraphQLSchema customerSchema = CustomerSchema.createCustomerQuerySchema();

        final SimpleGraphQLServlet graphQLServlet = new SimpleGraphQLServlet(customerSchema, null);

        root.addServlet(new ServletHolder(graphQLServlet), "/*");
        root.setResourceBase(webappDirLocation);
        root.setParentLoaderPriority(true);

        server.setHandler(root);
        server.start();
        server.join();
    }}
