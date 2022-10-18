## How to  Run

Start the backend server before the frontend client.

**Backend**

1. Install [PostgreSQL](https://www.postgresql.org/download/)
2. Configure datasource in `application.yml`.
3. `cd backend`.
4. Run `mvn install`.
5. Run `mvn spring-boot:run`.
6. Spring Boot will import mock data into database by executing `import.sql` automatically.
7. The backend server is running on [localhost:8080]().

**Frontend**
1. Install [Node.js and npm](https://www.npmjs.com/get-npm)
2. `cd frontend`.
3. Run `npm install`.
4. Run `ng serve`
5. The frontend client is running on [localhost:4200]().

Note: The backend API url is configured in `src/environments/environment.ts` of the frontend project. It is `localhost:8080` by default.
