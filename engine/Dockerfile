FROM openjdk

# set a directory for the app
RUN mkdir /app

COPY build/libs/*.jar /app/engine.jar

# define the port number the container should expose
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/engine.jar"]