package com.bokachan.Day02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;


@SpringBootApplication
public class Day02Application {

	public static void main(String[] args) {
		SpringApplication.run(Day02Application.class, args);
        System.out.println(" BUBBLE SORT DEMO");

        // Mảng cần sắp xếp
        int[] array = {64, 34, 25, 12, 22, 11, 90, 5, 77, 30};

        // In mảng ban đầu
        System.out.println("Mảng ban đầu: " + Arrays.toString(array));

        // Gọi hàm bubble sort
        bubbleSort(array);

        // In mảng sau khi sắp xếp
        System.out.println("Mảng sau sắp xếp: " + Arrays.toString(array));
    }

    // Hàm sắp xếp bubble sort
    public static void bubbleSort(int[] arr) {
        System.out.println("🔹 Đang sắp xếp bằng Bubble Sort...");

        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] và arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // Nếu không có swap nào, mảng đã được sắp xếp
            if (!swapped) {
                break;
            }
        }
    }
}
