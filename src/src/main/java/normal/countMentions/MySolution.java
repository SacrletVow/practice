package normal.countMentions;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 整体上来讲，思路上没问题
 * 1。 先对事件进行排序，按照时间戳和事件类型进行排序；
 * 2。 需要有一个状态机来保存用户的在线状态
 */
public class MySolution {
    public int[] countMentions(int numberOfUsers, List<List<String>> events) {
        int[] mentions = new int[numberOfUsers];

        //用于记录用户登陆信息状态
        int[] onlineState = new int[numberOfUsers];

        List<List<String>> lists = sortEvent(events);

        for (List<String> event : lists) {
            doEvent(event, mentions, onlineState);
        }
        return mentions;
    }

    public void doEvent(List<String> event, int[] mentions, int[] onlineState){
        String message = event.get(0);
        String timeStamp = event.get(1);
        String scope = event.get(2);

        switch (message){
            case "MESSAGE":
                doMessageEvent(timeStamp,scope,mentions,onlineState);
                break;
            case "OFFLINE":
                doOfflineEvent(timeStamp,scope,onlineState);
                break;
            default:
                break;
        }
    }

    private void doMessageEvent(String timeStamp, String scope, int[] mentions, int[] onlineState){
        switch (scope){
            case "ALL":
                doAllEvent(mentions);
                break;
            case "HERE":
                doHereEvent(timeStamp,mentions,onlineState);
                break;
            default:
                doDefaultEvent(scope,mentions);
                break;
        }
    }

    /**
     * 更新所有用户
     * @param mentions
     */
    private void doAllEvent(int[] mentions){
        for (int i = 0; i < mentions.length; i++) {
            mentions[i]+=1;
        }
    }

    /**
     * 更新在线用户
     * @param timeStamp
     * @param mentions
     * @param onlineState
     */
    private void doHereEvent(String timeStamp, int[] mentions, int[] onlineState){
        for (int i = 0; i < mentions.length; i++) {
            if (onlineState[i] >= 0){
                mentions[i]+=1;
            }else {
                if (60 - onlineState[i] <= Integer.parseInt(timeStamp)){
                    mentions[i]+=1;
                    onlineState[i] = 0;
                }
            }
        }
    }

    private void doDefaultEvent(String scope, int[] mentions){
        String[] user = scope.split(" ");
        for (int i = 0; i < user.length; i++) {
            int index = convertScopeToInt(user[i]);
            if (index < mentions.length){
                mentions[index]+=1;
            }
        }
    }

    private void doOfflineEvent(String timeStamp, String scope, int[] onlineState){
        String[] user = scope.split(" ");
        for (int i = 0; i < user.length; i++) {
            int index = Integer.parseInt(user[i]);
            int offlineTime = Integer.parseInt(timeStamp);
            if (index < onlineState.length){
                onlineState[index] = -offlineTime;
            }
        }
    }

    private int convertScopeToInt(String str){
        int index = str.indexOf("d");
        System.out.println(index);
        return Integer.parseInt(str.substring(index+1, str.length()));
    }

    private List<List<String>> sortEvent(List<List<String>> events){
        return events.stream().sorted((event1, event2) -> {
            int time1 = Integer.parseInt(event1.get(1));
            int time2 = Integer.parseInt(event2.get(1));

            if (time1 != time2) {
                return Integer.compare(time1, time2);
            }

            String scope1 = event1.get(0);
            String scope2 = event2.get(0);

            if (scope1.equals("OFFLINE") && scope2.equals("MESSAGE")) {
                return -1;
            } else if (scope1.equals("MESSAGE") && scope2.equals("OFFLINE")) {
                return 1;
            }
            return 0;
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        String str = "id0";
        int index = str.indexOf("d");
        System.out.println(index);
        String id = str.substring(index+1, str.length());
        System.out.println(id);
    }
}
