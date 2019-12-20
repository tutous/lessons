package de.tutous.spring.boot.common.bo;

import java.io.Serializable;

public interface BusinessObject<BO extends BusinessObject<BO, ID>, ID extends Serializable>
{

    ID getId();

    BO toSafeBO();

    public static class VoidSerializable implements java.io.Serializable
    {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        private VoidSerializable()
        {
        }

    }
}
