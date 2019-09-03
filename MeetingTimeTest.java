import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

class Interval {
    public int start;
    public int end;

    public Interval() {
        start = 0;
        end = 0;
    }

    public Interval(int s, int e) {
        start = s;
        end = e;
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Interval that = (Interval) o;
        return start == that.start && end == that.end;
    }

    @Override
    public int hashCode() {
        return 31 * start + end;
    }
}

class Point implements Comparable<Point> {
    public int time;
    public boolean isStart;

    Point(int time, boolean isStart) {
        this.time = time;
        this.isStart = isStart;
    }

    @Override
    public int compareTo(Point that) {
        if (this.time != that.time || this.isStart == that.isStart) {
            return this.time - that.time;
        } else {
            return this.isStart ? -1 : 1;
        }
    }
}

class MeetingTime {
    List<Interval> getAvailableIntervals(List<List<Interval>> intervals, int k) {
        List<Interval> reslist = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        for(List<Interval> list: intervals) {
            for(Interval interval: list) {
                points.add(new Point(interval.start, true));
                points.add(new Point(interval.end, false));
            }
        }
        Collections.sort(points);
        Point availableStart = null;
        int cnt = 0;
        int size = intervals.size();
        //  k = intervals.size() - cnt ==> intervals.size() - cnt >= k
        for(int i = 0; i< points.size(); ++i) {
            Point point = points.get(i);
            if(point.isStart) {
                cnt++;
                if (availableStart == null && i == 0 && k <= intervals.size() - cnt) {
                    availableStart = point;
                } else if (availableStart != null  && k == intervals.size() - cnt + 1) {
                    reslist.add(new Interval(availableStart.time, point.time));
                    availableStart = null;
                }
            } else {
                cnt--;
                if(availableStart != null && i == points.size() - 1 && k <= intervals.size() - cnt ) {
                    reslist.add(new Interval(availableStart.time, point.time));
                    availableStart = null;
                } else if (i < points.size()-1 && k == intervals.size() - cnt ) {
                    availableStart = point;
                }
            }
        }

        return reslist;
    }
}

public class MeetingTimeTest {
    @Test
    public void test1() {
        MeetingTime sol = new MeetingTime();
        List<List<Interval>> intervals = new ArrayList<List<Interval>>() {{
            add(new ArrayList<Interval>() {{
                add(new Interval(1, 3));
                add(new Interval(6, 7));
            }});
            add(new ArrayList<Interval>() {{
                add(new Interval(2, 4));
            }});
            add(new ArrayList<Interval>() {{
                add(new Interval(2, 3));
                add(new Interval(9, 12));
            }});
        }};
        List<Interval> res = sol.getAvailableIntervals(intervals, 3);
        assertEquals(2, res.size());
        assertEquals(4, res.get(0).start);
        assertEquals(6, res.get(0).end);
        assertEquals(7, res.get(1).start);
        assertEquals(9, res.get(1).end);
    }


}
