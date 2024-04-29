# Use the official maven/Java 17 image to create a build artifact.
# This is based on Debian and sets the MAVEN_HOME environment variable
FROM maven:3.8.4-openjdk-17 as build

# Set the working directory in the image to /app
WORKDIR /app

# Copy the pom.xml file to our app directory
COPY pom.xml .

# This layer is used to cache the dependencies
# It will only be re-built when the pom.xml file changes
RUN mvn dependency:go-offline -B

# Copy the rest of the application
COPY src /app/src

# Package the application
RUN mvn package -DskipTests

# Start with a base image containing Java runtime
FROM eclipse-temurin:17.0.8.1_1-jdk

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Set the application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
COPY --from=build /app/${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/app.jar"]
