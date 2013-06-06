# SODA Android SDK

## Overview

This is the Android SDK for the Socrata Open Data API (SODA). Please refer to the developer site (http://dev.socrata.com/) for a deeper discussion of the underlying protocol.

Currently this SDK only contains support for the Consumer aspects of the SODA API but may be extended later to include the publisher capabilities as well.

## Distribution

You can add the SODA Client to your project as a Gradle / Maven dependency or simply copying the client sources and adding the dependent libraries.

### Gradle / Maven dependency

    'com.socrata:android-soda-client:1.0-SNAPSHOT'

### SODAClient requires the following dependencies that may have to be manually included if you don't use gradle or maven

- [android-async-http-1.4.2-66-g4b6eb97](soda-android-sdk/libs/android-async-http-1.4.2-66-g4b6eb97.jar?raw=true)
- play-services-3.1.36.aar
- support-v4-13.0.0

### Build from sources

The Android SDK uses the Android Build system based in Gradle.

1. First you need to add *local.properties* providing your Android SDK location

    sdk.dir=/path/to/android-sdk

2. List available tasks

    ./gradlew tasks

3. Run integration and unit tests on a connected USB device or emulator

    ./gradlew connectedCheck --info

4. Install the provided sample app on a connected USB device or emulator. You need to get your own Maps API Key from Google to display GMaps [here](https://developers.google.com/maps/documentation/android/start#getting_the_google_maps_android_api_v2)

    ./gradlew installDebug


## SODA

## Consumer

### Initialization

The SODAConsumer is the main interface to communicate with the SODA consumer API. A SODA consumer is created and initialized with the domain and token provided by Socrata.
If you are just getting started with the SODA API please take a look at the [Getting Started Guide](http://dev.socrata.com/consumers/getting-started)

```java
Consumer consumer = new Consumer("soda.demo.socrata.com", "YOUR_TOKEN");
```
Instances of a consumer can be held in memory for further requests.

### Getting Data

All requests to the SODA API are non blocking and performed asynchronously in a background thread. A callback style interface ensures that responses are delivered in the callback back to the caller in the Main thread where it is is safe to update the user interface.

You can lookup records by IDs or query with a SQL style language as defined in (http://dev.socrata.com/docs/queries).

#### Get by ID

Get by ID returns a single result 

```java
Consumer consumer = new Consumer("soda.demo.socrata.com", "YOUR_TOKEN");
consumer.getObject("earthquakes", earthquakeId, Earthquake.class, new Callback<Earthquake>() {
    @Override
    public void onResults(Response<Earthquake> response) {
        Earthquake earthquake = response.getEntity();
        //do somethings with earthquake
    }
});
```

#### Querying

The Consumer accepts both String based queries and Query objects that once executed against the SODA Consumer API will return a subset of records constrained by the criteria specified in the query.

##### Query By String

Consumer accepts raw SoQL strings as input to the com.socrata.android.client.Consumer#getObjects method. You can use any standard SoQL query passed to this method as defined in (http://dev.socrata.com/docs/queries).

```java
Consumer consumer = new Consumer("soda.demo.socrata.com", "YOUR_TOKEN");
consumer.getObjects("earthquakes", "select * where magnitude > 2.0", Earthquake.class, new Callback<List<Earthquake>>() {
    @Override
    public void onResults(Response<List<Earthquake>> response) {
        List<Earthquake> earthquakes = response.getEntity();
        //do somethings with earthquake
    }
});
```

##### Query Building

You can build queries based on simple or complex criteria.
The SODA Android Clients provides both a simple query interface where you can append expressions to a query and static methods that can be all statically imported at once for each one of the available expressions.

Query Example to get Earthquakes with magnitude > 2.0

```java
import static com.socrata.android.soql.clauses.Expression.*;

Consumer consumer = new Consumer("soda.demo.socrata.com", "YOUR_TOKEN");
Query query = new Query("earthquakes", Earthquake.class);
query.addWhere(gt("magnitude", "2.0"));
consumer.getObjects(query, new Callback<List<Earthquake>>() {
    @Override
    public void onResults(Response<List<Earthquake>> response) {
        List<Earthquake> earthquakes = response.getEntity();
        //do somethings with earthquakes
    }
});
```

###### Geo Queries

The SODA android SDK supports geo queries by including a query.addWhere(withinBox("location", box(north, east, south, west))) clause that takes a dataset location property and a geo bounding box with the NE, SW coordinates.

```java
import static com.socrata.android.soql.clauses.Expression.*;
import static com.socrata.android.soql.Query.*;

...

Consumer consumer = new Consumer("soda.demo.socrata.com", "YOUR_TOKEN");
Query query = new Query("earthquakes", Earthquake.class);
query.addWhere(withinBox("location", box(north, east, south, west)));
consumer.getObjects(query, new Callback<List<Earthquake>>() {
    @Override
    public void onResults(Response<List<Earthquake>> response) {
        List<Earthquake> earthquakes = response.getEntity();
        //do somethings with earthquakes
    }
});
```

## User Interface

The SODA SDK provides several user interface components that help speed the development of Android apps that access the SODA API.

### SodaListActivity & SodaListFragment

Activity and Fragment commodity classes that abstracts out much of the work involved in wiring up data from the SODA API to a list view.
Both include pagination out of the box.

Creating a SodaListActivity & SodaListFragment is much like creating a typical Activity or Fragment.

1. Create a subclass of SodaListActivity or SodaListFragment and customize it as needed.
2. Set the consumer property to indicate which Consumer object is used to query the data.
3. Override the getQuery method to create a custom Query to fetch the data required.
4. Implement com.socrata.android.ui.list.BindableView to provide a custom way to render the list results

When the view loads the Query is automatically executed and the results are loaded into the table.

```java

/**
 * A simple list activity displaying earthquakes results from the Soda API.
 * Showcases the use of the SodaListActivity component
 */
public class ListViewExampleActivity extends SodaListActivity<EarthquakeView, Earthquake> {

    private Consumer consumer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        consumer = new Consumer(getString(R.string.soda_domain));
        //new Consumer(getString(R.string.soda_domain), getString(R.string.soda_token));
        super.onCreate(savedInstanceState);
    }

    @Override
    public Consumer getConsumer() {
        return consumer;
    }

    @Override
    public Query getQuery() {
        Query query = new Query("earthquakes", Earthquake.class);
        query.addWhere(gt("magnitude", "2.0"));
        query.addOrder(order("magnitude", OrderDirection.DESC));
        return query;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }
}

```

### SodaMapFragment

The SodaMapFragment is simply a MapSupportFragment. The SodaMapFragment can be customized by overriding methods in its implementations

1. Subclass the SodaMapFragment and customize as needed.
2. Set the consumer property to indicate which Consumer object is used to query the data.
3. Override the getQuery(Geobox) method to create a custom Query to fetch the data required. The geo box for the map will be passed to this method if you wish to filter the results based on the current map view.
4. Override the toMarkerOptions(Object) method to provide a marker for the current fetched entity.
