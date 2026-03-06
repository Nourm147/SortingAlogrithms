import sys, os
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
import seaborn as sns

# Load data
path = sys.argv[1] if len(sys.argv) > 1 else "sort_data.csv"
df = pd.read_csv(path)

charts_dir = os.path.join(os.path.dirname(__file__), "plots")
os.makedirs(charts_dir, exist_ok=True)

sns.set_theme(style="darkgrid")

# Fix array size ordering
SIZE_ORDER = ["EXTRA_TINY", "TINY", "EXTRA_SMALL", "SMALL", "MEDIUM", "LARGE", "EXTRA_LARGE", "HUGE"]
df["arraySize"] = pd.Categorical(df["arraySize"], categories=SIZE_ORDER, ordered=True)
df = df.sort_values("arraySize")

modes = df["mode"].unique()
algos = df["algorithm"].unique()

# Algo vs Avg Runtime (log scale) 
fig, axes = plt.subplots(1, len(modes), figsize=(6 * len(modes), 6), sharey=False)
axes = [axes] if len(modes) == 1 else list(axes)

for ax, mode in zip(axes, modes):
    grouped = df[df["mode"] == mode].groupby("algorithm")["avgMs"].mean().reset_index()
    grouped = grouped.sort_values("avgMs", ascending=False)
    bars = sns.barplot(data=grouped, x="algorithm", y="avgMs", palette="tab10", ax=ax)

    # annotate exact values on top of each bar
    for bar, val in zip(bars.patches, grouped["avgMs"]):
        ax.text(bar.get_x() + bar.get_width() / 2,
                bar.get_height() + 0.5,
                f"{val:.2f}ms", ha="center", va="bottom", fontsize=9, fontweight="bold")

    ax.set_yscale("log")
    ax.set_title(f"Avg Runtime — {mode}", fontweight="bold")
    ax.set_xlabel("Algorithm")
    ax.set_ylabel("Avg Runtime (ms) — log scale")
    ax.tick_params(axis="x", rotation=30)

plt.suptitle("Algorithm Comparison by Generation Mode", fontsize=15, fontweight="bold")
plt.tight_layout()
plt.savefig(os.path.join(charts_dir, "bar_algo_vs_runtime.png"), dpi=150)
plt.show()

# Array Size vs Runtime 
fig, axes = plt.subplots(1, len(modes), figsize=(6 * len(modes), 6), sharey=False)
axes = [axes] if len(modes) == 1 else list(axes)

for ax, mode in zip(axes, modes):
    subset = df[df["mode"] == mode]
    for algo, group in subset.groupby("algorithm"):
        g = group.sort_values("arraySize")  # now sorts by SIZE_ORDER
        ax.plot(g["arraySize"].astype(str), g["avgMs"], marker="o", label=algo, linewidth=2)

    ax.set_yscale("log")
    ax.set_title(f"Scaling — {mode}", fontweight="bold")
    ax.set_xlabel("Array Size")
    ax.set_ylabel("Avg Runtime (ms) — log scale")
    ax.legend(fontsize=8)
    ax.tick_params(axis="x", rotation=30)

plt.suptitle("Runtime Scaling by Array Size", fontsize=15, fontweight="bold")
plt.tight_layout()
plt.savefig(os.path.join(charts_dir, "line_size_vs_runtime.png"), dpi=150)
plt.show()

# Heatmap — Algo × Mode
fig, axes = plt.subplots(1, 2, figsize=(6 * max(len(modes), 2), 7))

pivot_rt  = df.pivot_table(index="algorithm", columns="mode", values="avgMs",       aggfunc="mean")
pivot_cmp = df.pivot_table(index="algorithm", columns="mode", values="comparisons",  aggfunc="mean")

sns.heatmap(pivot_rt,  annot=True, fmt=".2f", cmap="YlOrRd",
            linewidths=0.5, ax=axes[0], cbar_kws={"label": "Avg Runtime (ms)"})
axes[0].set_title("Avg Runtime (ms)", fontweight="bold")

sns.heatmap(pivot_cmp, annot=True, fmt=".0f", cmap="Blues",
            linewidths=0.5, ax=axes[1], cbar_kws={"label": "Avg Comparisons"})
axes[1].set_title("Avg Comparisons", fontweight="bold")

plt.suptitle("Algorithm x Generation Mode Heatmap", fontsize=15, fontweight="bold")
plt.tight_layout()
plt.savefig(os.path.join(charts_dir, "heatmap_algo_vs_mode.png"), dpi=150)
plt.show()

# grouped by algo + size 
fig, axes = plt.subplots(1, 2, figsize=(18, 6))

sns.barplot(data=df, x="arraySize", y="comparisons", hue="algorithm",
            palette="tab10", ax=axes[0])
axes[0].set_yscale("log")
axes[0].set_title("Comparisons by Size & Algorithm", fontweight="bold")
axes[0].set_xlabel("Array Size")
axes[0].set_ylabel("Comparisons (log scale)")
axes[0].tick_params(axis="x", rotation=30)
axes[0].legend(fontsize=8)

sns.barplot(data=df, x="arraySize", y="interchanges", hue="algorithm",
            palette="tab10", ax=axes[1])
axes[1].set_yscale("log")
axes[1].set_title("Interchanges by Size & Algorithm", fontweight="bold")
axes[1].set_xlabel("Array Size")
axes[1].set_ylabel("Interchanges (log scale)")
axes[1].tick_params(axis="x", rotation=30)
axes[1].legend(fontsize=8)

plt.suptitle("Operations Count by Array Size", fontsize=15, fontweight="bold")
plt.tight_layout()
plt.savefig(os.path.join(charts_dir, "bar_operations.png"), dpi=150)
plt.show()

print(f"✅ Charts saved to {charts_dir}")