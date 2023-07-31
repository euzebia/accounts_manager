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

 # Question 2