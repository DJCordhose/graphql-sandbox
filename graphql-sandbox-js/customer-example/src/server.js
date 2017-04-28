const express = require('express');
const graphqlHTTP = require('express-graphql');

const {customerSchema, customerRoot} = require('./customer-model');

// http://graphql.org/graphql-js/running-an-express-graphql-server/
const app = express();
app.use('/graphql', graphqlHTTP({
    schema: customerSchema,
    rootValue: customerRoot,
    graphiql: true,
}));
app.use(express.static('public'));
app.listen(4000);

console.log('Running a GraphQL API server at localhost:4000/graphql ');
