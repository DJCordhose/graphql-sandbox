const { expect } = require('chai');
const { graphql } = require('graphql');
const { personRoot, personSchema } = require('../src/PersonModel');

function logResponse(response) {
  console.log(JSON.stringify(response, null, 2));

  return response;
}

function extractResponseData(response) {
  expect(response.data).to.be.ok;
  expect(response.errors).to.be.not.ok;
  return response.data;
}

function extractResponseErrors(response) {
  expect(response.data).to.be.not.ok;
  expect(response.errors).to.be.ok;
  return response.errors;
}

function runSuccessQuery(query) {
  return graphql(personSchema, query, personRoot)
    .then(logResponse)
    .then(extractResponseData)
    ;
}

function runFailingQuery(query) {
  return graphql(personSchema, query, personRoot)
    .then(logResponse)
    .then(extractResponseErrors)
    ;
}

// This is not really a test suite for the PersonModel,
// but for the GraphQL features
describe('PersonModel', function() {
  it('should return all Persons living in a specified city', function() {
    const query = `{ findPersonsLivingIn(city: "Hamburg") { name } }`;

    return runSuccessQuery(query).then(response => {
      expect(response).to.eql({
        "findPersonsLivingIn": [
          { "name": "Peter" },
          { "name": "Klaus" }
        ]
      });
    })
  });

  it('should return a person by id', function() {
    const query = `{ getPersonById(id: 3) { name } }`;
    return runSuccessQuery(query).then(response => {
      expect(response).to.eql({
        getPersonById: { name: 'Nadine' }
      })
    });
  });

  it('should return an error when mandatory data is not returned', function() {
    // By it's schema definition getPersonById *always* returns an object
    // If the query does *not* return a person it should fail (and not return
    // null or undefined)
    const query = `{ getPersonById(id: 666) { name } }`;
    return runFailingQuery(query).then(errors => {
      expect(errors[ 0 ]).to.have.property("message", "Cannot return null for non-nullable field Query.getPersonById.");
    });
  });

});
