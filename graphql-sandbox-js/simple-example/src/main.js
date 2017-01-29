const { graphql, buildSchema } = require('graphql');

// Construct a schema, using GraphQL schema language
const schema = buildSchema(`
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
    
  type Person {
    name: String,
    age: Int,
    address: Address,
    phones: [Phone!],
    phonesWithType(type: PhoneType!): [Phone!]
  }
  
  type Query {
    getPersonById(id: Int!): Person
    findPersonsLivingIn(city: String!): [Person]
  }
`);

class Person {
  constructor(id, name, age, address, phones) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.phones = phones;
  }

  phonesWithType({ type }) {
    return this.phones.filter(phone => phone.phoneType === type);
  }

}

const privatePhone = phoneNumber => ({ phoneType: 'PRIVATE', phoneNumber });
const businessPhone = phoneNumber => ({ phoneType: 'BUSINESS', phoneNumber });

const PERSONS = [
  new Person(1, 'Peter', 40,
    { street: 'Hauptstrasse 3', city: 'Hamburg' },
    [ privatePhone('123'), privatePhone('456'), businessPhone('999') ]
  ),
  new Person(2, 'Klaus', 38,
    { street: 'Bahnhofstraße 19', city: 'Hamburg', email: 'klaus@mail.com' },
    [ privatePhone('555') ]
  ),
  new Person(3, 'Nadine', 42, { street: 'Road to Hell', city: 'Berlin', email: 'n@dine.com' })
];

// The root provides a resolver function for each API endpoint
const root = {
  getPersonById:       ({ id }) => PERSONS.find(p => p.id === id),
  findPersonsLivingIn: ({ city }) => PERSONS.filter(p => p.address.city === city)
};

// Run the GraphQL query '{ hello }' and print out the response
graphql(schema, `query { 
  getPersonById(id: 1) { name, address { city, street }, phonesWithType(type: PRIVATE) { phoneNumber }  },
  findPersonsLivingIn(city: "Hamburg") {name}
}`,
  root).then((response) => {
  console.log(JSON.stringify(response, null, 2));
//  console.dir(response.data.getPersonById.address);
});

