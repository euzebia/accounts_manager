# Software name: accounts_manager
A real-world use case considered for implementation is an off-the-shelf software developed by a third party software vendor/system 
owner that is used in banks to help bank staff(personal banker) create bank accounts,modifying accounts based on customer preferences and managing 
other account details. 
# Question 1
  Brief description about the database design
  --------------------------------------------
1. Data normalization has been used to eliminate data redundancy as some properties are shared by more than one table. 
2. A primary key has been added to each table to uniquely identify each record in the table.
3. Foreign keys have been used to link tables that are related to each other.
4. Different types of users ie Type A,Type B,Type C have been categoried as bank staff,Customer and Vendor respectively.
5. The institution in which these users belong have been categorized by type ie the institution in which bank staff and Customer belong 
   has been categorized as bank, type C as Third party software vendor(vendor of the software).
6. Type C user(vendor) creates a bank staff  and assigns them a  under the institution bank.
7. A bank staff creates  customer accounts and modifies customers' info based on customer preferences.
8. Customers have been categoried according to customer category ie corporate and ordinary user.
9. Institution bank has a branch attached to it.
10. A vendor creates a bank staff who is responsible for creating customer accounts
11. Stored procedures have been to retrieve data from related tables  inorder to reduce on the traffic between the api and the db.
    Instead of sending multiple lengthy SQL statements,the api only sends sends the name and parameters of the stored procedure
	and details are retrieved once found.
12. Database views have been used to group data related to the various user types 

 # Question 2
 1. Added validation checks on payloads to ensure valid parameters are passed to the apis.
 2. Added encryption of passwords before being stored in the database to prevent malicious users from access plain user passwords 
    while they are at rest in the database.