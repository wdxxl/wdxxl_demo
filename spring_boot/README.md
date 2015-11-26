# Spring Boot Demo

##Chinese Demo
####http://blog.xiatiansong.com/2015/01/06/build-app-with-spring-boot-and-gradle/
####http://www.javacodegeeks.com/2014/06/spring-boot-fast-mvc-start.html

##Official Demo
####https://spring.io/guides/gs/rest-service/

###1. Restful Access URL:
####http://localhost:8080/greeting
####http://localhost:8080/greeting?name=User

###2. Unit Test
####GreetingControllerTest.java

###3. static web access
####http://localhost:8080/public/index.html

###4. Web Jsp page
####http://localhost:8080/hello

###5. banner.txt

###6. Restful User examples
####DEFAULT -- http://localhost:8080/users/1 (GET)
####GET     -- http://localhost:8080/users/1/customers
####POST    -- http://localhost:8080/users/createUser (Content-Type:application/json) ({"id":1,"name":"king"}) 
####PUT     -- http://localhost:8080/users/modifyUser (same as POST)
####PATCH   -- http://localhost:8080/users/patchUser (same as PUT)
####DELETE  -- http://localhost:8080/users/1

###Tips 1: Application Should Stay In Parent Package
###Tips 2: webapp under folder of src/main