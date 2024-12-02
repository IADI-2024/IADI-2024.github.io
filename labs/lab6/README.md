# Microservices application with capability-based security

In this folder you will find a sample application that uses a JWT token to authorize operations in microservices. 

The application works with an external JWT to circulate outside of the system, in the client applications and internal JWT to circulate between applications and services. 

# Components of a microservices architecture

## API Gateway

Manages the externally available endpoints and forwards them to the application endpoints. Works the loadbalancing and proxying of requests between the different applications possible.

In our scenario, a single application process takes the role of an API Gateway as a single point of entry running on port 80 (or 8080).

## Authentication Server

Can be implemented using a OAuth architecture. In our scenario it is implemented by the Authentication to JWT filter in the single application process. It produces an external JWT with username and role information.

## Authorization Server 

Where the security adapters for the microservices convert the external JWT. May be implemented together with the Application, or follow a OAuth architecture (internal or external to the system).

## Applications

They provide the externally available endpoints. May be implemented together with an API Gateway. In this scenario, the application consists of the controller components, and all the layers beneeth them. More than one application is possible in a system, if the API Gateway proxies the requests accordingly.

Handles the authorization (access-control) to each request based on username and roles carried by a JWT provided by the Authentication Server. 