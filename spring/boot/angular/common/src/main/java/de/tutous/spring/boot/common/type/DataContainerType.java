package de.tutous.spring.boot.common.type;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DataContainerType {

    ZDC, SWAP;

}
