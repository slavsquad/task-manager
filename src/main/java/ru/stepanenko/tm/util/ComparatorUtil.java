package ru.stepanenko.tm.util;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.model.entity.BaseEntity;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ComparatorUtil {
    public static<E extends BaseEntity> Comparator<E> getComparator(
            @NotNull final String parameter) {

        Comparator<E> comparatorDateStart = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
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

        Comparator<E> comparatorDateEnd = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
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

        Comparator<E> comparatorStatus = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                Status status1 = o1.getStatus();
                Status status2 = o2.getStatus();
                return status1.compareTo(status2);
            }
        };

        Map<String, Comparator<E>> comparators = new HashMap<>(3);
        comparators.put("dateStart", comparatorDateStart);
        comparators.put("dateEnd", comparatorDateEnd);
        comparators.put("status", comparatorStatus);
        return comparators.get(parameter);
    }
}
