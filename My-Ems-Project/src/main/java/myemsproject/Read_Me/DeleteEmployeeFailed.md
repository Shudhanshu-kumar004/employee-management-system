Problem Faced: Employee Delete Failed (Foreign Key Constraint)

While implementing the Delete Employee feature in my Spring Boot + Angular EMS project, I got a MySQL error saying:

Cannot delete or update a parent row: a foreign key constraint fails

This happened because the Users table was the parent, while Leaves and Resignations were child tables linked through the users_id foreign key. I was trying to delete the user before deleting the related child records.

How I Solved It

I solved the issue by using JPA Cascade Delete (CascadeType.ALL) along with @Transactional.

CascadeType.ALL

Automatically performs operations (delete, update, save) on child entities when the parent entity is affected.

When a user is deleted, Hibernate first deletes the related Leaves and Resignations records, then deletes the Users record.

orphanRemoval = true

Removes child records from the database if they are removed from the parent's collection.

Helps keep the database clean by preventing orphan records.

@Transactional

Executes all database operations inside a single transaction.

If any delete operation fails, the entire transaction is rolled back, keeping the database consistent.

Result

After applying Cascade Delete and @Transactional, deleting an employee worked successfully without any foreign key constraint errors, and all related records were removed automatically.