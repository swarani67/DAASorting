import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;


public class DAASorting {


    public static void insertionSort(int[] array) {
        int n = array.length;

        for (int i = 1; i < n; i++) {
            int x = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > x) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = x;
        }
    }

    public static void selectionSort(int[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            int min = i;

            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }

            if (min != i) {
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
    }

    public static void bubbleSort(int[] array) {
        int n = array.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static String getSystemInfo() {
        return "System Info:\n" +
                "Processor: " + System.getProperty("os.arch") + "\n" +
                "RAM: " + Runtime.getRuntime().totalMemory() + " bytes\n" +
                "OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version");
    }

    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        return rand.ints(size, 1, 100).toArray();
    }

    public static void plottingBenchmarkResults(List<Integer> sizes, List<Long> iSort, List<Long> sSort, List<Long> bSort) {
        XYChart chart = new XYChartBuilder()
                .title("Sorting Three Algorithms")
                .theme(Styler.ChartTheme.Matlab)
                .xAxisTitle("a_size")
                .yAxisTitle("ms")
                .width(1000)
                .height(700)
                .build();

        chart.addSeries("Insertion Sort", sizes, iSort);
        chart.addSeries("Selection Sort", sizes, sSort);
        chart.addSeries("Bubble Sort", sizes, bSort);

        new SwingWrapper<>(chart).displayChart();
    }

    public static long benchmark(Runnable sortFunction, int[] array) {
        long startTime = System.nanoTime();
        sortFunction.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
    }

    public static void main(String[] args) {

        int[] sizes = {1, 2, 9, 14, 4, 124, 31, 1241, 55};


        // Benchmark Results
        List<Integer> sizesList = new ArrayList<>();
        List<Long> iSortList = new ArrayList<>();
        List<Long> sSortList = new ArrayList<>();
        List<Long> bSortList = new ArrayList<>();

        // Running benchmarks
        for (int size : sizes) {
            int[] array = generateRandomArray(size);

            sizesList.add(size);

            long insTime = benchmark(() -> insertionSort(array.clone()), array);
            iSortList.add(insTime);

            long selTime = benchmark(() -> selectionSort(array.clone()), array);
            sSortList.add(selTime);

            long bubTime = benchmark(() -> bubbleSort(array.clone()), array);
            bSortList.add(bubTime);

            // Executed Time
            System.out.println("Insertion Sort - a_size: " + size + ", Execution Time: " + insTime + " ms");
            System.out.println("Selection Sort - a_size: " + size + ", Execution Time: " + selTime + " ms");
            System.out.println("Bubble Sort - a_size: " + size + ", Execution Time: " + bubTime + " ms");
            System.out.println();
        }

        // Plot the benchmark results
        plottingBenchmarkResults(sizesList, iSortList, sSortList, bSortList);

        // System Information
        System.out.println(getSystemInfo());
    }

}
