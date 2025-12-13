package normal.validateCoupons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestSolution {
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        String[] electronicsType = new String[code.length];
        String[] groceryType = new String[code.length];
        String[] pharmacyType = new String[code.length];
        String[] restaurantType = new String[code.length];
        int index1 = 0, index2 = 0, index3 = 0, index4 = 0;
        for (int i = 0; i < code.length; i++) {
            if (!isActive[i] || !checkBusinessLine(businessLine[i])) {
                continue;
            }
            if (code[i].isEmpty() || !checkCodeName(code[i])) {
                continue;
            }
            if ("electronics".equals(businessLine[i])) {
                electronicsType[index1++] = code[i];
            } else if ("grocery".equals(businessLine[i])) {
                groceryType[index2++] = code[i];
            } else if ("pharmacy".equals(businessLine[i])) {
                pharmacyType[index3++] = code[i];
            } else if ("restaurant".equals(businessLine[i])) {
                restaurantType[index4++] = code[i];
            }
        }
        Arrays.sort(electronicsType, 0, index1);
        Arrays.sort(groceryType, 0, index2);
        Arrays.sort(pharmacyType, 0, index3);
        Arrays.sort(restaurantType, 0, index4);
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < index1; i++) {
            ans.add(electronicsType[i]);
        }
        for (int i = 0; i < index2; i++) {
            ans.add(groceryType[i]);
        }
        for (int i = 0; i < index3; i++) {
            ans.add(pharmacyType[i]);
        }
        for (int i = 0; i < index4; i++) {
            ans.add(restaurantType[i]);
        }
        return ans;
    }

    private boolean checkCodeName(String s) {
        for (char c : s.toCharArray()) {
            if ((c < '0' || c > '9') && (c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                if (c != '_') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkBusinessLine(String businessLine) {
        return "electronics".equals(businessLine) || "pharmacy".equals(businessLine) || "grocery".equals(businessLine) || "restaurant".equals(businessLine);
    }
}
