# Software name: Accounts manager
### About the software
<p> Accounts manager is an application programming interface developed by a third party software vendor/system owner that is used in banks to help bank staff(personal banker) create customer accounts,modifying accounts based on customer preferences and managing 
other account details.</p>

#### Installation
> #### Prerequisite
> - Make sure you have java installed on your machine.If not yet installed,<br> you can download from [Java download site](https://www.oracle.com/)<p>
> - If the java set up is complete, clone the solution from [account manager repository](https://github.com/euzebia/accounts_manager)

#### Usage/Getting started

 
# Question 1
  Brief description about the database design
  --------------------------------------------
1. Data normalization has been used to eliminate data redundancy as some properties are shared by more than one table. 
2. A primary key has been added to each table to uniquely identify each record in the table.
3. Stored procedures have been to retrieve data from related tables  inorder to reduce on the traffic between the api and the db.
    Instead of sending multiple lengthy SQL statements,the api only sends sends the name and parameters of the stored procedure
	and details are retrieved once found.
4. User_name and email are unique as they part of details used to uniquely identify a user in the system.
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
13. User_name and email are unique as they part of details used to uniquely identify a user in the system.
 



 # Question 2
 1. Added validation checks on payloads to ensure valid parameters are passed to the apis.
 2. Added encryption of passwords before being stored in the database to prevent malicious users from access plain user passwords 
    while they are at rest in the database.
 3. Basic authentication has been added to the api to prevent unauthorized access.(username: api_user, password:t5p4krGSJyil2hWB)
 4. Added exception handling to capture any exceptions that could be encountered during execution and a custom error is being returned to the user
    instead of the actual exception.
 5. Added file logging to capture exceptions on execution, this will enable easy trouble shooting incase of system malfunctioning,
    will be trouble shooting will check on the logs to determine exact cause of failure.
 6. Added api versioning to ensure stability,reliability and to	avoid disrupting system users.
 
 
  Bank Customer
  user name: pkikulwa
  user password: H@ll809

 
 
