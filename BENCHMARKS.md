# Benchmark Results

The experimental implementation was compared with a standard Selection Sort implementation using identical input arrays.

## Example

Input

```
[7, 3, 1, 5, 2, 8, 4, 6]
```

Results

| Algorithm | Comparisons |
|-----------|------------:|
| Standard Selection Sort | 28 |
| Experimental Version | 20 |

Reduction

- 8 fewer comparisons
- ~28.6% reduction

---

## Observations

### Scenario 1

The early-exit condition never occurs.

Result:

- Identical behavior
- Identical comparison count

---

### Scenario 2

The early-exit condition occurs frequently.

Result:

- Noticeably fewer comparisons
- Same sorted output
- Lower execution time

---

### Scenario 3

Mixed input distributions.

Result:

- Partial comparison reduction
- Performance depends on the arrangement of values

---

The benchmark included in the Java source reproduces these measurements.
