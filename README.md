## BookWorm server side

Backend part for BookWorm project.
This document is for making documentation like:
* Endpoints for getting to data, for example
>  Endpoint to get all users: `/getAllUsers`

* Features or mechanisms you want to explain

**All info must be added below this document's info with header that informs 'what is this about"**

### Architecture
Prototypes, files, images or links - you have issues and tasks for this, also you can pin info related to the task in by commenting it

## How to? 

### How to work with PostgreSQL

For now, our application supports PostgreSQL, and to work with it - follow theese steps:
1. You can install pgAdmin for administrating your local database (one-database-for-everybody is in progress)
2. Configure the user
3. Change the application.properties file in your version of the app
4. You should change to yourvalues fields:
- `datasource.url=`
- `datasource.username=` - probably it will be "**postgres**"
- `datasource.password=`
5. Start your application
6. If you have any troubles with it - feel free to contact [Me](https://gitlab.com/alex.tifox)