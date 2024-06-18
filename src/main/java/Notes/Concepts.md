How we autowired Repo interfaces ?
=> 1. There implementation is there is class which is known as proxy class which loaded at runtime.when u autowired then u create instance of that. 


 How ModelMapper Works ?

=> https://modelmapper.org/user-manual/how-it-works/#:~:text=ModelMapper%20consists%20of%20two%20separate,a%20source%20to%20destination%20object.


Use Regex for validation : https://www.geeksforgeeks.org/regular-expressions-in-java/


Basics of Validation :

1. Java Bean is validated using JSR 380 as Bean Validation 2.0 .

IMP Annotations for Validations :

@NotNull
@Size
@Min
@Max
@NotEmpty


SB supports Hibernate Validator :
1. Add dependency :starter-validations
2. then use annotations

-------------------------------------------

When to use Lazy Loading means  -  FetchType.LAZY ? 
=>  When to Use Lazy Loading:
Lazy loading is often suitable for scenarios where the associated entities or collections are not always needed and loading them eagerly would result in unnecessary overhead. It's particularly useful when dealing with large object graphs and to optimize performance by deferring the loading of related data until it's required.

----------------------------------------------------------------------------

Relationships 

 mappedBy is used in Entity which doesn't contain foreign key .

-----------------------------------------------------------------------------

Constructor injection is preferred over field injection for several reasons:

1. **Explicit Dependencies**: With constructor injection, dependencies are explicitly defined as parameters in the constructor, making it clear which dependencies are required for the class to function. This provides transparency and makes the class's dependencies explicit, improving readability and understanding of the class's requirements.

2. **Immutability**: When dependencies are injected via the constructor, they can be marked as `final`, making the class immutable once instantiated. Immutability can lead to safer and more predictable code, especially in concurrent or multithreaded environments.

3. **Testability**: Constructor injection makes it easier to write unit tests for the class, as it allows for easy substitution of dependencies with mock objects or stubs. This is because the dependencies are provided as parameters to the constructor, enabling straightforward injection of test doubles for testing purposes.

4. **Enforcement of Dependency Injection**: With field injection, dependencies can be directly assigned to the class's fields, making it possible for them to be changed after the object is constructed. Constructor injection ensures that all required dependencies are provided at the time of object creation, reducing the risk of null or uninitialized dependencies.

5. **Reduced Coupling**: Constructor injection reduces the coupling between classes, as the dependencies are passed in from the outside rather than being directly accessed or modified within the class. This promotes better separation of concerns and improves the maintainability of the codebase.

6. **Consistency**: Using constructor injection consistently across the codebase provides a uniform approach to dependency injection, making the codebase more predictable and easier to understand for developers.

Overall, constructor injection promotes better design practices, improves testability, and reduces the likelihood of unexpected behavior related to mutable state or uninitialized dependencies, making it the preferred method for dependency injection in many scenarios.


----------------------------------------------------------------------------------------------------------

The difference between C1 and C2 lies in the use of the `collect` method with `Collectors.toList()` in C1 and the `toList()` method in C2. Let's break down the differences:

C1:
```java
postRepo.findByCategory(category)
    .stream()
    .map(post -> modelMapper.map(post, PostDto.class))
    .collect(Collectors.toList());
```

C2:
```java
return postRepo.findByCategory(category)
    .stream()
    .map(post -> modelMapper.map(post, PostDto.class))
    .toList();
```

In both C1 and C2, the code is performing the following steps:
1. Fetching posts from the `postRepo` based on a specific category.
2. Converting the fetched posts to `PostDto` using a model mapper.

The difference lies in how the results are collected into a list:

In C1:
The `collect` method is used to accumulate the elements into a new `List`. The `Collectors.toList()` method is used as the downstream collector to collect the elements into a `List`.

In C2:
The `toList` method is a new method introduced in Java 16 as part of the Stream API. It directly collects the elements of the stream into an unmodifiable list.

The main difference between C1 and C2 is that C2 uses the `toList` method directly on the stream, providing a more concise and readable way to collect the stream elements into a list. It simplifies the syntax and eliminates the need to explicitly call the `collect` method and use `Collectors.toList()`.

