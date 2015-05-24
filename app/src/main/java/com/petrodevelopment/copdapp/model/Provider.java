package com.petrodevelopment.copdapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 10/05/2015.
 * Added Coordinates - Tom - 18-05-2015
 */
public class Provider extends Model {

    public static List<Provider> getDummy() {
        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider(
                "Andrey",
                "Petrov",
                "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpt1/v/t1.0-1/c11.0.50.50/p50x50/10364063_10152202370808445_8669827953058751583_n.jpg?oh=d33beced428f52734e819c32a7ca3e90&oe=55CD22D0&__gda__=1439647123_9c3b43b6842836cf8530294101f1a748",
                "+1 111111111",
                "andrey@email.com",
                "12 Okanagan Drive, North York, ON, M1M 1M1",
                "43.6430879,-79.4186298",
                new ClinicianType("Surgeon")
                ));

        providers.add(new Provider(
                "Ivan",
                "Petrov",
                "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpt1/v/t1.0-1/c11.0.50.50/p50x50/10364063_10152202370808445_8669827953058751583_n.jpg?oh=d33beced428f52734e819c32a7ca3e90&oe=55CD22D0&__gda__=1439647123_9c3b43b6842836cf8530294101f1a748",
                "+1 111111111",
                "ivan@email.com",
                "13 Okanagan Drive, North York, ON, M1M 1M1",
                "23.6430879,-72.4186298",
                new ClinicianType("Surgeon")
        ));

        providers.add(new Provider(
                "Aleksandar",
                "Petrov",
                "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpt1/v/t1.0-1/c11.0.50.50/p50x50/10364063_10152202370808445_8669827953058751583_n.jpg?oh=d33beced428f52734e819c32a7ca3e90&oe=55CD22D0&__gda__=1439647123_9c3b43b6842836cf8530294101f1a748",
                "+1 2222222",
                "aleksandar@email.com",
                "12 Okanagan Drive. NEW YORK, meh",
                "61.6430879,23.6430879",
                new ClinicianType("Surgeon")
        ));

        providers.add(new Provider(
                "Tom",
                "White",
                "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpt1/v/t1.0-1/c11.0.50.50/p50x50/10364063_10152202370808445_8669827953058751583_n.jpg?oh=d33beced428f52734e819c32a7ca3e90&oe=55CD22D0&__gda__=1439647123_9c3b43b6842836cf8530294101f1a748",
                "+1 2222222",
                "aleksandar@email.com",
                "33 Robinson Ste. Hamilton, ON, N7V 1H7",
                "43.2500976,-79.8749977",
                new ClinicianType("Respirologist")
        ));
        return providers;
    }

    public Provider() {
    }

    public Provider(String firstName, String lastName, String photoUrl, String phoneNumber, String email, String address, String Coordinates, ClinicianType clinicianType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.Coordinates = Coordinates;
        this.clinicianType = clinicianType;
    }

    public String firstName;
    public String lastName;
    public String photoUrl;
    public String phoneNumber;
    public String email;
    public String Coordinates;
    public String address;

    //a relationship to the type of provider
    ClinicianType clinicianType;
}
