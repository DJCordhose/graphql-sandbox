// Tutorial from: http://graphql.org/graphql-js

var express = require('express');
var graphqlHTTP = require('express-graphql');
var { buildSchema } = require('graphql');
var jwt = require('express-jwt');

// use to make request to internal core server
// var fetch = require('node-fetch');

// Construct a schema, using GraphQL schema language
var schema = buildSchema(`
type RandomDie {
  numSides: Int!
  rollOnce: Int
  roll(numRolls: Int!): [Int]
}

type Query {
hello: String
rollDice(numDice: Int!, numSides: Int): [Int]
    getDie(numSides: Int): RandomDie
  getMessage: String
}

type Mutation {
  setMessage(message: String): String
}

`);

class RandomDie {
    constructor(numSides) {
        this.numSides = numSides;
    }

    rollOnce() {
        return 1 + Math.floor(Math.random() * this.numSides);
    }

    roll({numRolls}) {
        var output = [];
        for (var i = 0; i < numRolls; i++) {
            output.push(this.rollOnce());
        }
        return output;
    }
}

var fakeDatabase = {};


// The root provides a resolver function for each API endpoint
var root = {
    hello: () => 'Hello world!',
    rollDice: ({numDice, numSides}) => {
        const output = [];
        for (let i = 0; i < numDice; i++) {
            output.push(1 + Math.floor(Math.random() * (numSides || 6)));
        }
        return output;
    },
    getDie: ({numSides}) => new RandomDie(numSides || 6),
    setMessage: ({message}) => {
        fakeDatabase.message = message;
        return message;
    },
    getMessage: () =>fakeDatabase.message
};

function loggingMiddleware(req, res, next) {
    console.log('ip:', req.ip);
    next();
}
var app = express();
app.use(loggingMiddleware);
app.get('/protected',
    jwt({secret: 'shhhhhhared-secret'}),
    function(req, res, next) {
        if (!req.user.admin) return res.sendStatus(401);
        res.sendStatus(200);
    });
app.post('/secure-graphql',
    jwt({secret: 'shhhhhhared-secret'}),
    function(req, res, next) {
        if (!req.user.admin) {
            return res.sendStatus(401);
        } else {
            next();
        }
    });
app.use('/secure-graphql', graphqlHTTP({
    schema: schema,
    rootValue: root,
    graphiql: false
}));
app.use('/graphql', graphqlHTTP({
    schema: schema,
    rootValue: root,
    graphiql: true
}));
app.use(express.static('public'));
app.listen(4000);
console.log('Running a GraphQL API server at localhost:4000/graphql');