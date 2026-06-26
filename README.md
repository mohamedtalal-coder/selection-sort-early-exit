# Optimized Selection Sort (Experimental)

An experimental variation of Selection Sort that introduces an early-exit condition for integer arrays.

Instead of always scanning the entire unsorted portion to find the next minimum, the algorithm stops searching once it encounters `previousMinimum + 1`.

Because there are no integers between `N` and `N + 1`, that value is guaranteed to be the next minimum, making the remaining comparisons unnecessary.

> This is a domain-specific optimization and is not intended to replace the standard algorithm.

---

## Motivation

While studying sorting algorithms, I wondered whether Selection Sort's complete scan on every iteration was always necessary.

For integer arrays containing consecutive values, an observation emerged:

If the previously selected minimum is `N`, and during the next scan the current minimum becomes `N + 1`, the search can terminate immediately.

This preserves the algorithm's in-place nature while reducing unnecessary comparisons for certain input distributions.

---

## Characteristics

| Property | Standard | Experimental |
|----------|----------|--------------|
| Worst-case time | O(n²) | O(n²) |
| Best-case time | Θ(n²) | O(n)\* |
| Space | O(1) | O(1) |
| Comparisons | n(n−1)/2 | ≤ n(n−1)/2 |
| Adaptive | No | Under stated assumptions |

\*Under the assumptions described below.

---

## Assumptions

This optimization relies on properties of integer data.

- Integer values
- Consecutive values occur reasonably often
- Domain knowledge about the input

If these assumptions are not satisfied, the algorithm naturally falls back to behaving like standard Selection Sort.

---

## Benchmark

See **BENCHMARKS.md** for example comparison results.

---

## Repository Structure

```
OptimizedSelectionSort.java
README.md
BENCHMARKS.md
images/
```

---

## Future Ideas

Potential directions worth exploring include:

- More rigorous benchmarking
- Additional input distributions
- Formal proof of correctness
- Support for generic comparable types where similar guarantees exist

Contributions, feedback, and discussion are welcome.
