Getting this App up and running, follow below guideline.
Build the project: mvn clean install
Run jar to start up: java -jar target\momentumshoppe-0.0.1-SNAPSHOT.jar or mvn spring-boot:run

Integration testing:
getProducts curl: curl -X GET "http://localhost:8080/momentum-active-shoppe/getProducts" -H  "accept: application/json"
purchaseProduct: curl -X POST "http://localhost:8080/momentum-active-shoppe/purchaseProducts" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"customerId\":\"T20\",\"products\":[270,271,272]}"

Spring-doc URI: http://localhost:8080/momentum-active-shoppe/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

H2-in-memory DB: http://localhost:8080/h2-console/login.do?jsessionid=bfe817daa1cea91f4d0a250995c9e6ad
Username: sa
password: password

prometheus Monitoring: http://localhost:8080/actuator/prometheus