const { buildSchema } = require('graphql');

// Construct a schema, using GraphQL schema language
const customerSchema = buildSchema(`
  type Address {
    street: String!,
    city: String!,
    email: String
  }

  enum PhoneType {
    PRIVATE, BUSINESS
  }

  type Phone {
    phoneType: PhoneType!,
    phoneNumber: String!
  }

  type Customer {
    id: Id,
    name: String,
    address: Address,
    phones: [Phone!],
    phonesWithType(type: PhoneType!): [Phone!]
  }

  type Query {
    customer(id: ID!): Customer!
    hello: String!
  }
`);

class Customer {
    constructor(id, name, address, phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    phonesWithType({ type }) {
        return this.phones.filter(phone => phone.phoneType === type);
    }
}

const privatePhone = phoneNumber => ({ phoneType: 'PRIVATE', phoneNumber });
const businessPhone = phoneNumber => ({ phoneType: 'BUSINESS', phoneNumber });

const CUSTOMERS = [
    new Customer("c1", 'Peter',
        { street: 'Hauptstrasse 3', city: 'Hamburg' },
        [ privatePhone('123'), privatePhone('456'), businessPhone('999') ]
    ),
    new Customer("c2", 'Klaus',
        { street: 'Bahnhofstraße 19', city: 'Hamburg', email: 'klaus@mail.com' },
        [ privatePhone('555') ]
    ),
    new Customer("c3", 'Nadine', { street: 'Road to Hell', city: 'Berlin', email: 'n@dine.com' })
];

// The root provides a resolver function for each API endpoint
const customerRoot = {
    hello: () => "Hello, World",
    customer:       ({ id }) => CUSTOMERS.find(p => p.id === id)
};

module.exports = {
    Customer,
    customerSchema,
    customerRoot
};
