package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        List<String> names = new ArrayList<>();

        String path = "../output.csv";
        FileInputStream fis = new FileInputStream(path);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = br.readLine()) != null) {
                int i = 0;
                StringBuilder fullName = new StringBuilder();
                while (i < line.length() && line.charAt(i) != ' ' ) {
                    if (isAlphanumeric(line.charAt(i))) {
                        fullName.append(line.charAt(i));
                    }
                    i++;
                }
                names.add(fullName.toString().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }







//        for (String name: names) {
//            System.out.println(name);
//        }
//        System.out.println(names.size());
        String[] result= topKFrequent(names, 1);
        for (String name: result) {
            System.out.println(name);
        }
    }


    private static boolean isAlphanumeric(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if (c >= 'A' && c <= 'Z') {
            return true;
        }
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }


    public static String[] topKFrequent(List<String> combo, int k) {

        if (combo.size() == 0) {
            return new String[0];
        }
        Map<String, Integer> freqMap = getFreqMap(combo);
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(k,
                new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String,Integer> e2) {
                        return e1.getValue().compareTo(e2.getValue());
                    }
                });
        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(entry);
            } else if (entry.getValue() > minHeap.peek().getValue()) {
                minHeap.poll();
                minHeap.offer(entry);
            }
        }
        return freqArray(minHeap);
    }

    private static Map<String, Integer> getFreqMap(List<String> combo) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String s : combo) {
            Integer freq = freqMap.get(s);
            if (freq == null) {
                freqMap.put(s, 1);
            } else {
                freqMap.put(s, freq + 1);
            }
        }
        return freqMap;
    }
    private static String[] freqArray(PriorityQueue<Map.Entry<String, Integer>> minHeap) {
        String[] result = new String[minHeap.size()];
        for (int i = minHeap.size() - 1; i >= 0; i--) {
            result[i] = minHeap.poll().getKey();

        }
        return result;
    }
}
