package de.tutous.spring.boot.common.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import de.tutous.spring.boot.common.lang.OptionalMap;

public interface ToString
{

    @Target(
    { ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ToStringProperty
    {

        /**
         * It only works with propertiesToString(template).
         * 
         */
        String id()

        default "";

        @SuppressWarnings("rawtypes")
        Class<? extends ToStringPropertyReader> reader() default ToStringPropertyReader.class;

    }

    /**
     * Convert the template variables to a values express. It does not work with {@code ToStringPropertyReader} </br>
     * </br>
     * example: </br>
     * {@code @ToStringProperty(id = "status")}</br>
     * private StatusAttribute statusAttribute;</br>
     * {@code @ToStringProperty(id = "value")}</br>
     * private boolean value;</br>
     * </br>
     * {@code @Override}</br>
     * public String toString()</br>
     * {</br>
     * return propertiesToString("${status}=${value}");</br>
     * }</br>
     * 
     * result is: SELECTED_SOURCE=true
     * 
     * @return
     */
    public default String propertiesToString(String template)
    {
        return ToStringUtil.propertiesToString(this, template);
    }

    public default String propertiesToString()
    {
        return ToStringUtil.propertiesToString(this);
    }

    static class ToStringUtil
    {

        private ToStringUtil()
        {
        }

        private static String propertiesToString(Object instance)
        {
            StringBuilder toStringValues = new StringBuilder();
            StringBuilder parenthesis = new StringBuilder();
            // toString by this.getClass()
            String values = getProperties(instance.getClass().getDeclaredFields(), instance);
            toStringValues.append(instance.getClass().getSimpleName());
            toStringValues.append('[');
            toStringValues.append(values);
            parenthesis.append(']');
            // toString by this.getClass().getSuperclass()
            Class<?> superClass = instance.getClass().getSuperclass();
            while (superClass != Object.class)
            {
                toStringValues.append(',');
                toStringValues.append(superClass.getSimpleName());
                values = getProperties(superClass.getDeclaredFields(), instance);
                toStringValues.append('[');
                toStringValues.append(values);
                superClass = superClass.getSuperclass();
                parenthesis.append(']');
            }
            // toString result
            toStringValues.append(parenthesis.toString());
            return toStringValues.toString();
        }

        /**
         * ...
         * 
         * @param instance
         * @param template
         * @return
         */
        static String propertiesToString(final Object instance, final String template)
        {
            Map<String, Object> fields = Stream.of(instance.getClass().getDeclaredFields()).filter(f ->
            {
                ToStringProperty toStringProperty = f.getAnnotation(ToStringProperty.class);
                if (Objects.nonNull(toStringProperty))
                {
                    return template.indexOf("${" + toStringProperty.id() + "}") != -1;
                }
                else
                {
                    return false;
                }
            }).collect(Collectors.toMap(f -> "${" + f.getAnnotation(ToStringProperty.class).id() + "}", f ->
            {
                try
                {
                    f.setAccessible(true);
                    return f.get(instance);
                }
                catch (IllegalArgumentException | IllegalAccessException e)
                {
                    return "error";
                }
            }));
            String result = template;
            for (Entry<String, Object> field : fields.entrySet())
            {
                result = result.replace(field.getKey(), String.valueOf(field.getValue()));
            }
            return result;
        }

        /**
         * Get properties by fields.
         * 
         * @param fields
         * @param instanze
         * @return
         */
        private static String getProperties(Field[] fields, Object instanze)
        {
            StringBuilder sb = new StringBuilder();
            int index = 0;
            for (Field field : fields)
            {
                String value = getValue(field, instanze);
                if (Objects.nonNull(value))
                {
                    if (index > 0)
                    {
                        sb.append(',');
                    }
                    sb.append(value);
                    index++;
                }
            }
            return sb.toString();
        }

        /**
         * Get value by field.
         * 
         * @param field
         * @param toStringInstanze
         * @return
         * @throws InvocationTargetException
         * @throws IllegalArgumentException
         */
        private static String getValue(Field field, Object toStringInstanze)
        {
            ToStringProperty toStringProperty = field.getAnnotation(ToStringProperty.class);
            if (Objects.isNull(toStringProperty))
            {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(field.getName());
            sb.append('=');
            if (Objects.nonNull(toStringProperty.reader()))
            {
                try
                {
                    field.setAccessible(true);
                    Object toStringPropertyValue = field.get(toStringInstanze);
                    Object toStringValue = readProperty(toStringProperty.reader(), toStringPropertyValue);
                    sb.append(String.valueOf(toStringValue));
                }
                catch (Exception e)
                {
                    sb.append("'error'");
                }
            }
            else
            {
                sb.append("'reader required'");
            }
            return sb.toString();
        }

        @SuppressWarnings("rawtypes")
        private static Object readProperty(Class<? extends ToStringPropertyReader> readerClass, Object toStringPropertyValue)
                throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
        {
            ToStringPropertyReader<?> reader = readerClass.newInstance();
            Method readerMethod = reader.getClass().getMethod("read", new Class[]
            { reader.getType() });
            return readerMethod.invoke(reader, toStringPropertyValue);
        }

    }

    /**
     * array reader
     *
     */
    public static class ArrayToStringPropertyReader extends ToStringPropertyReader<Object>
    {

        private static OptionalMap<Class<?>, BiConsumer<Object, Builder<Object>>> toObjectArrayConsumers;
        static
        {
            toObjectArrayConsumers = new OptionalMap<Class<?>, BiConsumer<Object, Builder<Object>>>();
            toObjectArrayConsumers.put(byte[].class, (pv, b) ->
            {
                for (byte v : byte[].class.cast(pv))
                    b.accept(Byte.valueOf(v));
            });
            toObjectArrayConsumers.put(int[].class, (pv, b) ->
            {
                for (int v : int[].class.cast(pv))
                    b.accept(Integer.valueOf(v));
            });
            toObjectArrayConsumers.put(double[].class, (pv, b) ->
            {
                for (double v : double[].class.cast(pv))
                    b.accept(Double.valueOf(v));
            });
            toObjectArrayConsumers.put(short[].class, (pv, b) ->
            {
                for (short v : short[].class.cast(pv))
                    b.accept(Short.valueOf(v));
            });
            toObjectArrayConsumers.put(boolean[].class, (pv, b) ->
            {
                for (boolean v : boolean[].class.cast(pv))
                    b.accept(Boolean.valueOf(v));
            });
            toObjectArrayConsumers.put(long[].class, (pv, b) ->
            {
                for (long v : long[].class.cast(pv))
                    b.accept(Long.valueOf(v));
            });
        }

        public Class<?> getType()
        {
            return Object.class;
        }

        @Override
        public String read(Object propertyValue)
        {
            if (Objects.isNull(propertyValue))
            {
                return "null";
            }
            if (!propertyValue.getClass().isArray())
            {
                return "array required";
            }
            Object[] array;
            if (isPrimitiveArray(propertyValue))
            {
                array = toObjectArray(propertyValue);
            }
            else
            {
                array = Object[].class.cast(propertyValue);
            }
            return Arrays.toString(array);
        }

        private Object[] toObjectArray(Object toStringPropertyValue)
        {
            Builder<Object> builder = Stream.builder();
            toObjectArrayConsumers.getOptional(toStringPropertyValue.getClass()).ifPresent(c -> c.accept(toStringPropertyValue, builder));
            return builder.build().collect(Collectors.toList()).toArray();
        }

        private boolean isPrimitiveArray(Object toStringPropertyValue)
        {
            boolean b = byte[].class == toStringPropertyValue.getClass();
            b = b || int[].class == toStringPropertyValue.getClass();
            b = b || double[].class == toStringPropertyValue.getClass();
            b = b || short[].class == toStringPropertyValue.getClass();
            b = b || boolean[].class == toStringPropertyValue.getClass();
            b = b || long[].class == toStringPropertyValue.getClass();
            return b;
        }

    }

    /**
     * simple base reader
     *
     * @param <T>
     */
    public static class ToStringPropertyReader<T extends Object>
    {

        public Class<?> getType()
        {
            return Object.class;
        }

        public String read(T propertyValue)
        {
            if (Objects.isNull(propertyValue))
            {
                return "null";
            }
            StringBuilder sb = new StringBuilder();
            try
            {
                boolean quotationMark = isBaseType(propertyValue);
                if (quotationMark)
                {
                    sb.append('\'');
                }
                sb.append(String.valueOf(propertyValue));
                if (quotationMark)
                {
                    sb.append('\'');
                }
            }
            catch (Exception e)
            {
                sb.append("error");
            }
            return sb.toString();
        }

        private boolean isBaseType(Object propertyValue)
        {
            boolean b = String.class.isInstance(propertyValue);
            b = b || Number.class.isInstance(propertyValue);
            b = b || Date.class.isInstance(propertyValue);
            b = b || Temporal.class.isInstance(propertyValue);
            b = b || Boolean.class.isInstance(propertyValue);
            b = b || propertyValue.getClass().isPrimitive();
            b = b || propertyValue.getClass().isEnum();
            return b;
        }

    }

    public static Object arg(Object arg)
    {
        if (ToString.class.isInstance(arg))
        {
            return ToString.class.cast(arg).propertiesToString();
        }
        else
        {
            return arg;
        }
    }

    public static Object[] args(Object[] arguments)
    {
        return Stream.of(arguments).map(a -> arg(a)).collect(Collectors.toList()).toArray();
    }
}
