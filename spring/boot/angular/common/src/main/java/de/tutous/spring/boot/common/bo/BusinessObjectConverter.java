package de.tutous.spring.boot.common.bo;

import org.springframework.core.convert.converter.Converter;

public interface BusinessObjectConverter<S, BO extends BusinessObject<?, ?>> extends Converter<S, BO>
{

}
