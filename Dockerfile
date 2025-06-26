Dockerfile
``>
*(Make sure it’s not `Dockerfile.txt` by accident)*

---

## ✍️ STEP 2: Paste This Inside Your `Dockerfile`

Open the file and paste this:

```Dockerfile
# Step 1: Build the app with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the app
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
