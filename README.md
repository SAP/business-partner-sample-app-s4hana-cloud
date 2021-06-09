# Sample Extension Application for Managing Business Partners in SAP S/4HANA Cloud

## Introduction

You can extend the functionality of your SAP S/4HANA Cloud system to manage your business partners. To do that, you can use the SAP S/4HANA Cloud Business Partner Sample App, deploy it in a subaccount in SAP Business Technology Platform (SAP BTP) but have it fully integrated in your SAP S/4HANA Cloud system. Using this application, you can:
* View a list of all your business partners
* Add new business partners

![](S4HANA-Cloud-Business-Partner-Sample-App.png)

## Prerequisites

There are several components and authorizations that you and/or your team members need.

**Tools**

* [JDK 7 or later](http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html)
* [Maven 3.0.x](http://maven.apache.org/docs/3.0.5/release-notes.html)
* [Cloud Foundry Command Line Interface (cf CLI)](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/4ef907afb1254e8286882a2bdef0edf4.html?q=cf%20CLI)
* [git](https://git-scm.com/download/)

**On SAP BTP side:**

* You have either an [enterprise](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/171511cc425c4e079d0684936486eee6.html) or a [trial](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/046f127f2a614438b616ccfc575fdb16.html) global account in SAP BTP.
* You have an S-user or P-user (if you are using an enterprise global account), and a trial user (if you are using a trial account). See [User and Member Management](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/cc1c676b43904066abb2a4838cbd0c37.html?q=user).
* You are an administrator of the global account where you want to register your SAP S/4HANA Cloud system.
* You have enabled the Cloud Foundry capabilities for your subaccount in SAP BTP.
* Check which feature set you are using. See [Cloud Management Tools — Feature Set Overview](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/caf4e4e23aef4666ad8f125af393dfb2.html?q=feature%20set).

**On SAP S/4HANA Cloud side:**

* You have a dedicated SAP S/4HANA Cloud tenant with an Identity Authentication tenant configured. You need to use the same Identity Authentication tenant for your subaccount in SAP BTP.
* To configure the integration on the SAP S/4HANA Cloud system side, you need a business user with the roles: 

   * SAP_BR_ADMINISTRATOR that contains the Business Catalog SAP_CORE_BC_COM necessary to access and administer the SAP S/4HANA Cloud tenant.
   * SAP_BR_BPC_EXPERT to be able to maintain the event topics in the SAP S/4HANA Cloud tenant.

You can check whether your user has the required roles via the Maintain Business Users application in your SAP S/4HANA Cloud tenant. See [Maintain Business Roles](https://help.sap.com/viewer/55a7cb346519450cb9e6d21c1ecd6ec1/2102.500/en-US/8980ad05330b4585ab96a8e09cef4688.html).

## Process

### 1. Connect the SAP S/4HANA Cloud system you want to extend with the corresponding global account in SAP BTP

To do that, have to register your SAP S/4HANA Cloud system in your global account in SAP BTP. During this process, an integration token is created and then used by the SAP S/4HANA Cloud system tenant administrator to configure the integration on the SAP S/4HANA Cloud system side.
See [Register an SAP S/4HANA Cloud System in a Global Account in SAP BTP](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/28171b629f3549af8c1d66d7c8de5e18.html). 

### 2. Make the SAP S/4HANA Cloud system accessible in the subaccount in SAP BTP in which you want to build your extension application.

To do so, you configure the entitlements and assign the corresponding quota and the `api-access` service plan to the subaccount where the extension applications will reside for the system you registered in the previous step.
See [Configure the Entitlements for the Subaccount in SAP BTP](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/65ad330d11ac49a196948aa8db6470fb.html).

### 3. Clone the SAP S/4HANA Cloud Business Partner Sample Application from GitHub

1. To clone the GitHub repository, use this command:

      ```
      git clone https://github.wdf.sap.corp/i323283/s4hana-cloud-ext-business-partner
      ```

2. In the root of the project, there's a file called `s4-0008.json` which contains the name of the SAP S/4HANA Cloud system which you'll connect to. You need to replace the values of these parameters:

* **"systemName"**: this is the system you registered in step 1. 
* **"communicationArrangementName"**: the name you specify here will be the same name of the communication arrangement that is going to be automatically created in your SAP S/4HANA Cloud tenant. 

3. In the root of the project, there's a file called `vars.yml`. You need to replace the values of these parameters:

* **ID**: this is your user in SAP BTP. It's either an S-user, a P-user, or a trial user. 
* **LANDSCAPE_APPS_DOMAIN**: this is the API endpoint of your subaccount in SAP BTP. See [Log On to the Cloud Foundry Environment Using the Cloud Foundry Command Line Interface](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/7a37d66c2e7d401db4980db0cd74aa6b.html).

4. To build the appliation, use this command in a console started from the root folder of the project:

      ```
      mvn clean install
      ```

### 4. Configure the Entitlements to the Cloud Foundry Runtime

1. Log on to the SAP BTP cockpit as Cloud Foundry administrator.
2. Go to your global account.
3. [Feature Set A]: From the navigation area, choose **Entitlements** > **Subaccount Assignments** and enter your subaccount.
   [Feature Set B]: From the navigation area, choose **Entitlements** > **Entity Assignments**.

4. If there is no entry for the Cloud Foundry runtime, choose **Configure Entitlements** and **Add Service Plans**.
5. In the following popup, proceed as follows:

    1. Choose **Cloud Foundry Runtime**.
    2. Under **Available Service Plans**, select the checkbox **MEMORY**.
    3. Choose **Add 1 Service Plan**.
  
6. Choose **+** to add at least 1 to the subaccount.
7. Choose **Save**.

### 5. Create a Destination Service Instance

You have to create a service instance of the Destination service using the `lite` service plan.

1. To log on to the cf CLI, use this command:

      ```
      cf login -a https://api.cf.sap.hana.ondemand.com
      ```
  
  where https://api.cf.sap.hana.ondemand.com is the API endpoint of the subaccount. See [Log On to the Cloud Foundry Environment Using the Cloud Foundry Command Line Interface](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/7a37d66c2e7d401db4980db0cd74aa6b.html).

2. To navigate to your space, type in the console the number that corresponds to your Org in the list with Orgs that is displayed after you log on to cf CLI. 
  
3. To create the Destination service instance, use this command:

      ```
      cf create-service destination lite destination
      ```

### 6. Create an SAP S/4HANA Cloud Extensibility Service Instance to Consume the SAP S/4HANA Cloud APIs

You have to create a service instance of the SAP S/4HANA Cloud Extensibility service using the `api-access` service plan.

During the service instance creation, an HTTP destination on a subaccount level is automatically generated in this subaccount. It contains all instance binding properties which are sufficient to establish connection to the SAP S/4HANA Cloud system. When creating the service instance, you configure the communication arrangement and the authentication type for the connection in a JSON file. 
  
In cf CLI, use this command to create a service instance of the SAP S/4HANA Cloud Extensibility service:

```
cf create-service s4-hana-cloud api-access s4-hana-cloud -c s4-0008.json
```
      
---
**Note:** Before creating the service instance, make sure you have edited the **s4-0008.json** file by replacing the values of these parameters:

* **"systemName"**: this is the system you registered in step 1. 
* **"communicationArrangementName"**: the name you specify here will be the same name of the communication arrangement that is going to be automatically created in your SAP S/4HANA Cloud tenant. 
---
      
### 7. Create a service instance of the Authorization and Trust Management (XSUAA) service

In cf CLI, use this command to create a service instance of the Authorization and Trust Management (XSUAA) service:

```
cf create-service xsuaa application xsuaa 
```

### 8. Build, Deploy and Run the Application

These are the steps you need to follow to get the SAP S/4HANA Cloud Business Partner Sample App, deploy it and run it:

1. In the cf CLI, navigate to the root folder of the project using the command:

      ```
      cd <root_project_folder>
      ```

2. In the cf CLI push the `vars.yml` file using this command:

      ```
      cf push --vars-file vars.yml
      ```

3. To run the application, copy and paste this URL in a browser:

      ```
      http://business-partners-demo-web<your-user>.cfapps.sap.hana.ondemand.com/index.html
      ```
   
   where **<your-user>** is your S-user, P-user, or trial user.
   
## Summary
