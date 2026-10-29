//prompt:--
Convert my work into **short project notes** for a `docs/*.md` file.

Keep it concise (100–200 words) and use this exact format:

# [Feature Name]

## Goal

(1–2 lines)

## Files Changed

* Controller
* Service
* Repository
* Frontend (if applicable)

## Key Logic

(Important query, API, or code pattern in 1–3 lines.)

## Problems Faced

* Bug 1
* Bug 2

## Key Learning

(2–4 bullet points about the main concepts learned.)

## If I build this again

1. Step 1
2. Step 2
3. Step 3
4. Step 4

Use only the information I provide. Don't add unnecessary theory or long explanations.


-------------------------------------------------------------------------------------------------------------------





@RequestParam is used in a Controller to receive values from an HTTP request (URL query parameters or form data).

@Param is used in a Repository to bind a Java method parameter to a named parameter inside a JPQL or Native SQL query.


# Search API – Repository Return Type Fix

## Goal

Implement user search by name or email using a native SQL query.

## Files Changed

* Repository
* Service
* Controller

## Key Logic

Used a native query with `LOWER()` and `LIKE CONCAT('%', :keyword, '%')` for case-insensitive partial search.

## Problems Faced

* Repository returned `List<UsersDto>` instead of `List<Users>`.
* Service mapper expected `Users` Entity, causing a type mismatch.

## Key Learning

* Controller and Service return `UsersDto`.
* Repository should return the `Users` Entity when Service performs DTO mapping.
* `:keyword` is a named parameter replaced by Spring Data JPA.
* `CONCAT('%', keyword, '%')` creates a partial-match pattern like `%shu%`.

## If I build this again

1. Write the repository query returning `List<Users>`.
2. Map `Users → UsersDto` in Service.
3. Keep Controller returning `List<UsersDto>`.
4. Test the API in Postman before connecting the frontend.
