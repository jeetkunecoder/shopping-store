# Shopping Store API

Sample Shopping Store Application. This project relies on Play Framework to expose a REST API and Slick for DB queries.

## How do I get setup?

Install PostgreSQL. (This process applies for Mac, for installing in Linux
or Windows you can refer to ​http://postgresguide.com/setup/install.html​ ) 

```bash
$ b​rew install postgresql
```
 
Start PostgreSQL:

```bash
​$ brew services start postgres
```

Run psql:

```bash
$ psql postgres
```

Create a database, for example:
```bash
​postgres=#​ CREATE DATABASE mydb;
```

Check the database owner:
```bash
p​ostgres=#​ \l
```

Exit psql by typing ​exit;​ in the terminal

- Clone the project to a local directory 

- Open a terminal and ​navigate to the root of the ​shopping-store project previously downloaded.
Execute the schema.sql Postgres script to create the table. Substitute <db-owner> ​with the DB owner user found in previous steps.
```bash
$ psql -U <db-owner> -d mydb postgres -f resources/schema.sql
```

- Open the project with IntelliJ and then open the application.conf file and change the DB user for your <​ db-owner>​ from previous steps.

Alternatively, you can also change this line with Vim or with any text editor.

Compile the project:
```bash
$ sbt compile
```

Run the tests:
```bash
$ sbt test
```

Run the application
```bash
$ sbt run
```

The application will start running in ​http://localhost:9000 and we can start making requests to the endpoints. 
You can check the available endpoints in the routes file in the project.

