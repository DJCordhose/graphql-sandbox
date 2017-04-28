const { buildSchema } = require('graphql');

// Construct a schema, using GraphQL schema language
const customerSchema = buildSchema(`
  type Address {
    street: String,
    city: String,
    email: String!
  }

  enum PhoneType {
    PRIVATE, BUSINESS
  }

  type Phone {
    phoneType: PhoneType!,
    phoneNumber: String!
  }

  type Customer {
    id: ID,
    firstName: String,
    lastName: String,
    address: Address,
    phones: [Phone!],
    phonesWithType(type: PhoneType!): [Phone!]
  }

  type Query {
    hello: String!,
    customer(id: ID!): Customer!,
    allCustomers: [Customer]
  }
  
  type Mutation {
    addCustomer(firstName: String, lastName: String, email: String!): Customer!
  }
`);

class Customer {
    constructor(id, firstName, lastName, address, phones) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phones = phones;
    }

    phonesWithType({ type }) {
        return this.phones.filter(phone => phone.phoneType === type);
    }
}

const privatePhone = phoneNumber => ({ phoneType: 'PRIVATE', phoneNumber });
const businessPhone = phoneNumber => ({ phoneType: 'BUSINESS', phoneNumber });

let id = 0;
const newId = () => `c${++id}`;

const CUSTOMERS = [
    new Customer(newId(), 'Peter', 'Mueller',
        {street: 'Hauptstrasse 3', city: 'Hamburg', email: 'some@where.net'},
        [ privatePhone('123'), privatePhone('456'), businessPhone('999') ]
    ),
    new Customer(newId(), 'Susi', 'Hansen',
        {street: 'Bahnhofstraße 19', city: 'Hamburg', email: 'susi@mail.com'},
        [ privatePhone('555') ]
    ),
    new Customer(newId(), 'Nadine', 'Knudsen', {street: 'Lindenallee 49', city: 'Berlin', email: 'n@dine.com'})
];

// The root provides a resolver function for each Query and Mutation
const customerRoot = {
    // Queries
    hello: () => "Hello, World",
    customer: ({id}) => CUSTOMERS.find(p => p.id === id),
    allCustomers: () => CUSTOMERS,

    // Mutations
    addCustomer: ({firstName, lastName, email}) => {
        const newCustomer = new Customer(newId(), firstName, lastName, {email}, []);
        CUSTOMERS.push(newCustomer);
        return newCustomer;
    }

};

module.exports = {
    Customer,
    customerSchema,
    customerRoot
};
