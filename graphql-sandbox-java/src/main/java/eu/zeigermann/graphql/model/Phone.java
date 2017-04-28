package eu.zeigermann.graphql.model;

import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLNonNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Phone {

    @GraphQLField
    @GraphQLNonNull
    private PhoneType phoneType;

    @GraphQLField
    @GraphQLNonNull
    private String phoneNumber;

    public static Phone privatePhone(String nr) {
        return new Phone(PhoneType.PRIVATE, nr);
    }

    public static Phone businessPhone(String nr) {
        return new Phone(PhoneType.BUSINESS, nr);
    }

    public Phone(@GraphQLNonNull PhoneType phoneType, @GraphQLNonNull String phoneNumber) {
        this.phoneType = phoneType;
        this.phoneNumber = phoneNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneType=" + phoneType +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
