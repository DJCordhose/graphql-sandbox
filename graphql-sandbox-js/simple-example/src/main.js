const { graphql } = require('graphql');

const { personSchema, personRoot } = require('./PersonModel');

function logResponse(response) {
  console.log(JSON.stringify(response, null, 2));
}

const query = `query {
    getPersonById(id: 1) {
      name,
      address {
        city
        street
      }
      phonesWithType(type: PRIVATE) {
        phoneNumber
      }
    }
    findPersonsLivingIn(city: "Hamburg") {
      name
    }
  }`;

// Run the GraphQL query and print out the response
graphql(personSchema, query, personRoot).then(logResponse);

