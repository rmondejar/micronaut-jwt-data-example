FROM openjdk:14-alpine
COPY build/libs/mn-jwt-data-*-all.jar mn-jwt-data.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "mn-jwt-data.jar"]
