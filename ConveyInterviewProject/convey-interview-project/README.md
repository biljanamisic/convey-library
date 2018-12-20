# interview

How to start the interview application
---

1. Create database called convey_db
2. Create schema public in the database and make sure the db runs on the default port (5432)
3. Checkout the source code
4. Position in the project root folder (where pom.xml is located) and run `mvn package` to build your application
5. In the same position, start application with `java -jar target/convey-interview-project-1.0.jar server config.yml`
6. To check that your application is running, please enter url `http://localhost:8080`
7. If it runs, call the following url via a browser: `http://localhost:8080/library/client/books/save-books-in-bulk`  (it will create 10 books in the DB)
8. Call  the following url to retrieve a book: `http://localhost:8080/library/client/books/1`
9. Check if the file called `books-fetched-from-api.txt` is created in `src/main/resources/archive`
10. The file should display each book that is called in step 8


Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