In summary, C1 and C2 achieve the same result of collecting the mapped elements into a list, but C2 uses the new `toList` method introduced in Java 16, providing a more streamlined and expressive syntax for collecting stream elements into a list.
-----------------------------------------------------------------------------------------------------------

## Pagination 

1. When there too much data to display then use this concept.
=> from client we will get input in the form of parameter.
ex: http://localhost:9090/posts?pageSize=5&pageNo=2&sortBy=title


In pagination we want following things to be there in our application :

1. pageNumber
2. pageSize
3. totalElements
4. totalPages
5. is it LastPage
6. content - [not only this]

------------------------------------------------------------------------------------------------------

# How servelet works ?
=> A servlet in Java is a server-side component that extends the capabilities of a server. It receives and responds to requests from web clients, typically web browsers, and communicates with web servers using the HTTP protocol. Here's a detailed overview of how servlets work:

1. Initialization: When a web server starts, it loads and initializes the servlets defined in its configuration. This typically involves creating an instance of each servlet and calling its init method.
Request Handling: When a client (e.g., a web browser) sends an HTTP request to the server, the server determines which servlet should handle the request based on the URL mapping defined in the server's configuration.

2. Lifecycle Methods: The server calls the servlet's service method, passing it the request and response objects. The service method then dispatches the request to the appropriate method (doGet, doPost, doPut, etc.) based on the HTTP method used in the request.

3. Request Processing: The servlet processes the request, which may involve tasks such as reading request parameters, accessing session data, interacting with databases, or performing any other necessary business logic.
Response Generation: After processing the request, the servlet constructs an HTTP response by setting response headers, status codes, and writing the response content to the response object obtained from the HttpServletRequest.

4. Sending Response: Once the response is prepared, the servlet sends it back to the client by writing the response content to the output stream or writer obtained from the HttpServletResponse.

5. Destruction: When the server is shut down or the servlet is no longer needed, the server calls the servlet's destroy method to allow it to release any resources it has allocated.
Servlets are often used to generate dynamic web content, handle form submissions, and interact with databases. They are a fundamental part of Java-based web applications and provide a powerful mechanism for server-side processing of HTTP requests.


---------------------------------------------------------------------------------

Serverlet: 
A servlet is like a waiter at a restaurant. When you go to a restaurant, you place an order with the waiter, and they bring your food back to you. Similarly, a servlet takes requests from web browsers, processes them, and sends back the results. It's like a server-side waiter for web requests.


When we have to write API of dowloading an image from database to client's browser then what to use serverlet or controller ?

=>  Alternative way of serving image in Spring boot and it is more convient way :

@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
public ResponseEntity<byte[]> downloadImage(@PathVariable String imageName) throws IOException {
    InputStream resource = fileUploadService.getResource(path, imageName);
    byte[] imageBytes = IOUtils.toByteArray(resource);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);

    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
}

## TODO: https://www.baeldung.com/spring-response-entity
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html 



--------------
How ResponseEntity works ?
=> Yes, internally, ResponseEntity in Spring Boot does utilize servlets in the background. When a request is made to a Spring Boot application, the request is ultimately handled by the embedded servlet container (such as Tomcat, Jetty, or Undertow) that Spring Boot uses.
Here's how ResponseEntity works internally and its interaction with servlets:
Controller Method Execution:
When a request is received by the Spring Boot application, it is mapped to a specific controller method based on the request URL and HTTP method.
Building ResponseEntity:
Inside the controller method, when a ResponseEntity is created and returned, it encapsulates the HTTP response details such as status code, headers, and body.
Servlet Container Interaction:
When the ResponseEntity is returned from the controller method, Spring Boot's internal processing involves converting the ResponseEntity into an actual HTTP response that can be understood by the servlet container.
Servlet Container Handling:
The servlet container, which is responsible for handling incoming requests and generating responses, takes the information from the ResponseEntity and constructs the appropriate HTTP response.
Dispatching the Response:
The servlet container then dispatches the constructed HTTP response back to the client that made the original request.
Use of Servlets:
The servlet container plays a crucial role in the entire process of handling HTTP requests and responses in a Spring Boot application. It manages the lifecycle of servlets, processes incoming requests, and dispatches responses back to clients.
In summary, while ResponseEntity itself is a part of Spring's web framework and provides a high-level abstraction for building HTTP responses, its functionality ultimately relies on the underlying servlet container to handle the actual processing and dispatching of the HTTP response.


