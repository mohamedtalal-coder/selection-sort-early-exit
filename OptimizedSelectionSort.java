public class OptimizedSelectionSort {

    // =========================================================================
    // STANDARD SELECTION SORT
    // Classic implementation — always scans the full unsorted portion.
    // No early exit, no domain knowledge used.
    // Always performs exactly n*(n-1)/2 comparisons regardless of input.
    // =========================================================================
    static int standardSort(int[] arr) {
        int n = arr.length;
        int comparisons = 0;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Inner loop always runs to the end — no way to stop early
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap minimum into position
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }

        return comparisons;
    }

    // =========================================================================
    // OPTIMIZED SELECTION SORT (your idea)
    // Same as standard but adds one check inside the inner loop:
    // if the new minimum equals lastMinimum+1, it is guaranteed to be
    // the next smallest integer — no smaller value can exist between two
    // consecutive integers — so we break early and skip the rest of the scan.
    // =========================================================================
    static int optimizedSort(int[] arr) {
        int n = arr.length;
        int comparisons = 0;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // arr[i-1] is the value we placed in the previous iteration.
            // We need it to check if the current candidate is exactly +1 of it.
            int lastMinimum = i > 0 ? arr[i - 1] : Integer.MIN_VALUE;

            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;

                    // ── EARLY EXIT ────────────────────────────────────────────
                    // Found a value that is exactly lastMinimum+1.
                    // Since we are working with integers, nothing smaller
                    // can exist between lastMinimum and lastMinimum+1,
                    // so this must be the next minimum. Stop scanning.
                    if (lastMinimum != Integer.MIN_VALUE && arr[minIndex] == lastMinimum + 1) {
                        break;
                    }
                }
            }

            // Swap minimum into position
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }

        return comparisons;
    }

    // =========================================================================
    // RUN — runs BOTH algorithms on the same array and prints a side-by-side
    // comparison of time and comparisons for each
    // =========================================================================
    static void run(String label, String category, int[] original) {
        int n           = original.length;
        int maxPossible = n * (n - 1) / 2; // theoretical max comparisons

        // ── Standard sort ────────────────────────────────────────────────────
        int[] arrStd      = original.clone();  // clone so original is untouched
        long  startStd    = System.nanoTime();
        int   cmpStd      = standardSort(arrStd);
        long  timeStd     = System.nanoTime() - startStd;

        // ── Optimized sort ───────────────────────────────────────────────────
        int[] arrOpt      = original.clone();  // fresh clone for fair comparison
        long  startOpt    = System.nanoTime();
        int   cmpOpt      = optimizedSort(arrOpt);
        long  timeOpt     = System.nanoTime() - startOpt;

        // ── Derived stats ────────────────────────────────────────────────────
        int    savedCmp   = cmpStd - cmpOpt;                     // comparisons saved
        double savedPct   = (savedCmp * 100.0) / maxPossible;    // % of total work saved
        long   savedTime  = timeStd - timeOpt;                   // nanoseconds saved

        // ── Print ────────────────────────────────────────────────────────────
        System.out.println("┌─ " + label + "  [" + category + "]");
        System.out.print  ("│  Input:                    "); printArray(original);
        System.out.print  ("│  Output:                   "); printArray(arrOpt);
        System.out.println("│");
        System.out.printf ("│  %-26s  Comparisons    Time%n",          "");
        System.out.printf ("│  %-26s  %5d / %-5d  %6d ns%n",
                           "Standard sort:",   cmpStd, maxPossible, timeStd);
        System.out.printf ("│  %-26s  %5d / %-5d  %6d ns%n",
                           "Optimized sort:",  cmpOpt, maxPossible, timeOpt);
        System.out.println("│");

        if (savedCmp > 0) {
            System.out.printf("│  Saved: %d comparisons (%.1f%% less work)  |  %d ns faster%n",
                              savedCmp, savedPct, savedTime);
        } else {
            // savedCmp == 0 means early exit never fired — identical behaviour
            System.out.println("│  No savings — early exit never triggered (worst case for optimization)");
        }

        System.out.println("└" + "─".repeat(60));
        System.out.println();
    }

    static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    // =========================================================================
    // MAIN
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("=".repeat(62));
        System.out.println("  STANDARD vs OPTIMIZED SELECTION SORT — Full Comparison");
        System.out.println("=".repeat(62));
        System.out.println();

        // ── NO CHANGE (worst case for the optimization) ───────────────────────
        // Early exit never fires because last_minimum+1 is always buried at
        // the far end of the unsorted portion. Both algorithms do identical work.
        // Comparison counts will match exactly — 0 comparisons saved.
        System.out.println("── NO CHANGE  (worst case — early exit never fires) ─────");
        System.out.println();

        run("Reversed dense integers",
            "NO CHANGE",
            new int[]{5, 4, 3, 2, 1});

        run("Next minimum buried at the end",
            "NO CHANGE",
            new int[]{1, 5, 4, 3, 2});

        run("Larger reversed array",
            "NO CHANGE",
            new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1});


        // ── BEST CASE (maximum savings) ───────────────────────────────────────
        // last_minimum+1 is encountered near the start of every inner scan,
        // so the optimized version exits the inner loop very early each time.
        // This is where the gap between the two algorithms is largest.
        System.out.println("── BEST CASE  (early exit fires on nearly every iteration)");
        System.out.println();

        run("Already sorted",
            "BEST CASE",
            new int[]{1, 2, 3, 4, 5});

        run("Next minimum always near the front",
            "BEST CASE",
            new int[]{2, 1, 3, 4, 5});

        run("Larger already-sorted array",
            "BEST CASE",
            new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});


        // ── SLIGHT CHANGE (average / partial savings) ─────────────────────────
        // Early exit fires on some iterations but not all.
        // The optimized version does less work, but not dramatically so.
        // This is the realistic everyday scenario for random integer arrays.
        System.out.println("── SLIGHT CHANGE  (early exit fires on some iterations) ─");
        System.out.println();

        run("Mixed order",
            "SLIGHT CHANGE",
            new int[]{3, 1, 4, 2, 5});

        run("Integers with small gaps",
            "SLIGHT CHANGE",
            new int[]{10, 12, 11, 13, 14});

        run("Partially sorted",
            "SLIGHT CHANGE",
            new int[]{1, 3, 2, 6, 4, 5});

        run("Larger mixed array",
            "SLIGHT CHANGE",
            new int[]{7, 3, 1, 5, 2, 8, 4, 6});
    }
}