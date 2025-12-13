FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/PurchaseOrder-0.0.1-SNAPSHOT.jar ./PurchaseOrder-0.0.1-SNAPSHOT.jar

EXPOSE 8001

ENTRYPOINT ["java","-jar","PurchaseOrder-0.0.1-SNAPSHOT.jar"]
