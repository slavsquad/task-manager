package ru.stepanenko.tm.util;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.enumerate.Status;

import java.util.*;

public class ComparatorUtil {

    public static Comparator<Project> getProjectComparator(@NotNull final String comparator) {
        Comparator<Project> comparatorDateStart = new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                Date date1 = o1.getDateBegin();
                Date date2 = o2.getDateBegin();
                if (date1 == null) {
                    date1 = new Date(Long.MAX_VALUE);
                }
                if (date2 == null) {
                    date2 = new Date(Long.MAX_VALUE);
                }
                return date1.compareTo(date2);
            }
        };

        Comparator<Project> comparatorDateEnd = new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                Date date1 = o1.getDateEnd();
                Date date2 = o2.getDateEnd();
                if (date1 == null) {
                    date1 = new Date(Long.MAX_VALUE);
                }
                if (date2 == null) {
                    date2 = new Date(Long.MAX_VALUE);
                }
                return date1.compareTo(date2);
            }
        };

        Comparator<Project> comparatorStatus = new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                Status status1 = o1.getStatus();
                Status status2 = o2.getStatus();
                return status1.compareTo(status2);
            }
        };

        Map<String, Comparator<Project>> comparators = new HashMap<>(3);
        comparators.put("dateBegin", comparatorDateStart);
        comparators.put("dateEnd", comparatorDateEnd);
        comparators.put("status", comparatorStatus);

        return comparators.get(comparator);
    }

    public static Comparator<Task> getTaskComparator(@NotNull final String comparator) {
        Comparator<Task> comparatorDateStart = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                Date date1 = o1.getDateBegin();
                Date date2 = o2.getDateBegin();
                if (date1 == null) {
                    date1 = new Date(Long.MAX_VALUE);
                }
                if (date2 == null) {
                    date2 = new Date(Long.MAX_VALUE);
                }
                return date1.compareTo(date2);
            }
        };

        Comparator<Task> comparatorDateEnd = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                Date date1 = o1.getDateEnd();
                Date date2 = o2.getDateEnd();
                if (date1 == null) {
                    date1 = new Date(Long.MAX_VALUE);
                }
                if (date2 == null) {
                    date2 = new Date(Long.MAX_VALUE);
                }
                return date1.compareTo(date2);
            }
        };

        Comparator<Task> comparatorStatus = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                Status status1 = o1.getStatus();
                Status status2 = o2.getStatus();
                return status1.compareTo(status2);
            }
        };

        Map<String, Comparator<Task>> comparators = new HashMap<>(3);
        comparators.put("dateBegin", comparatorDateStart);
        comparators.put("dateEnd", comparatorDateEnd);
        comparators.put("status", comparatorStatus);

        return comparators.get(comparator);
    }

}
