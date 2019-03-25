# QapitalDemo
Android demo app using the tiny Qapital api.

The app is achitectured using MVP with a repository handling the database and api calls with RoomDB and Retrofit respectively.

## Points of interest
This app leverages the Data Binding library in several ways.
* layout/fragment_goal_detail.xml uses include to separate the header
* Library.kt contains some useful BindingAdapters to set html and load images
* Both views use bindings to show/hide loading views

GoalsApplication instantiates the repository and provides it through an extension function on the Fragment class.

The files containing presenters also contains the view models and View interfaces.

RxJava2 is leveraged throughout the app to observe the data in the database. When an api call completes with new data, the updated items are pushed out to observers.
