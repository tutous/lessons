package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.domain.VehicleClassEntity;

public class VehicleClassEntityUtil
{

    public static VehicleClassEntity createVehicleClass(Integer id, String name)
    {
        VehicleClassEntity entity = new VehicleClassEntity(id, name);
        return entity;
    }

    public static VehicleClassEntity createNewVehicleClass(String name)
    {
        Integer id = null;
        return createVehicleClass(id, name);
    }

}
