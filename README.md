# Sorting Algorithms Visualizer & Comparator

A JavaFX application that visualizes and benchmarks six classical sorting algorithms in real time, with Python-powered data analysis and chart generation.

---

## Features

### Sorting Visualization
- Animate up to **6 algorithms simultaneously**, each in its own independent block
- Each algorithm runs on its own **dedicated thread**
- Bar color reflects the current operation:

  | State | Color |
  |---|---|
  | Default | Steel Blue `#5b9bd5` |
  | Comparing | Amber Gold `#f5a623` |
  | Swapping | Coral Red `#e8524a` |
  | Sorted | Mint Green `#4caf82` |

- **Real-time sound feedback** — tone pitch scales with element value
- Adjustable **animation speed**
- Displays comparisons and interchanges count on completion

### Sorting Comparison
- Benchmarks all algorithms across all array sizes and generation modes
- Configurable number of runs per combination
- Results appear live in a table as each benchmark completes
- Supports **custom file input** (comma-separated integers)
- Export results to CSV and auto-launch Python charts with one click

---

## Algorithms

| Algorithm | Best | Average | Worst | Space |
|---|---|---|---|---|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |

---

## Project Structure

```
src/main/
├── java/com/
│   ├── Algorithms/        # Sorting implementations
│   ├── Data/              # SimpleRunData, DataBlock
│   ├── Enums/             # SortingType, ArraySize, ArrayType
│   ├── Events/            # Generic Event/Observer system
│   ├── Factories/         # SortingFactory, ArrayGenerator
│   ├── Utils/             # FileReaderUtil, DataExporter
│   ├── Visualizations/    # JavaFX views and UI components
│   └── sorting/           # MainApp entry point
├── python/
│   ├── analysis.py        # Chart generation script
│   └── plots/             # Generated charts output here
└── resources/
    ├── assets/
    └── styles/
```

---

## Getting Started

### Prerequisites
- Java 17+
- JavaFX 17+
- Python 3.x
- Python packages:
  ```bash
  pip install pandas matplotlib seaborn
  ```

### Running the App
```bash
# Clone the repo
git clone <your-repo-url>
cd SortingAlgorithms

# Run with Maven
mvn javafx:run
```

### Input File Format
Plain text file with comma-separated integers:
```
23, 7, 45, 12, 89, 34, 56, 78, 3, 91
```

---

## Usage

### Visualization Mode
1. Set **Array Size** and **Speed**
2. Select an algorithm from the dropdown
3. Click **+ Add Block** (up to 6 blocks)
4. Click **▶ Start All** or use each block's individual **▶ Play** button

### Comparison Mode
1. Select **Array Generation Mode** (Random / Sorted / Reversed) or load a file
2. Set the number of **Runs**
3. Click **▶ Compare** — results fill the table live
4. Click **📊 Visualize** to export CSV and generate Python charts

---

## Charts

The Python script generates two output files in `src/main/python/plots/`:

- **`analysis.png`** — Line charts per algorithm showing runtime, comparisons, and interchanges scaling across array sizes and generation modes
- **`heatmap_algo_vs_mode.png`** — Heatmaps comparing all algorithms against generation modes for each metric

---

## Assignment

**CSE 224 — Data Structures 2** | Spring 2026 | Alexandria University  
**Due:** Friday, 6th March 2026