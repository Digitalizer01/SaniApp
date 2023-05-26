# SaniApp

Built on Android and Firebase, SaniApp offers a secure and user-friendly solution for residences, staff members, and residents involved in medication management

## Table of Contents

1. [Introduction](#introduction)
2. [Android](#android)
3. [Firebase](#firebase)
4. [Database Structure](#database-structure)
5. [User Roles](#user-roles)
6. [Authentication and Account Management](#authentication-and-account-management)
7. [Pillbox repository](#pillbox-repository)

## 1. Introduction<a name="introduction"></a>

This repository is dedicated to a mobile platform application designed to streamline and enhance the management of residential pillbox systems. The application aims to provide an efficient and user-friendly solution for residences, staff members, and residents involved in medication management. By leveraging the power of Android and Firebase, the platform offers a robust and secure environment for managing medication schedules, resident profiles, and staff operations.
The repository includes the necessary code, documentation, and database structure to facilitate the development, deployment, and maintenance of the mobile platform application. With a focus on usability, security, and scalability, this repository serves as a comprehensive resource for building a reliable and intuitive solution for residential pillbox management.

## 2. Android<a name="android"></a>

Android is an open-source operating system for smart devices such as mobile phones or tablets. It was developed by Google and Open Handset Alliance based on the Linux kernel and other open-source software.

Android is a free and open-source operating system designed for all smart mobile devices. It was born in 2007, thanks to the joint effort of Open Handset Alliance (OHA) and Google, who created Android Inc. in Silicon Valley. Google developed the Dalvik virtual machine, which runs Java code and interprets it as Dalvik bytecode. This virtual machine is designed to enjoy all the functions on a device with limited memory and CPU, with efficient power consumption.

Android supports a variety of programming languages, making it accessible to people with different roles:
- Java: It is the native language of the Android operating system and one of the most popular languages in the world. This language is necessary for an application to communicate with the device or utilize different hardware components.
- JavaScript: It is a simpler and lightweight language compared to Java. With the help of a converter called IONIC, it allows the use of different hardware components with fewer resources.
- HTML5: It is the fifth version of the original HTML language. It is a basic web language that can greatly reduce resource consumption.
- CSS: It complements the previous languages as it takes care of the graphical representation of documents written in HTML5. This includes design and style.
- Kotlin: It appeared in 2017 and has become the most suitable language for programming applications on this operating system. This shift is due to the fact that its code is simpler than the previous code.

## 3. Firebase<a name="firebase"></a>

Firebase is a web and mobile application development platform launched in 2011 and acquired by Google in 2014.

Firebase is a cloud-based platform integrated with Google Cloud Platform, using a set of tools to create and synchronize projects, which allows increasing the number of users and also provides greater monetization.

Firebase provides users with excellent documentation to create applications with this platform. Additionally, it offers free email support to all its users and developers who are also active participants in platforms like Github and StackOverflow, as well as a YouTube channel that explains the operation of many tools. Thanks to all these features, any developer can combine and restructure the platform to adapt it to their needs and requirements.

In the case of this application, two services will be used:
- Firebase Authentication: It is a service that can authenticate users using client-side code only. Authentication can be performed through popular login providers such as Facebook, GitHub, Twitter, Google, Yahoo, and Microsoft, as well as classic login methods using email and password. It also includes a user management system through which developers can enable user authentication using emails and passwords stored in Firebase, all in an encrypted and secure manner.
- Firebase Realtime Database: It is a real-time backend database organized as a JSON tree. The service provides application developers with an API to synchronize and store application data in the Firebase cloud. The company allows integration with Android, iOS, JavaScript, Java, Objective-C, Swift, and Node.js applications. The database can also be accessed through a REST API and has integration for various JavaScript frameworks like AngularJS, React, Ember.js, and Backbone.js.

## 4. Database Structure<a name="database-structure"></a>

The Firebase database structure consists of the following components:
- **Admin**: This node contains the ID of the admin user who manages the system.

- **Residences**: This node represents the residences registered in the system. Each residence is assigned a unique ID as a child node.

  - **Data**: This node contains general information about the residence, such as the city, country, email, name, phone, province, street, timetable, and ZIP code.

  - **Residents**: This node stores information about the residents living in the residence. Each resident is assigned a unique ID as a child node.

    - **Data**: This node contains details about each resident, including their birthdate, gender, IDPillbox, name, and surnames.

    - **Medication**: This node stores the medication information for each resident. It is structured based on the days of the week (e.g., Monday, Tuesday, etc.) and contains the medication details for each time of the day (morning, afternoon, evening, night). Each medication entry includes the hour, medication name, and whether it has been taken or not.

- **Staff**: This node stores information about the staff members associated with the residence. Each staff member is assigned a unique ID as a child node.

  - **Data**: This node contains details about each staff member, such as their birthdate, email, gender, name, phone, and surnames.

This database structure allows for efficient organization and retrieval of data related to residences, residents, and staff members involved in the pillbox project.

## 5. User Roles<a name="user-roles"></a>

The system supports three types of user roles:
- **Admin**: Has full access to all system features and functionalities.
- **Residence Admin**: Manages a specific residence and has permissions to perform administrative tasks within that residence.
- **Residence Staff**: Works in a specific residence and has restricted permissions to perform certain tasks within that residence.

## 6. Authentication and Account Management<a name="authentication-and-account-management"></a>

Before accessing the system, users need to log in with their account credentials. If they don't have an account, they can sign up and create a new account. All user account information is encrypted and securely stored for privacy and security reasons.

Note: To use the SaniApp Android app with Firebase, you will need to provide the necessary Firebase credentials. Please note that the specific details about the Firebase credentials, such as the Firebase project ID, API key, and other configuration settings, are not included in this repository.

To set up the Firebase credentials for the app, follow these steps:

1. Create a new Firebase project on the [Firebase Console](https://console.firebase.google.com/).
2. Generate the required Firebase configuration files (google-services.json) and place them in the appropriate directory within your project.
3. Replace the placeholder values in the configuration files with your actual Firebase credentials.
4. Build and run the Gamection Android app on your device or emulator.

Make sure to keep your Firebase credentials secure and avoid committing them to version control or sharing them publicly. It is recommended to use a secure and private method to handle Firebase credentials, such as using environment variables or a secure configuration file that is excluded from version control.

Please refer to the Firebase documentation for detailed instructions on setting up Firebase credentials for your Android app.

## 7. Pillbox repository<a name="pillbox-repository"></a>

There is a respository for the pillbox that contains the code and resources for the pillbox management functionality of SaniApp. It includes the logic and interfaces required for medication tracking, scheduling, and administration. The repository provides the necessary components to integrate the pillbox management feature into the main application seamlessly. Developers can refer to the Pillbox Repository for detailed implementation guidelines and access the codebase to understand the inner workings of the pillbox management functionality.

You can visit the repository [here](https://github.com/Digitalizer01/SaniApp-Pillbox).
