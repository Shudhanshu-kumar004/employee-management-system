# Search + Status Filter

## Problem

Initially, I had two separate APIs:

- `GET /search/users?keyword=...`
- `GET /filter/users?status=...`

The issue was that both APIs worked independently. Whenever I searched for a user and then changed the status filter, the search results were replaced by all employees matching the selected status instead of applying both filters together.

## Approach

Instead of handling this combination in the frontend, I merged both functionalities into a single backend endpoint that accepts optional `keyword` and `status` parameters. (because of i have used : @RequestParam(required = false)) This allows the database to apply both conditions in one query.

## Controller

```java
@GetMapping("/filter/search/users")
public List<UsersDto> filterSearch(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String status) {

    return emsServiceImpl.filterSearch(keyword, status);
}
```

## Repository

```java
@Query(value = """
SELECT * FROM users u
WHERE (
        :keyword IS NULL
        OR :keyword = ''
        OR LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.email_id) LIKE LOWER(CONCAT('%', :keyword, '%'))
      )
AND (
        :status IS NULL
        OR :status = 'ALL EMPLOYEES'
        OR u.status = :status
      )
""", nativeQuery = true)
List<Users> filterSearch(
        @Param("keyword") String keyword,
        @Param("status") String status
);
```

## Result

- `?keyword=kumar` → Search only
- `?status=ACTIVE` → Status filter only
- `?keyword=kumar&status=ACTIVE` → Search and status applied together
- No parameters → All employees

## Key Learning

For multiple filters that should work together, use a single endpoint with optional query parameters instead of creating separate endpoints that override each other's results.