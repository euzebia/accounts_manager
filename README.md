
# Software name: Accounts manager
### About the software
<p> Accounts manager is an application programming interface developed by a third party software vendor/system owner that is used in banks to help bank staff(personal banker) create customer accounts,modifying accounts based on customer preferences and managing 
other account details.</p>

>- A detailed description of the system can be downloaded from [System Design and Evidences](https://github.com/euzebia/accounts_manager/blob/main/ProjectDescriptAndEvidence.docx)

#### Installation
> #### Prerequisite
> - Ensure you have php and mysql installed,the software uses;
    <p>mysql version 5.1.3</p>
    <p>PHP Version: 7.4.28</p>
>- Once the above step is done,navigate to ....\accounts_manager\src\main\resources  and import data_manager.sql into mysql. It is the database containing oour test data.
> - Make sure you have java installed on your machine.If not yet installed,<br> you can download from [Java download site](https://www.oracle.com/)<p>
> - To confirm if java is successfully installed,use the command below;

           java -version 
          
#### Features
  >- The api interacts with individuals of three user types ie vendor,bank staff and customer. All the 3 user types are able to login from one central point with their credntials.
  >- It allows a system owner to create a new staff,and assign them to a specific branch.
  >- It enables different bank staff to enroll new customers onto the platform.
  
#### Api endpoints
>-  Login: This api interface enables different user types to log onto the platform.
  
          http://localhost:8006/api/v1/accountsManagement/login
>-  Save bank staff details: It enables the system owner to add a new bank staff
          
          http://localhost:8006/api/v1/accountsManagement/saveBankStaffDetails

>- Save customer details: Enables a bank staff to create an account for a new bank customer

          http://localhost:8006/api/v1/accountsManagement/saveBankCustomer
          
>- Get institutions: This interface allows retrieval of institutions registered in the platform

          http://localhost:8006/api/v1/accountsManagement/allInstitutions

    
#### Usage/Getting started
>- If the java set up is complete, clone the solution from [account manager repository](https://github.com/euzebia/accounts_manager)
>- Navigate to the folder, where you clone the code and navigate to the libs folder as show for my case E:\path\accounts_manager\build\libs, account_manager.jar file will be seen.
>- Navigate to the C drive and locate where the java environment is installed<br>
   C:\Program Files\Java\jdk-17.0.2\bin
>- Open the path location in command prompt run as Administrator
>- Paste java -jar "your system path where the .jar file is located"
>- Press enter for the application to start


#### Some of the Test cases to be performed are as below;
>- Add new bank staff to the system
>- New bank staff logs into the system from same central point like other users of the system
>- Create new bank customer 
>- Retrieve all institutions from the system
>- Test how rate limiting works
>- Test how circuit breakers work
 All tests performed are available on [Tests and screenshots](https://github.com/euzebia/accounts_manager/blob/main/ProjectDescriptAndEvidence.docx)
  
#### Design explanation and evidence
>- A document describing the architecture ,assumptions and proof of working functionality    can be accessed on [Design,Assumptions and Evidences](https://github.com/euzebia/accounts_manager/blob/main/ProjectDescriptAndEvidence.docx)