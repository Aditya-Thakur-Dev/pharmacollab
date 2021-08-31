package com.pam.labs.pharma.collaborator.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class ConversionUtil {

    public Date convertLocalDateTimeToUtilDate(LocalDateTime localDateTime) {
        return null == localDateTime ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalDateTime convertUtilDateToLocalDateTime(Date utilDate) {
        return null == utilDate ? null : LocalDateTime.ofInstant(utilDate.toInstant(), ZoneId.systemDefault());
    }

    public Date convertLocalDateToUtilDate(LocalDate localDate) {
        return null == localDate ? null : java.sql.Date.valueOf(localDate);
    }

    public LocalDate convertUtilDateToLocalDate(Date utilDate){
        return null == utilDate ? null : new java.sql.Date(utilDate.getTime()).toLocalDate();
    }

}
