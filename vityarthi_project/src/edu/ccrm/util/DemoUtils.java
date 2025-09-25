package edu.ccrm.util;

import java.util.Arrays;

public class DemoUtils {
    public static void arraysAndOperatorsDemo() {
        String[] codes = {"CS101", "EE200", "MA150", "CS020"};
        Arrays.sort(codes);
        int idx = Arrays.binarySearch(codes, "CS101");
        int a = 5 + 3 * 2; // precedence demo => 11
        int b = (5 + 3) * 2; // => 16
        int bit = (1 << 3) | 2; // bitwise demo => 10
        if (idx >= 0 && bit > 0) {
            // no-op, demonstrates if and operator usage
        }
        for (String c : codes) {
            if (c.startsWith("CS")) continue;
            // loop demo
        }
    }
}


