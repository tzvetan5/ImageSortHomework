package imageuploadexample;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SortVisualizer extends JFrame {
    private JComboBox<String> algorithmSelector;
    private JPanel sortPanel;
    private List<SortableElement> elements = new ArrayList<>();
    private String[] sortingAlgorithms = {"Bubble sort", "Selection sort", "Insertion sort", "Merge sort", "Quick sort", "Heap sort"};

    public SortVisualizer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sorting Visualizer");
        initUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        algorithmSelector = new JComboBox<>(sortingAlgorithms);
        JButton sortButton = new JButton("Sort");

        sortButton.addActionListener(e -> startSorting());

        JPanel topPanel = new JPanel();
        topPanel.add(algorithmSelector);
        topPanel.add(sortButton);

        add(topPanel, BorderLayout.NORTH);

        sortPanel = new JPanel();
        sortPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(sortPanel, BorderLayout.CENTER);

        loadElements();
        displayElements();
    }

    private void loadElements() {

        String[] imageFilenames = {"java100.png", "java50.png", "java150.png", "java.png", "java75.png"};
        Path resourceDirectory = Paths.get("src","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        for (String imageName : imageFilenames) {
            ImageIcon imgIcon = new ImageIcon(absolutePath + "/" + imageName);
            int size = imgIcon.getIconHeight();
            elements.add(new SortableElement(imgIcon, size));
        }
    }

    private void displayElements() {
        sortPanel.removeAll();
        for (SortableElement element : elements) {
            JLabel imgLabel = new JLabel();
            imgLabel.setIcon(element.getImageIcon());
            sortPanel.add(imgLabel);
        }
        sortPanel.revalidate();
        sortPanel.repaint();
    }

    private void startSorting() {
        String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
        switch (selectedAlgorithm) {
            case "Bubble sort":
                bubbleSort();
                break;
            case "Selection sort":
                selectionSort();
                break;
            case "Insertion sort":
                insertionSort();
                break;
            case "Merge sort":
                mergeSort(0, elements.size() - 1);
                break;
            case "Quick sort":
                quickSort(0, elements.size() - 1);
                break;
            case "Heap sort":
                heapSort();
                break;
        }
    }



    private void updateUI() {
        sortPanel.removeAll();
        for (SortableElement element : elements) {
            JLabel imgLabel = new JLabel();
            imgLabel.setIcon(element.getImageIcon());
            sortPanel.add(imgLabel);
        }
        sortPanel.revalidate();
        sortPanel.repaint();
    }

    private void sleepForVisualEffect() {

        // I have read it in the internet this technique, here I am slowing the transition to ensure the effect is smooth
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void bubbleSort() {
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = 0; j < elements.size() - i - 1; j++) {
                if (elements.get(j).getSize() > elements.get(j + 1).getSize()) {

                    SortableElement temp = elements.get(j);
                    elements.set(j, elements.get(j + 1));
                    elements.set(j + 1, temp);


                    updateUI();
                    sleepForVisualEffect();
                }
            }
        }
    }

    private void selectionSort() {
        int n = elements.size();

        for (int i = 0; i < n - 1; i++) {
            // Finding the min element
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
                if (elements.get(j).compareTo(elements.get(min_idx)) < 0)
                    min_idx = j;

            // Changing the position of first and min element
            SortableElement temp = elements.get(min_idx);
            elements.set(min_idx, elements.get(i));
            elements.set(i, temp);


            updateUI();
            sleepForVisualEffect();
        }
    }

    private void insertionSort() {
        int n = elements.size();
        for (int i = 1; i < n; ++i) {
            SortableElement key = elements.get(i);
            int j = i - 1;

            while (j >= 0 && elements.get(j).compareTo(key) > 0) {
                elements.set(j + 1, elements.get(j));
                j = j - 1;

                updateUI();
                sleepForVisualEffect();
            }

            elements.set(j + 1, key);

        }
    }

    private void merge(int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Separating each half in two lists
        List<SortableElement> leftArray = new ArrayList<>();
        List<SortableElement> rightArray = new ArrayList<>();


        for (int i = 0; i < n1; ++i)
            leftArray.add(elements.get(left + i));
        for (int j = 0; j < n2; ++j)
            rightArray.add(elements.get(middle + 1 + j));

        // Merging the two list into the original one
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray.get(i).compareTo(rightArray.get(j)) <= 0) {
                elements.set(k, leftArray.get(i));
                i++;
            } else {
                elements.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            elements.set(k, leftArray.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            elements.set(k, rightArray.get(j));
            j++;
            k++;
        }

        updateUI();
        sleepForVisualEffect();
    }
    private void mergeSort(int left, int right) {
        if (left < right) {
            // Finding the middle and then dividing in two parts
            int middle = left + (right - left) / 2;

            // Sorting each half
            mergeSort(left, middle);
            mergeSort(middle + 1, right);

            // Merging
            merge(left, middle, right);
        }
    }

    private void quickSort(int low, int high) {
        if (low < high) {

            int pi = partition(low, high);

            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }
    private int partition(int low, int high) {
        // Finding the Pivot element
        SortableElement pivot = elements.get(high);

        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // Comparing to the pivot
            if (elements.get(j).compareTo(pivot) < 0) {
                i++;

                SortableElement temp = elements.get(i);
                elements.set(i, elements.get(j));
                elements.set(j, temp);

                updateUI();
                sleepForVisualEffect();
            }
        }

        SortableElement temp = elements.get(i+1);
        elements.set(i+1, elements.get(high));
        elements.set(high, temp);

        updateUI();
        sleepForVisualEffect();

        return i + 1;
    }

    private void heapSort() {
        int n = elements.size();

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(elements, n, i);

        for (int i=n-1; i>=0; i--) {

            SortableElement temp = elements.get(0);
            elements.set(0, elements.get(i));
            elements.set(i, temp);

            heapify(elements, i, 0);

            updateUI();
            sleepForVisualEffect();
        }
    }
    void heapify(List<SortableElement> arr, int n, int i) {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;


        if (l < n && arr.get(l).compareTo(arr.get(largest)) > 0)
            largest = l;


        if (r < n && arr.get(r).compareTo(arr.get(largest)) > 0)
            largest = r;


        if (largest != i) {
            SortableElement swap = arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);

            heapify(arr, n, largest);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(SortVisualizer::new);
    }
}
