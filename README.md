# Virtual bank 
[![CircleCI](https://circleci.com/gh/grzegorz103/virtual-bank-system.svg?style=svg)](https://circleci.com/gh/grzegorz103/virtual-bank-system) ![GitHub language count](https://img.shields.io/github/languages/count/grzegorz103/virtual-bank-system) ![GitHub top language](https://img.shields.io/github/languages/top/grzegorz103/virtual-bank-system)    

## Table of contents
* [General info](#general-info)
* [Live demo](#live-demo)
* [Screenshots](#screenshots)
* [Technologies](#technologies)
* [Features](#features)
* [Setup](#setup)

## General info
Application which simulates electronic banking managment on virtual bank accounts.

## Live demo
**You may need to wait a while until back-end loads**    
https://virtual-system-af1e2.firebaseapp.com/

## Screenshots
<p>
<img width="800" src="https://i.imgur.com/i0uGako.png"><img width="800" src="https://i.imgur.com/r40WlZW.png">
</p>
### Sample accounts

| __Identifier__ | __Password__ | Role |
| -------------- | ------------ | --- |
| 11111111 | kowalski | Admin |
| 22222222 | kamilski | Client |
| 33333333 | jakubski | Employee |    

## Technologies
- Spring (Boot, Security, Data JPA, MVC)
- Angular 8
- Hibernate
- JUnit & Mockito
- PostgreSQL
- Mapstruct, Lombok
- CircleCI, Maven

## Features
- Client registration (employees need to activate new clients accounts)
- System admin/employee managment modules
- Several types of operations on virtual bank accounts are available for clients
- Chat support system

## Setup
### Prerequisites

- Angular 8 or greater is required
```$xslt
$ npm install -g @angular/cli
``` 
- Java 8+

### Deployment

```
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev
$ cd front
$ npm install
$ ng serve
```
Run browser and head to ```http://localhost:4200```
