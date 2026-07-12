# Spring REST Exercise 6: JWT Authentication Service

This exercise creates an authentication REST service that accepts user credentials via HTTP Basic Auth and returns a signed JWT (JSON Web Token). This is Step 1 of a JWT-based authentication flow — the client gets a token here, then uses it in all subsequent requests.

---

## Files in this Folder

```
Exercise-6/
├── pom.xml
├── src/main/resources/
│   └── application.properties          ← port 8090, credentials, JWT secret
├── src/main/java/com/cognizant/springlearn/
│   ├── SpringLearnApplication.java     ← main class
│   ├── security/
│   │   ├── SecurityConfig.java         ← Spring Security config, permits /authenticate
│   │   └── JwtUtil.java                ← generates signed JWT tokens
│   └── controller/
│       └── AuthenticationController.java ← reads header, decodes creds, returns token
└── screenshots/
```

---

## How JWT Authentication Works (the full picture)

JWT stands for **JSON Web Token**. It is a compact, self-contained token that contains claims (user info + expiry) and is digitally signed so the server can verify it wasn't tampered with.

A JWT looks like this:
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTcwMzc5NDc0LCJleHAiOjE1NzAzODA2NzR9.t3LRvlCV-hwKfoqZYlaVQqEUiBloWcWn0ft3tgv0dL0
```

It has three parts separated by dots — `HEADER.PAYLOAD.SIGNATURE`:

| Part | Contains | Example (decoded) |
|------|----------|-------------------|
| Header | Algorithm used | `{ "alg": "HS256" }` |
| Payload | Claims (user, expiry) | `{ "sub": "user", "iat": 1570379474, "exp": 1570380674 }` |
| Signature | HMAC of header+payload | Verifies token wasn't modified |

All three parts are Base64-encoded. The signature is created using the secret key only the server knows — so if someone tampers with the payload, the signature won't match.

---

## Three Steps Implemented in This Exercise

### Step 1: Authentication Controller + SecurityConfig

`SecurityConfig` configures Spring Security to:
- Allow `/authenticate` without authentication (so clients can call it to get a token)
- Enable HTTP Basic Auth (so the `Authorization: Basic ...` header is accepted)

`AuthenticationController` maps `GET /authenticate` and reads the `Authorization` header from the request.

### Step 2: Read and Decode the Authorization Header

When `curl -u user:pwd http://localhost:8090/authenticate` is run, curl automatically creates this header:
```
Authorization: Basic dXNlcjpwd2Q=
```
`dXNlcjpwd2Q=` is the Base64 encoding of `user:pwd`.

The controller decodes it:
```java
String base64Credentials = authHeader.substring("Basic ".length()); // strip "Basic "
byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
String credentials = new String(decodedBytes);   // "user:pwd"
String username = credentials.split(":")[0];     // "user"
String password = credentials.split(":")[1];     // "pwd"
```

### Step 3: Generate Token Based on the Username

`JwtUtil.generateToken(username)` builds and signs the JWT:
```java
Jwts.builder()
    .setSubject(username)          // who the token is for
    .setIssuedAt(new Date())       // when it was created
    .setExpiration(new Date(...))  // when it expires (20 mins)
    .signWith(signingKey, HS256)   // sign with HMAC-SHA256
    .compact();                    // produce the token string
```

The token is returned as JSON:
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWI..." }
```

---

## How to Run

### Step 1: Folder structure

Create these folders first:
- `src/main/java/com/cognizant/springlearn/controller`
- `src/main/java/com/cognizant/springlearn/security`
- `src/main/resources`

Place files in the correct locations as shown in the folder structure above.

### Step 2: Open in VS Code

File → Open Folder → select `Exercise-6`. Wait for **"Java: Ready"** in the status bar. This downloads Spring Security + JJWT jars — may take a few minutes first time.

### Step 3: Run

Open terminal (`Ctrl + ~`) inside the `Exercise-6` folder:
```
mvn spring-boot:run
```

Confirm startup with:
```
Tomcat started on port 8090
```

### Step 4: Test with curl

Open a **new terminal** (don't stop the running app) and run:

**On Git Bash / Linux terminal:**
```
curl -s -u user:pwd http://localhost:8090/authenticate
```

**On PowerShell:**
```
curl -s -u user:pwd http://localhost:8090/authenticate
```
If PowerShell's `curl` doesn't support `-u`, use this instead:
```
$headers = @{ Authorization = "Basic " + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("user:pwd")) }
Invoke-RestMethod -Uri http://localhost:8090/authenticate -Headers $headers
```

**Expected response:**
```json
{"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0Ij..."}
```

### Step 5: Test with Postman

1. Open Postman → new GET request → URL: `http://localhost:8090/authenticate`
2. Click **Authorization** tab → Type: **Basic Auth**
3. Username: `user`, Password: `pwd`
4. Click **Send**
5. Response body shows the JWT token

Click the **Headers** tab in the response to see `Content-Type: application/json`.

### Step 6: Verify the JWT (optional but useful)

Copy the token value and go to **https://jwt.io**. Paste the token in the "Encoded" box on the left. The right side shows the decoded header and payload — you can see `sub: user`, `iat`, and `exp` values.

---

## Output

### Terminal — Spring Boot startup

![Terminal showing Tomcat started on port 8090](screenshots/jwt_ex6_terminal.png)

### curl response — JWT token returned

![curl command output showing the JSON token response](screenshots/jwt_ex6_curl.png)


### Postman — Authorization tab setup

![Postman Basic Auth configuration with user and pwd](screenshots/jwt_ex6_postman_auth.png)



---

## Folder Structure

```text
WEEK 3/
└── Spring REST using Spring Boot 3/
    └── Exercise-6/
        ├── pom.xml
        ├── README.md
        ├── src/
        │   └── main/
        │       ├── java/com/cognizant/springlearn/
        │       │   ├── SpringLearnApplication.java
        │       │   ├── security/
        │       │   │   ├── SecurityConfig.java
        │       │   │   └── JwtUtil.java
        │       │   └── controller/
        │       │       └── AuthenticationController.java
        │       └── resources/
        │           └── application.properties
        └── screenshots/
            ├── jwt_ex6_terminal.png
            ├── jwt_ex6_curl.png
            └── jwt_ex6_postman_auth.png
```

---

## What I Learned

- JWT is stateless — the server doesn't store sessions. Instead, the client stores the token and sends it with every request. The server only needs the secret key to verify the token's signature.
- `curl -u user:pwd` automatically creates the `Authorization: Basic <base64>` header. The Base64 encoding of `user:pwd` is `dXNlcjpwd2Q=`. We decode it in the controller to extract the username.
- `SecurityConfig` must explicitly `permitAll()` the `/authenticate` endpoint, otherwise Spring Security would block the request before it even reaches the controller (since the user isn't authenticated yet — they're trying to get a token).
- JWT has three parts: header (algorithm), payload (claims like subject and expiry), and signature. The signature is what makes it tamper-proof — only the server knows the secret key used to sign it.
- `jjwt-api`, `jjwt-impl`, and `jjwt-jackson` are three separate JARs — the API is the interface, impl is the engine, jackson handles JSON serialization of claims. All three are needed.
- The secret key in `application.properties` must be long enough for the HS256 algorithm (minimum 256 bits = 32 characters). Short keys cause a `WeakKeyException` at runtime.
- In a production system, passwords would be validated against a database (using `UserDetailsService`) before generating the token. Here, Spring Security's HTTP Basic Auth handles credential validation using the in-memory user from `application.properties`.
