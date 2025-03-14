# Overall approach

I have put an emphasis in implementing the core logic within the domain model, encapsulating behavior alongside data. This 
ensures business rules are explicit and self-contained.

Also, I have followed the Ports & Adapters Architecture (aka Hexagonal), where the domain is decoupled from frameworks 
and infrastructure.

Additionally and also for simplicity, I have only used a framework exclusively for what I needed: exposing the REST
endpoints. I used Ktor for this purpose.

For time efficiency, I have prioritised testing the domain which I consider more important than the REST API,
which is "just an input port to the domain".

Please, also see _Improvements_ section bellow.


# URL shortening approach

I have avoided a URL hashing strategy since it's prone to collisions. Instead, a short URL is generated as follows:

1. Each short URL has a max length of 6 alphanumeric characters (`a-z`, `0-9`), which allows a total of 34 different characters
2. A numeric ID is acquired for the URL
3. The ID is transformed to Base34 to get the short version of it - e.g., ID `100.000` results in `255s`.

This approach would allow a maximum of `34^6` (`2.176.782.336`) URLs.


# How to run it

Requirements:
  - Java 21 or greater

Running tests suite:

```shell
./gradlew test
```

Running the app:

```shell
./gradlew :run
```

## API

### Shorten URL endpoint

Request:

```shell
curl \
  --location 'localhost:8080/' \
  --header 'Content-Type: application/json' \
  --data '{
    "url": "http://example.com"
  }'
```

Response:

```json
{
  "shortUrl": "http://localhost:8080/255t"
}
```

### Retrieve original URL endpoint

Request:

```shell
curl --location 'localhost:8080/255t'
```

Response:

```text
301 - Moved permatently
http://example.com
```

# Improvements

Potential improvements should this be a real project:

- Structure
    - Using an actual K/V store instead of in-memory
    - Usage of ArchUnit to enforce Ports&Adapters architecture, dependency direction and prevent illegal imports, among other
- Tests
    - Integration tests with Testcontainers, using the actual DB used in production
    - Creating an integration test for the REST API
- Other
    - Sanitising input
    - Security filters to prevent attacks
    - Proper error responses (JSON) instead of empty body
    - Metrics
