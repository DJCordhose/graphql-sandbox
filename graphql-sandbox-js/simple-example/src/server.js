const express = require('express');
const graphqlHTTP = require('express-graphql');

const { personSchema, personRoot } = require('./PersonModel');

// http://graphql.org/graphql-js/running-an-express-graphql-server/
const app = express();
app.use('/graphql', graphqlHTTP({
  schema:    personSchema,
  rootValue: personRoot,
  graphiql:  true,
}));
app.listen(4000);
console.log('Running a GraphQL API server at localhost:4000/graphql ');