-----------------------------------
1.  Securing API :

2. What is configuration class :

In Spring Boot, a configuration class is used to define and configure beans, components, and various settings for the application. It plays a crucial role in setting up the application's environment, defining dependencies, and customizing the behavior of different components. Here's a breakdown of the purpose and meaning of a configuration class and the concept of configuration in general:
Configuration Class in Spring Boot:
Bean Definition: Configuration classes are used to define and configure beans, which are objects managed by the Spring IoC (Inversion of Control) container. These beans can represent services, data sources, components, or any other objects that the application needs.
Annotation: In Spring Boot, a configuration class is typically annotated with @Configuration, indicating that it contains bean definitions and application configuration settings.
Customization: Configuration classes allow developers to customize various aspects of the application, such as defining data sources, integrating with third-party services, setting up security, configuring logging, and more.
Modularity: By using configuration classes, the application's configuration can be modularized and organized into separate classes, making it easier to manage and understand the configuration settings.
Meaning of Configuration:
The term "configuration" refers to the process of setting up and customizing the behavior of an application or system. In the context of software development, configuration encompasses a wide range of settings, parameters, and options that determine how the application operates and interacts with its environment.
Key Aspects of Configuration:
Customization: Configuration allows developers to tailor the behavior of the application to specific requirements, such as defining database connections, specifying logging levels, or enabling/disabling certain features.
Environment Setup: Configuration includes setting up the application's environment, including properties, profiles, and external dependencies needed for the application to function correctly.
Separation of Concerns: Configuration helps in separating the application's logic from its configuration, promoting modularity and maintainability.
Flexibility: Well-designed configuration allows for flexibility in adapting the application to different deployment environments, such as development, testing, staging, and production.
In summary, a configuration class in Spring Boot is a fundamental component for defining and customizing the application's behavior, while the concept of configuration encompasses the broader idea of setting up and customizing the application's environment and behavior to meet specific requirements.
-----------------------------------------------------------------------------------------

1. How server handles incoming requests , what software and hardware tools use it to do so ?
=> 1. Recieving request :  when client such as web browser sends an HTTP requests , it is recieved by server's network stack , which listens for incoming request at specific port.
(e.g :  port 80 for HTTP)
2. Routing and Dispatching
: The server uses its routing mechanism to determine which component or endpoint within the application should handle the incoming request based on the request's URL and HTTP method (GET, POST, etc.).

3. Request Processing: Once the request is routed, the server passes it to the appropriate component (e.g., servlet, controller, handler) within the application for processing. This may involve executing business logic, accessing databases, or performing other operations.

4. Response Generation: After processing the request, the server generates an HTTP response, which includes the requested data, HTML content, or other resources. The response is then sent back to the client.


Software Tools:
Web Server Software: This includes software such as Apache HTTP Server, Nginx, Microsoft IIS, or embedded web servers like Tomcat, Jetty, or Undertow, which handle incoming requests and serve web content.
Application Server: In the case of more complex web applications, an application server (e.g., WildFly, WebSphere, JBoss) may be used to provide additional services such as transaction management, messaging, and clustering.
Middleware: Middleware components like load balancers, reverse proxies, and caching servers can be used to optimize request handling, improve performance, and enhance security.
Frameworks and Libraries: Web frameworks like Spring Boot, Django, Express.js, or Ruby on Rails provide tools and libraries for handling HTTP requests and building web applications.
Hardware Tools:
Network Interface Cards (NICs): These hardware components enable the server to connect to the network and receive incoming requests.
Load Balancers: Hardware load balancers distribute incoming requests across multiple servers to improve performance and reliability.
Storage Devices: Servers may use storage devices such as hard drives, solid-state drives, or network-attached storage (NAS) to store and retrieve data required to process incoming requests.
Memory (RAM): Adequate memory is essential for handling concurrent requests and efficiently processing data in memory.
In summary, the server uses a combination of software tools such as web server software, application servers, middleware, and frameworks, along with hardware tools like network interface cards, load balancers, storage devices, and memory to handle incoming requests efficiently and serve web content to clients.



