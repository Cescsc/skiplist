# Skip List

A straightforward Skip List implementation in Java — a school project completed for the KTH course DD1327 (Fundamentals of Computer Science) in Spring 2023.

This repository contains a typical implementation of a probabilistic skip list data structure, demonstrating the standard operations and concepts used in the structure.

## What it is

A skip list is a probabilistic, layered linked structure that provides efficient average-case search, insert and delete operations (average O(log n)). It uses multiple forward pointer levels per node and random promotion of nodes to higher levels to achieve balanced-like performance without strict rebalancing.

## Implemented features

- Insert
- Search / Contains
- Delete
- Configurable maximum levels and promotion probability
- Clear and commented code intended for learning and demonstration

## Complexity

- Average: O(log n) for search, insertion and deletion
- Worst-case: O(n)

## Usage

This project is a small library-style implementation (no packaged CLI). You can compile and run it with your usual Java toolchain or import it into an IDE.

Example compilation (from the repository root):

```bash
# compile all .java files into the out/ directory
javac -d out $(find src -name "*.java")

# run a main class if provided (replace com.example.Main with the actual main class)
java -cp out com.example.Main
```

Example (typical) usage in code:

```java
// create a skip list for integers
SkipList<Integer> list = new SkipList<>();

// insert values
list.insert(10);
list.insert(5);

// check membership
boolean hasTen = list.contains(10);

// delete
list.delete(5);
```

Adjust the API calls above to match the concrete class/method names in src/.

## Project structure

- src/ — Java source files (skip list implementation and supporting classes)
- README.md — this file

## Notes on implementation

- The implementation follows the common approach using an array of forward pointers per node.
- Node promotion is decided by a random coin flip (or configurable probability), so behavior is probabilistic and meant for educational purposes.
- The code includes comments to explain key design decisions and should be straightforward to extend or adapt.

## Want to improve it?

Suggestions:
- Add unit tests (JUnit) and example programs demonstrating performance vs. Java's TreeSet/SkipListMap
- Add benchmarks and visualizations of level distributions
- Make the implementation generic with comparators (if not already)

## License & contact

This is a school project — check with me for reuse. If you'd like to see more or give feedback, open an issue or reach out via my GitHub profile: https://github.com/Cescsc
