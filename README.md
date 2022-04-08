Prodigi-Java-SDK
================

A java library for communicating with the [Prodigi API](https://www.prodigi.com/print-api/docs/).

v4.0 of the API is supported. If you want an older version, see the [Pwinty Java SDK](https://github.com/OddPrints/pwinty-java-sdk) ;)


Releases
--------

* v4.0 - Initial release

Usage
-----

Create and Order object using a builder pattern and submit it:

``` java

// Choose Environment.LIVE for real orders
Prodigi prodigi = new Prodigi(Prodigi.Environment.SANDBOX, "YOUR-API-KEY");

// Create Recipient
Recipient recipient = new Recipient("Bob", new Address("line1", "line2", "90210", CountryCode.GB, "Bristol"));

// Create Order
Order order = new Order.Builder(Order.ShippingMethod.Standard, recipient)
        .addImage("https://www.oddprints.com/images/header-dogcat.jpg", "GLOBAL-PHO-4x6", 1)
        .build();

// Submit to API
OrderResponse response = prodigi.createOrder(order);

```


Download
--------

See [jitpack](https://jitpack.io/#OddPrints/prodigi-java-sdk/)

Basically, add the jitpack repoository:

    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }

and the dependency:

    dependencies {
        implementation 'com.github.OddPrints:prodigi-java-sdk:v4.0.2'
    }


Contribute/Extend
-----------------

Everything is built using standard [gradle](https://gradle.org/docs/current/userguide/tutorial_java_projects.html).


Running the tests
-----------------

This library has a comprehensive test suite which is helpful as the Prodigi API evolves. To run the tests yourself, just set en environment variable for your api key:

```
export PRODIGI_API_KEY_SANDBOX="YOUR_API_KEY"
./gradlew test
```
