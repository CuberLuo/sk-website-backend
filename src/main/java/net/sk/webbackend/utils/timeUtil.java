package net.sk.webbackend.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class timeUtil {
    /**
     * LocalDateTime转毫秒时间戳
     * @param localDateTime
     * @return
     */
    public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zoneId).toInstant();
            return instant.toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocalDateTime strToLocalDateTime(String s){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(s,df);
    }

}
