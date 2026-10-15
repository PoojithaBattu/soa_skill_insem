# Tourist Record API with Token Validation

A Spring Boot 3 / Java 17 Maven project for the lab task: validate the `Token` header before allowing a DELETE request to remove a tourist record.

## Requirements
- JDK 17 or later
- Spring Tool Suite (STS) or Eclipse with Maven support

## Import into STS
1. Extract `TouristTokenValidation.zip`.
2. Open STS.
3. Select **File → Import → Maven → Existing Maven Projects**.
4. Browse to the extracted `TouristTokenValidation` folder.
5. Select `pom.xml`, then click **Finish**.
6. Wait for Maven dependencies to download.
7. Right-click the project → **Run As → Spring Boot App**.

The application runs at `http://localhost:8080`.

## API endpoints

### List tourists
`GET http://localhost:8080/api/tourists`

### Add a tourist
`POST http://localhost:8080/api/tourists`
Header: `Content-Type: application/json`

Body:
```json
{
  "name": "Haritha",
  "destination": "Vizag"
}
```

### Delete a tourist (valid token)
`DELETE http://localhost:8080/api/tourists/1`
Header:
`Token: tourist-secret-token`

Expected response: HTTP 200
```json
{"message":"Tourist record deleted successfully"}
```

### Delete with missing/invalid token
Send DELETE without the `Token` header, or use a different value.

Expected response: HTTP 500
```text
Deletion Failed: Token Error
```

> The HTTP 500 status is included to match the wording in the supplied lab question. In production APIs, an invalid/missing token should normally return HTTP 401 or 403 instead.

## Test with Postman
1. Start the app.
2. Send `GET http://localhost:8080/api/tourists` to see seeded records.
3. Send `DELETE http://localhost:8080/api/tourists/1` with header `Token: tourist-secret-token`.
4. Try again without the header to see the token error.

## Token validation implementation
`TokenValidationInterceptor.java` reads the custom `Token` request header before controller execution. If the request is DELETE and the token is not `tourist-secret-token`, it stops the request and returns the lab-specified error. The interceptor is registered for `/api/tourists/**`.

This is a classroom demo token, not production-grade authentication.
