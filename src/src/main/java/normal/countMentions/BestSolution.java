package normal.countMentions;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 内存差不多的情况下，时间从54 -> 18
 */
public class BestSolution {
    public int[] countMentions(int numberOfUsers, List<List<String>> events) {
        Event[] eventArr = new Event[events.size()];
        int id = 0;
        for (List<String> event : events) {
            eventArr[id++] = new Event(Status.valueOf(event.get(0)), toInt(event.get(1)), event.get(2));
        }
        //events.sort也行
        Arrays.sort(eventArr, (a, b) -> a.timestamp == b.timestamp ? a.status.compareTo(b.status) : a.timestamp - b.timestamp);
        boolean[] online = new boolean[numberOfUsers];
        Arrays.fill(online, true);
        int[] ret = new int[numberOfUsers];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (Event event : eventArr) {
            while (!pq.isEmpty() && pq.peek()[0] <= event.timestamp) {
                online[pq.poll()[1]] = true;
            }
            //System.out.println(event.status + ":" + event.content);
            if (event.status == Status.OFFLINE) {
                int uid = toInt(event.content);
                online[uid] = false;
                pq.add(new int[]{event.timestamp + 60, uid});
                continue;
            }

            if (event.content.startsWith("id")) {
                String[] mentions = event.content.split(" ");
                for (String mention : mentions) {
                    int cId = toInt(mention.substring(2));
                    ret[cId]++;
                }
            } else if (event.content.equals("ALL")) {
                for (int i = 0; i < numberOfUsers; ++i) {
                    ret[i]++;
                }
            } else {
                for (int i = 0; i < numberOfUsers; ++i) {
                    if (!online[i]) {
                        continue;
                    }
                    ret[i]++;
                }
            }
        }
        return ret;
    }

    private int toInt(String s) {
        int ret = 0;
        for (int i = 0; i < s.length(); ++i) {
            ret = ret * 10 + (s.charAt(i) - '0');
        }
        return ret;
    }

    public enum Status {
        OFFLINE,
        MESSAGE
    }
    private static class Event{
        private Status status;
        private int timestamp;
        private String content;
        Event(Status status, int timestamp, String content) {
            this.status = status;
            this.timestamp = timestamp;
            this.content = content;
        }
    }
}
