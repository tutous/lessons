package de.tutous.spring.boot.common.entity;

import java.io.Serializable;
import java.util.function.Supplier;

@FunctionalInterface
public interface SafeEntitySupplier extends Supplier<AbstractEntity<? extends Serializable>>
{

}
