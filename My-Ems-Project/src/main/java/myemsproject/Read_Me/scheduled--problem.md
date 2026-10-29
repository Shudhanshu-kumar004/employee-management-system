Scheduled Jobs — Short Notes
Problem I faced

In my Employee Management System, the employee's status needed to change automatically based on leave dates.

For example:

Leave starts today → ON_LEAVE
Leave ends → ACTIVE
Employee joins today → ACTIVE

Initially, I was checking these conditions only inside an API method. The problem was that the API is not called automatically every day, so the status would not change by itself.

Solution

I used Spring Boot Scheduler with @Scheduled.

@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")

This runs the method automatically every day at midnight.

I also used:

@Transactional

because my Users entity was loaded lazily and I got:

LazyInitializationException: no session

@Transactional keeps the Hibernate session/transaction available while the scheduled method executes.

What is @Scheduled?

@Scheduled tells Spring:

"Run this method automatically according to the specified schedule."

It does not require an API request or controller call.

Example:

@Scheduled(fixedRate = 60000)
public void myTask() {
System.out.println("Running...");
}

This runs approximately every 60 seconds.

Common ways to use it

Every fixed interval:

@Scheduled(fixedRate = 60000)

→ Run every 60 seconds.

Cron schedule:

@Scheduled(cron = "0 0 0 * * *")

→ Run every day at midnight.

Important

To enable scheduling, add:

@EnableScheduling
@SpringBootApplication
public class MyApplication {
}
My final flow
Spring Scheduler
↓
@Scheduled method
↓
Check leave dates
↓
Update Users status
↓
usersRepository.save()
↓
MySQL

In short: @Scheduled is useful when backend tasks need to happen automatically at a fixed time or interval without someone calling an API.

---------------------------------------------------------------------------------



LazyInitializationException — Short Notes

What is the error?
LazyInitializationException:
Could not initialize proxy [Users#10] - no session

This happened in your scheduler when you accessed:

users.getJoiningDate()
Why does it happen?

Your Leaves entity probably has a relationship like:

@ManyToOne(fetch = FetchType.LAZY)
private Users users;

LAZY means:

"Don't load the Users object immediately. Load it only when I actually need it."

So when you do:

Users users = leave.getUsers();

Hibernate may give you a proxy object, not the actual Users data.

Later:

users.getJoiningDate();

Hibernate tries to fetch the real user from the database.

But the Hibernate Session is already closed.

Therefore:

Users Proxy
↓
getJoiningDate()
↓
Hibernate tries to load Users
↓
Session is closed ❌
↓
LazyInitializationException
How did we resolve it?

We used:

@Transactional

on the scheduled method:

@Transactional
@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
public void scheduledLeaves() {
// ...
}
What does @Transactional do?

It keeps a database transaction/Hibernate session active while the method executes.

So:

@Scheduled
↓
@Transactional
↓
Hibernate Session OPEN
↓
Load Leaves
↓
Access Users
↓
getJoiningDate()
↓
Hibernate can load Users ✅
↓
Update Users
↓
Transaction completes
Other ways to resolve it
1. @Transactional — recommended for your case
   @Transactional

Good for your scheduled database operation.

2. Change LAZY to EAGER
   @ManyToOne(fetch = FetchType.EAGER)

This loads Users immediately.

But don't change to EAGER just to hide the error. It can cause unnecessary database loading.

3. Fetch the relationship using a query

For example, using JOIN FETCH:

@Query("""
SELECT l FROM Leaves l
JOIN FETCH l.users
WHERE l.status = :status
""")
List<Leaves> findByStatusWithUsers(LeaveStatus status);

This explicitly tells Hibernate to load Leaves and their Users together.

Important terms to remember
Term	Meaning
LAZY	Load related data only when needed
EAGER	Load related data immediately
Hibernate Session	Connection/context Hibernate uses to load entities
Proxy	Placeholder object Hibernate creates for lazy data
LazyInitializationException	Tried to access lazy data after the Hibernate session was closed
@Transactional	Keeps the transaction/session active while the method runs
One-line memory trick

LazyInitializationException = I tried to access lazy-loaded data, but Hibernate's session was already closed.