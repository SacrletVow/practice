package normal.validateCoupons;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 主要考察对字符串的排序
 * 1。 默认顺序排序
 * 2。 自定义顺序排序
 * 3。 字符串符合校验 ， 可以一个个判断，也可使用正则表达式
 * 查学补漏：排序
 * Arrays.sort() 是对数组进行排序
 * Collections.sort() 是对集合进行排序
 * List.sort() 是对列表进行排序
 * Comparator 是用来比较两个对象大小的，可以自定义排序规则。comparing thenComparing
 * stream().sorted() 是对流进行排序
 * 枚举类的使用
 * map.getOrDefault
 */
public class MySolution {

    private static final String regex = "^\\w+$";

    private static Map<String, Integer> orderMap = new HashMap<>();

    static {
        orderMap.put("electronics", 1);
        orderMap.put("grocery", 2);
        orderMap.put("pharmacy", 3);
        orderMap.put("restaurant", 4);
    }
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {

        List<Coupon> validCoupons = new ArrayList<>();
        int size = code.length;
        for (int i = 0; i < size; i++) {
            if (isValidCoupon(code[i],businessLine[i],isActive[i])) {
               validCoupons.add(new Coupon(code[i], businessLine[i], isActive[i]));
            }
        }

        Comparator<Coupon> comparator = Comparator.comparing((Coupon c) -> orderMap.get(c.getBusinessLine()))
                .thenComparing(Coupon::getCode);

        validCoupons.sort(comparator);
        return validCoupons.stream().map(Coupon::getCode).collect(Collectors.toList());

    }

    public boolean isValidCoupon(String code, String businessLine, boolean isActive) {
        return isActive && code.matches(regex) && orderMap.containsKey(businessLine);
    }

    private class Coupon {
        private String code;
        private String businessLine;

        private boolean isActive;

        public Coupon(String code, String businessLine, boolean isActive) {
            this.code = code;
            this.businessLine = businessLine;
            this.isActive = isActive;
        }

        public String getBusinessLine() {
            return businessLine;
        }

        public String getCode() {
            return code;
        }
    }

    public static void main(String[] args) {
        String[] code = {"SAVE20","","PHARMA5","SAVE@20"};
        String[] businessLine = {"restaurant","grocery","pharmacy","restaurant"};
        boolean[] isActive = {true,true,true,true};

        MySolution solution = new MySolution();
        List<String> list = solution.validateCoupons(code, businessLine, isActive);
        System.out.println(list);

    }

}
