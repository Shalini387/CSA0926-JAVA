package CO2_AT1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveDuplicatesUsingSet {

    // Method to remove duplicates
    public static void removeDuplicates(List<Integer> originalList) {

        System.out.println("Original List: " + originalList);

        // Convert ArrayList to HashSet to remove duplicates
        Set<Integer> uniqueSet = new HashSet<>(originalList);

        // Convert HashSet back to ArrayList
        List<Integer> deduplicatedList = new ArrayList<>(uniqueSet);

        System.out.println("Duplicate-Free Set: " + uniqueSet);
        System.out.println("Duplicate-Free List: " + deduplicatedList);
        System.out.println("Original Size: " + originalList.size());
        System.out.println("Duplicate-Free Size: " + deduplicatedList.size());
    }

    public static void main(String[] args) {

        // Test Case 1: Typical Case
        System.out.println("=== Test Case 1: Typical Case ===");
        List<Integer> typicalCase = new ArrayList<>(List.of(10, 20, 10, 30, 20, 40, 10));
        removeDuplicates(typicalCase);

        // Test Case 2: Empty List
        System.out.println("\n=== Test Case 2: Empty List ===");
        List<Integer> emptyCase = new ArrayList<>();
        removeDuplicates(emptyCase);

        // Test Case 3: All Duplicates
        System.out.println("\n=== Test Case 3: All Duplicates ===");
        List<Integer> allDuplicatesCase = new ArrayList<>(List.of(5, 5, 5, 5));
        removeDuplicates(allDuplicatesCase);
    }
}