package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Date> {
		
	@Override
    public Date convertToDatabaseColumn(LocalDateTime locDateTime) {
    	return (locDateTime == null ? null : Date.from(locDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date sqlDate) {
    	return (sqlDate == null ? null : LocalDateTime.ofInstant(sqlDate.toInstant(), ZoneId.systemDefault()));
    }
}


