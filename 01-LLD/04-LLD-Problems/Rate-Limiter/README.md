
# Fixed Window Rate Limiter

## Requirement
Allow a maximum of 5 requests per user per 60-second window.

## Algorithm
1. Get the current time.
2. Calculate the fixed window start.
3. Find or create the user's Window.
4. Synchronize on the user's Window.
5. Reset the counter if the window changed.
6. Reject if the maximum request count was reached.
7. Otherwise increment the counter and allow.

## Data structure
ConcurrentHashMap<String, Window>

Window contains:
- windowStart
- count

## Complexity
- Time: O(1) average per request
- Space: O(U), where U is the number of stored users

## Limitations
- Boundary burst problem
- In-memory only, not distributed
- Old user entries need cleanup

## Running tests
mvn clean test
