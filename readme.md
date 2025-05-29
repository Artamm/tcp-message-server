# **TCP Client-Server Application**

This project implements a **TCP-based client and server** using **Java sockets API**. The **server** maintains a
shared data that can be modified by multiple clients.

## **Commands**

 Commands (case insensitive) that can be sent to the server:

|Command|Description|
|---|---|
|`LIST`|Retrieves the current server state|
|`ADD key value`|Adds a new key-value pair|
|`SET key value`|Updates an existing key's value|
|`DELETE key`|Removes a key from the server|
|`END`|Disconnects the client from the server|

### Run 
Build project with `mvn clean package install`, run in different terminals:
```bash
java -jar target/message-tcp-server.jar
```
```bash
java -jar target/message-tcp-client.jar
```
