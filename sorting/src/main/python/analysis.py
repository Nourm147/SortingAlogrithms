import sys, os
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
import seaborn as sns

path = sys.argv[1] if len(sys.argv) > 1 else "sort_data.csv"
df   = pd.read_csv(path)

df = df.sort_values("arraySize")

for metric in ["avgMs", "comparisons", "interchanges"]:
    df[metric] = df[metric].replace(0, float("nan"))

charts_dir = os.path.join(os.path.dirname(__file__), "plots")
os.makedirs(charts_dir, exist_ok=True)

algos  = sorted(df["algorithm"].unique())
modes  = df["mode"].unique()
colors = sns.color_palette("tab10", len(modes))

metrics = [
    ("avgMs",        "Avg Runtime (ms)", "YlOrRd"),
    ("comparisons",  "Comparisons",      "Blues"),
    ("interchanges", "Interchanges",     "Greens"),
]

# one subplot per algo
sns.set_theme(style="darkgrid")

rows = len(metrics)
cols = len(algos)

fig, axes = plt.subplots(rows, cols, figsize=(5 * cols, 4 * rows))
axes = axes.reshape(rows, cols)

for row, (metric, label, _) in enumerate(metrics):
    linthresh = df[metric].dropna().min() * 2
    for col, algo in enumerate(algos):
        ax = axes[row][col]
        subset = df[df["algorithm"] == algo]

        for mode, color in zip(modes, colors):
            data = subset[subset["mode"] == mode].dropna(subset=[metric])
            if data.empty:
                continue
            ax.plot(data["arraySize"].astype(str), data[metric],
                    marker="o", label=mode, color=color, linewidth=2)

        ax.set_yscale("symlog", linthresh=linthresh)
        ax.yaxis.set_minor_formatter(ticker.NullFormatter())
        ax.set_title(algo, fontweight="bold", fontsize=10)
        ax.set_ylabel(label if col == 0 else "")
        ax.set_xlabel("")
        ax.tick_params(axis="x", rotation=40, labelsize=7)
        ax.legend(fontsize=7)

plt.suptitle("Sorting Algorithm Analysis", fontsize=16, fontweight="bold", y=1.01)
plt.tight_layout()
plt.savefig(os.path.join(charts_dir, "analysis.png"), dpi=150, bbox_inches="tight")
plt.show()

# algo x mode 
sns.set_theme(style="white")

fig, axes = plt.subplots(1, 3, figsize=(22, 7))

for ax, (metric, title, cmap) in zip(axes, metrics):
    pivot = df.pivot_table(index="algorithm", columns="mode", values=metric, aggfunc="mean")
    sns.heatmap(pivot, annot=True, fmt=".2g", cmap=cmap,
                linewidths=0.5, ax=ax, cbar_kws={"label": title})
    ax.set_title(title, fontweight="bold", fontsize=12)
    ax.set_xlabel("Mode")
    ax.set_ylabel("Algorithm")
    ax.tick_params(axis="x", rotation=30)

plt.suptitle("Algorithm x Generation Mode Heatmap", fontsize=15, fontweight="bold")
plt.tight_layout()
plt.savefig(os.path.join(charts_dir, "heatmap_algo_vs_mode.png"), dpi=150, bbox_inches="tight")
plt.show()

print(f" Saved to {charts_dir}")