package com.Utils;

import com.Data.DataBlock;

import javafx.scene.control.TableView;

public class DataExporter {

    private DataExporter() {
    }

    public static void exportAndVisualize(TableView<DataBlock> table) {
        try {
            String base = "/home/nour/Code/Labs/javaFX/sortAlogos/SortingAlogrithms/sorting/src/main/python";
            String scriptPath = base + "/analysis.py";
            java.nio.file.Path csvPath = java.nio.file.Path.of(base + "/plots/sort_data.csv");

            // Build CSV
            StringBuilder csv = new StringBuilder();
            csv.append("algorithm,arraySize,mode,avgMs,minMs,maxMs,runs,comparisons,interchanges\n");

            for (DataBlock b : table.getItems()) {
                csv.append(b.getSortingAlgorithmName()).append(",")
                        .append(b.getArraySize()).append(",")
                        .append(b.getArrayGenerationMode()).append(",")
                        .append(b.getAverageRuntime()).append(",")
                        .append(b.getMinRuntime()).append(",")
                        .append(b.getMaxRuntime()).append(",")
                        .append(b.getNumberOfRuns()).append(",")
                        .append(b.getComparisonsNumber()).append(",")
                        .append(b.getInterchangesNumber()).append("\n");
            }

            // Write file (creates /plots dir if missing)
            java.nio.file.Files.createDirectories(csvPath.getParent());
            java.nio.file.Files.writeString(csvPath, csv.toString());

            // Launch Python
            new ProcessBuilder("python3", scriptPath, csvPath.toString())
                    .inheritIO()
                    .start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
