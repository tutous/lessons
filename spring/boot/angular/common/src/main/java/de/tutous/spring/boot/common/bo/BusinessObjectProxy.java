package de.tutous.spring.boot.common.bo;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import de.tutous.spring.boot.common.entity.AbstractEntity;
import de.tutous.spring.boot.common.entity.SafeEntitySupplier;
import de.tutous.spring.boot.common.exc.BusinessObjectProxyRuntimeException;

public class BusinessObjectProxy
{

    /**
     * Returns an proxy instance of a{@code BusinessObject} class that dispatches method invocations to the specified
     * entity.
     * 
     * 
     * @param <BO>
     * @param <E>
     * @param <ID>
     * @param interfaceClass
     * @param abstractEntity
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <BO extends BusinessObject<BO, ID>, E extends AbstractEntity<ID>, ID extends Serializable> BO toSafeBO(
            Class<BO> interfaceClass, E abstractEntity)
    {
        return (BO) Proxy.newProxyInstance(
                /** current class loader */
                BusinessObjectProxy.class.getClassLoader(),
                /** the BO interface */
                new Class[]
                {
                        /** BO interface */
                        interfaceClass,
                        /** back door interface */
                        SafeEntitySupplier.class },
                /** invocation handler of the wrapped entity */
                new EntityInvocationHandler(abstractEntity));
    }

    /**
     * Invocation handler of the wrapped entity.
     *
     */
    private final static class EntityInvocationHandler implements InvocationHandler
    {

        private final AbstractEntity<?> entity;
        private final HashMap<Method, Collection<BusinessObject<?, ?>>> businessObjectsByMethods;

        private EntityInvocationHandler(final AbstractEntity<?> entity)
        {
            this.entity = entity;
            this.businessObjectsByMethods = new HashMap<Method, Collection<BusinessObject<?, ?>>>();
        }

        /**
         * Processes a method invocation on a proxy instance of a {@code BusinessObject} and returns a safe BO. This
         * method will be invoked on an invocation handler when a method is invoked on a proxy instance that it is
         * associated with.
         * 
         * @throws BusinessObjectProxyRuntimeException
         * @throws AccessDeniedRuntimeException
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            Object result;
            if (isAccessEntity(method))
            {
                // back door
                result = entity;
            }
            else
            {
                result = getSafeResult(method, args);
            }

            return result;
        }

        private Object getSafeResult(Method method, Object[] args)
        {
            Object result;
            try
            {
                if (isSafeBOs(method, args))
                {
                    // child BOs
                    result = businessObjectsByMethods.get(method);
                }
                else
                {
                    // field value
                    result = method.invoke(entity, args);
                }
            }
            catch (IllegalAccessException | InvocationTargetException e)
            {
                throw new BusinessObjectProxyRuntimeException(entity, e);
            }
            return result;
        }

        /**
         * Try to find iterables of BusinessObject's.
         * 
         * @param method
         * @param args
         * @return true, if are childs an instance of a BusinessObject
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         */
        @SuppressWarnings("unchecked")
        private boolean isSafeBOs(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException
        {
            if (isReturnTypeIterableAndMissed(method))
            {
                Optional<Collection<BusinessObject<?, ?>>> optionalBOs = findSafeBOs(
                        Iterable.class.cast(method.invoke(entity, args)));
                optionalBOs.ifPresent(businessObjects -> {
                    Collection<BusinessObject<?, ?>> safeBOs = new ArrayList<BusinessObject<?, ?>>();
                    for (BusinessObject<?, ?> bo : businessObjects)
                    {
                        safeBOs.add(bo.toSafeBO());
                    }
                    businessObjectsByMethods.put(method, safeBOs);
                });
            }
            return businessObjectsByMethods.containsKey(method);
        }

        /**
         * Collect the BusinessObject's.
         * 
         * @param iterable
         * @return
         */
        private Optional<Collection<BusinessObject<?, ?>>> findSafeBOs(final Iterable<Object> iterable)
        {
            final Collection<BusinessObject<?, ?>> collection = new ArrayList<BusinessObject<?, ?>>();
            Optional<Collection<BusinessObject<?, ?>>> optional = Optional.empty();
            for (Object object : iterable)
            {
                if (isBusinessObject(object))
                {
                    if (!optional.isPresent())
                    {
                        optional = Optional.of(collection);
                    }
                    collection.add(BusinessObject.class.cast(object));
                }
                else
                {
                    break;
                }
            }
            return optional;
        }

        private boolean isReturnTypeIterableAndMissed(Method method)
        {
            return !businessObjectsByMethods.containsKey(method)
                    && Iterable.class.isAssignableFrom(method.getReturnType());
        }

        private boolean isBusinessObject(Object object)
        {
            return BusinessObject.class.isAssignableFrom(object.getClass());
        }

        private boolean isAccessEntity(Method method)
        {
            return method.getDeclaringClass().isAssignableFrom(SafeEntitySupplier.class)
                    && method.getName().equals("get");
        }

    }

    public static Object getEntity(Object obj)
    {
        AbstractEntity<?> safeEntity;
        if (Objects.nonNull(obj) && //
            SafeEntitySupplier.class.isInstance(obj) && //
            Objects.nonNull(safeEntity = SafeEntitySupplier.class.cast(obj).get()) && //
            Objects.nonNull(safeEntity))
        {
            return safeEntity;
        }
        return obj;
    }
}
