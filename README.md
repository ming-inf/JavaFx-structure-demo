<h4> Prerequisites </h4>

* Java SDK 9 Early Access (or later)
* IntelliJ IDEA 2017.1 (or later with support for Java 9 modules)

<h4> Technologies used </h4>

* JavaFX with Java 9
* SQLite-native (as backend db, native libs included in sqlite-jdbc resources folder)
* Xerial SQLite JDBC Driver (v. 3.20.0)

<h4> About </h4>

This demo application is built to show you how you can structure your desktop JavaFX app similar to the [Robotlegs](http://www.robotlegs.org/) approach.
The structure in this app is highly inspired by Yennick Trevels [blog post](http://yennicktrevels.com/blog/2013/10/15/javafx-structuring-your-application-overview/) 
with using direct calls with interfaces over events because that makes it easier to follow the flow of your application. However this example does
not use any dependency injection framework such as guice since I wanted to keep it as pure Java 9 as possible for instructional purposes.
This app is also built with the new module approach in Java 9. The UI is built with pure Java code instead of using fxml since I prefer
it that way giving me more control over my code. Also the UI is built to be resizable meaning the UI-components adapt their size when 
the main application windows is being resized.

<h4> Backend </h4>

SQLite with ([Xerial SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc)) is used as a backend with pure JDBC-calls for instructional purpose.
The Xerial source is included as a module named *sqlite-jdbc* in the application.

<h4> ToDo </h4>

* Add a logging framework module
* Add jOOQ as a module (and switch from pure JDBC-calls)

<h4> Output </h4>

The application creates and populates a JavaFx-structure-demo.db file under the user home directory, System.getProperty("user.home").

<h4> Screenshot of application </h4>

<img src="https://github.com/jbilander/JavaFx-structure-demo/blob/master/app_screenshot.png">

<h4> Usage </h4>

* compile with Java 9 SDK
* run with:
> java -m com.example/com.example.Main

