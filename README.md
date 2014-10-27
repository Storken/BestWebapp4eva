Bestlagerapp4eva (the best webapp4eva)
==============
<b>Project for TDA076 HT2014
<br>
Group 13</b>

<br>
<h3>See Setup.pdf for configurations in order to run the project</h3>
We've created Bestlagerapp4eva, a web based warehousing application.

In Bestlagerapp4eva, you can create your own entities (or rather, products) and manage them. This is ideal for any small (or large) company that wants to keep track of i.e how many planks they have in their warehouse. 

You're able to place orders from your warehouse and later review these orders. The application offers several features to quickly navigate among your collection of products in order to manage them with ease.

The application is meant to be used for any kind of company that wants to keep track of their stock, which may consist of any kind of product. This is why we allow users to create their own entities (products). 

Currently, the entities stored in the database are accessible to any user (also stored in the database). An order is however only accessible to the user who placed it. The idea was initially to let a company create an admin user which normal users could later on be tied to. This would represent a company and the employees responsible for managing the stock/warehouse. Another admin user would then not share the same entities as the first one. Unfortunately, there was not enough time to implement all this. The application supports normal users and acts as if it is serving just one company.

Members of the group: 
==============
Erik Tholén - 9209280537 ltd180@gmail.com

Magnus Junghard Huttu - 9307043555 magnushuttu@gmail.com

Simon Persson - 9204171293 simon.jrp@gmail.com

Usecases
==============

* Create a user
* Log in with the created user
* Add entities to the database 
* Edit entities in the database
* Add categories to the database
* Edit categories in the database
* Filter and sort entities based on their attributes
* Place an order of entities from the database
* Go through previouly placed orders
* Export a placed order as a PDF file after placing it

Approach
==============

We decided to use the PrimeFaces JSF Framework since it offers useful, feature rich and easy to use elements for an application of this kind.

Modules
==============

The application consists of the following packages:
* Controllers

The controllers are used to interpret user input and provide the Data Access Objects with the correct arguments.


* Data Access Objects
  
These classes acts as the connection between the application and the database.


* Entities
  
The models of the application, such as a User or an Order.


* Views
  
The classes that provide the web pages with the information they should show (backing beans). For example these classes are used in almost every table that lists things from the database.

UML
==============
UML for all java packages and classes can be found in the UML folder.

