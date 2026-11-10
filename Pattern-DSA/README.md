# Pattern DSA (Java + Maven)

A **practice-first** repository for interview DSA, organized by patterns rather than random question numbers. It is intentionally a **plain Maven project**, not Spring Boot: no server, no unnecessary framework boot time, and trivial JUnit test execution.

## Quick start (Windows / VS Code)

Prerequisites: **JDK 17+**, Maven 3.9+, Python 3.9+ (Python is only for progress logging).

```powershell
cd F:\workspace\Pattern-DSA
mvn clean test
mvn compile exec:java
python scripts/progress.py summary
```

> Extract the ZIP to your desired workspace. If you do not use Python, edit `tracking/problems.csv` and `tracking/attempts.csv` manually.

## Repository map

```text
Pattern-DSA/
├── pom.xml                         # one Maven build
├── src/main/java/org/nishant/dsa/
│   ├── App.java                     # pure Java demo
│   ├── arrayshashing/               # arrays, hashing, prefix sum
│   ├── binarysearch/
│   ├── binarytree/
│   ├── intervals/
│   ├── linkedlist/
│   ├── majority/
│   ├── slidingwindow/
│   ├── stack/
│   └── twopointers/
├── src/test/java/org/nishant/dsa/  # JUnit regression tests
├── notes/                           # concise patterns + approach logs
├── tracking/problems.csv            # question queue and status
├── tracking/attempts.csv            # every attempt, time & complexity
├── scripts/progress.py              # show progress / log attempt
└── .github/workflows/build.yml      # GitHub Actions Maven tests
```

## Working with multiple approaches

Each implemented problem uses a descriptive class (`MajorityElement`, `MergeIntervals`, etc.) with **separately named public static methods**, such as `bruteForce`, `hashMap`, `optimal`, `binarySearch`, `voting`, `withExtraSpace` or `inPlace`. This lets you:

1. Understand constraints and edge cases **before** solving.
2. Attempt an easy **brute-force** solution first.
3. Implement a more optimal approach without deleting the first.
4. Add tests asserting that both produce the same result.
5. Record **Big-O time and space** and **real practice minutes** separately.
6. Commit your attempt to GitHub and revise later.

> **Time complexity** (`O(n)`) describes growth as input increases; **execution time** (ms) and **study time** (minutes) are different. Both can be tracked, but do not infer Big-O from one execution.

## Tracker commands

```powershell
python scripts/progress.py summary
python scripts/progress.py list --pattern intervals
python scripts/progress.py log --id 56 --approach merge-sort --minutes 35 --time "O(n log n)" --space "O(n)" --status SOLVED --notes "Missed touching endpoints"
python scripts/progress.py log --id 169 --approach voting --minutes 20 --time "O(n)" --space "O(1)" --status REVIEW --notes "Recall cancellation proof"
```

The logger updates `tracking/problems.csv` (latest status) and **appends** to `tracking/attempts.csv` (history), so repeated attempts are visible. `TODO`, `ATTEMPTED`, `SOLVED`, `REVIEW` are valid statuses. **All questions are initialized TODO**: your progress is yours, not preclaimed by the template.

## Suggested per-problem workflow

- Read the LeetCode question; record examples in `notes/<pattern>.md` or `notes/problems/LCxxxx.md`.
- Write **why this pattern?**, invariants, and edge cases.
- Code brute-force, then optimal approaches. Keep commented Java in your repo.
- Run `mvn test` (or specific test: `mvn -Dtest=IntervalTest test`).
- Log minutes / complexity / trouble points.
- Commit: `git add . && git commit -m "solve(intervals): LC56 merge brute and sweep"`.
- Revisit on day 1, 3 and 7; change tracker to REVIEW when needed.

## Publish to GitHub

Create a **new empty GitHub repository** named `pattern-dsa` (do not initialize README/license on GitHub if pushing this complete folder), then from the extracted project:

```powershell
git init
git add .
git commit -m "chore: initialize pattern-wise DSA Maven practice"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/pattern-dsa.git
git push -u origin main
```

For future work: `git add .; git commit -m "solve: LC209 sliding window"; git push`. Authentication happens through your Git credentials/browser (or SSH), not in the ZIP.

## Java version & imports

- Java **17** compiler release (runs on JDK 17/21/22).
- Most solutions are **standalone pure functions** in `org.nishant.dsa.*`.
- Some questions have LeetCode-specific wrappers; use these method implementations inside `class Solution` when submitting.
- JUnit 5 dependencies download when Maven has internet access; a separate no-dependency `javac`/`java` compilation check is also possible.

## Notion notes (existing)

The repo intentionally links to your Notion reference, but does **not** silently overwrite it:

- [Linked List](https://app.notion.com/p/414d9598f0ab81de94b8d23b33fc6266)
- [Binary Search](https://app.notion.com/p/414d9598f0ab814a9d26cbaceb785309)
- [Sliding Window](https://app.notion.com/p/414d9598f0ab819aa79cdb96dd1b7afa)
- [Arrays & Hashing](https://app.notion.com/p/414d9598f0ab817084f2cbe9929f31b0)
- [Two Pointers](https://app.notion.com/p/414d9598f0ab81b19530f488e5d2b63e)
- [Stack](https://app.notion.com/p/414d9598f0ab81d7be1fe124f6cf8728)
- [Majority](https://app.notion.com/p/414d9598f0ab81e6a5b3dc1d9dcbbe6e)
- [Intervals](https://app.notion.com/p/414d9598f0ab81f4ba54e4b1c8cca170)