Spring Boot's Embedded Web Server (e.g., Tomcat):
Request Routing: The embedded web server, such as Tomcat, handles the routing of incoming HTTP requests to the appropriate controllers and methods within the Spring Boot application based on the defined URL mappings.
Servlet Container: It acts as a servlet container, managing the lifecycle of servlets and handling the execution of servlet-based components within the application.
Static Content Serving: The embedded server can serve static resources such as HTML, CSS, JavaScript, and image files, allowing the application to serve web pages and assets directly.
Concurrency Management: It manages concurrent request handling, ensuring that multiple requests can be processed simultaneously without blocking each other.
Integration with Spring Boot: The embedded server seamlessly integrates with Spring Boot, allowing developers to build and deploy web applications with minimal configuration and setup.

-----------------------------------------------------------------------------------------

## How to decide which logics should be written in backend on api itself and which logic should be written on frontend while accessing that api? for example I want to restrict the private post on application should only be seen by my connections so this restriction logic should be applied on frontend or backend ?


Deciding where to implement specific logic, whether in the backend API or the frontend, depends on several factors related to security, performance, and user experience. In the case of restricting access to private posts to a user's connections, the decision should be based on the following considerations:

Backend Logic:
Security: Security-critical operations, such as access control and data authorization, should primarily be enforced in the backend to prevent unauthorized access or tampering. This includes ensuring that private posts are only accessible to authorized users.
Data Integrity: Logic that ensures the integrity and consistency of data, such as access control rules and permissions, should be implemented in the backend to maintain a single source of truth for data access.
Scalability and Performance: By implementing access control logic in the backend, you can ensure consistent enforcement of access rules across different client applications and reduce the risk of unauthorized access attempts.
Frontend Logic:
User Experience: Logic that directly impacts the user experience, such as client-side filtering and presentation of data, can be implemented in the frontend to provide immediate feedback and responsiveness to user interactions.
Performance Optimization: Non-security-critical logic that aims to optimize performance, such as client-side caching and pre-fetching of data, can be implemented in the frontend to reduce the frequency of requests to the backend.
In the Context of Private Post Access Restriction:
In the scenario of restricting private post visibility to a user's connections, the core access control logic should be implemented in the backend API. This ensures that unauthorized users cannot bypass the access restrictions, maintaining the security and integrity of the private post data.
The frontend can handle the presentation and user interface aspects related to displaying and interacting with the restricted posts based on the data provided by the backend. This includes rendering the posts, providing user feedback, and managing the user experience around accessing and interacting with the restricted content.
In summary, security-critical access control logic, such as restricting access to private posts, should be implemented in the backend API to ensure data security and integrity, while the frontend can focus on user experience and presentation aspects related to interacting with the restricted content.


## without creating the component to any class can we autowire that particular class in some other class  in spring boot ?


In Spring Boot, autowiring a class without explicitly creating a component or defining it as a bean is possible through the use of the @Autowired annotation. However, this approach has some specific requirements and considerations:
Component Scanning: By default, Spring Boot performs component scanning to automatically detect classes annotated with @Component, @Service, @Repository, and @Controller. These classes are then eligible for autowiring without the need to explicitly define them as beans.
@Autowired Annotation: The @Autowired annotation can be used to inject dependencies into other Spring-managed beans. When Spring Boot encounters the @Autowired annotation, it looks for a suitable candidate for autowiring from the application context.
Considerations: While it's possible to autowire a class without explicitly creating it as a component, it's important to ensure that the class to be autowired is managed by Spring. This can be achieved by ensuring that the class is within the package hierarchy that is scanned by Spring for components.
Here's an example of autowiring a class without explicitly creating it as a component:
// Example of a class to be autowired
public class MyService {
    public void doSomething() {
        // Logic here
    }
}
// Example of a class where autowiring is performed
@Service
public class MyOtherService {
    @Autowired
    private MyService myService;
    // Rest of the class
}

