# Adding your next solution

1. Pick TODO problem from `python scripts/progress.py list --pattern intervals`.
2. Create `src/main/java/org/nishant/dsa/<pattern>/ProblemName.java`.
3. Keep multiple descriptive methods (`bruteForce`, `optimized`, `hashMap`, `inPlace`) for different approaches.
4. Write tests in `src/test/java/org/nishant/dsa/`. Test edge cases AND compare approaches.
5. Write notes in `notes/problems/LC####.md`.
6. `mvn clean test`, then `python scripts/progress.py log --id #### ...`.
7. Commit only after running tests. Track reviews separately, not as new solutions.

## Avoid fake metrics

Use Big-O complexity and study duration. A reported runtime-ms is an observed machine measurement only; small runs do not prove asymptotic complexity. The tracker does not run benchmarks for you.