In this example, MyService does not have an explicit Spring stereotype annotation like @Component, but it can still be autowired into MyOtherService using the @Autowired annotation. This works because both classes are managed by Spring and are within the component scanning path.
However, it's important to note that relying on implicit component scanning for autowiring without explicitly marking classes as components can lead to potential issues related to code readability, maintainability, and understanding of dependencies. It's generally recommended to explicitly mark classes as components or define them as beans to make the codebase more transparent and maintainable.



## How configure method of SecurityConfig which extend WebSecurityConfigure

=> Because of Auto-configuration and component scanning feature of spring boot this is get called.

1. When SB app starts its do Component Scanning and all the classes annotated with @Configuration are automatically configured.

2. It does auto configuration for spring security whenever it find it in classpath and all its does all default security setup.

3. Spring security framework invoke the custom configure method at app startup.

-------------------------------------------------------------------

JWT[Json Web Token] Authentication : [ 8 steps ] 

1. It mostly used mechanism to secure REST API. 
2. It follows state-less authentication mechanism as it didn't store anything on server. It generate token.


Architecture of JWT 

1. Header => Algo + Type
2. Payload => Info about claims[data]
3. Signature => encoded header + encoded payload + key



eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c



eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9 ==> header 

eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ     ==> Payload [ Info about claims]- [data]

SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c => Signature



## Why to prefer JWT over basic authentication ?

=> 1. Statelesness :
    => 1. In basic authentication the server needs to maintain state of autenticated session through cookies or session.
       2. JWT authentication: 
     => Stateless => why: Because we don't need to store anything on server => Because everything required for authentication and authorization is present in JWT Token itself. 
   
   2. Single Sign On :
   1. JWTs can be used to enable single sign-on (SSO) and identity federation across multiple applications and services.
   
   benefits such as statelessness, scalability, cross-origin support, flexibility, and support for SSO and identity federation. These advantages make JWT authentication a popular choice for securing modern web applications and APIs, especially in distributed and microservices-based architectures.
   
   
  
  Note: . When a user initially logs in using their credentials, the server authenticates the user and generates a JWT containing the necessary user information and claims. This JWT is then sent back to the client (e.g., web browser, mobile app) and typically stored on the client side, such as in local storage or a cookie.
  
  The server does store the user's credentials during the initial authentication process, but once the JWT is issued, the server does not need to retain the user's session state or credentials. The JWT itself contains all the necessary information for subsequent authentication and authorization, including user identity, permissions, and any other relevant claims.
     
     
   JWT Token generation =>
    login => token generation
    token => access API 
 
 Steps to implement JWT:
  1. Add dependency(io.jsonwebtoken) 
  2. Create JwtAuthenticationEntryPoint implements AuthenticationEntryPoint (override 1 method) => [Why ? => this method works when unauthorized user tries to access our API then exception will be thrown )
  3. JwtTokenHelper - [Help to write all method for token operation]
  4. JwtAuthenticationFilter extends OnceRequestFilter
  => [Before any request for rest api this method will check authentication header ] 
      methods:
        a. Get JWT token from response. => Request and Format
        b. Validate Token.
        c. Get user from token.
        d. Load User associated with token
        e. Set authentication using spring security.
 
  5. Create JwtAuthResponse => send token , type , user detail and date of generation.
  6. Configure JWT in spring security config.
  7. Create Login API to return token.=> Authenticate => token return 
  8. Test Application

----------------------------------------------------------------------------------------------------

1. Role Specific Access : 
@EnableGloablMethodSecurity(prePostEnabled=true) on security config 

And the api which we want to restrict so for that we can use this annotation on its controller:
1.@PreAuthorize("hasRole('ADMIN')")

  
----------------------------------------------------------------------------------------------------------------------------

Suggestions on code by Siddhant :

1. Should have interface to Controller layer as frontend developer got to know then which apis are there
2. Authentication and Authorization should be there for each and every API.
3. Every API should have base name. 
4. Don't use modelMapper as it also set the null parameters 
5. Store image in database and S3 image 
6. Don't show private details like password and all to clients 
7. Use aws s3 to store your data .

Challenge from Sunny Sir 

1. Made a design of such a situation where 1 user can comment and view other post but can not write on that post 
but 2nd user can not comment and view others post . 

