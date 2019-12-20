package de.tutous.spring.boot.domain;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import de.tutous.spring.boot.bo.VehicleClassBO;
import de.tutous.spring.boot.common.type.DataContainerType;
import de.tutous.spring.boot.common.type.DiagnosticBus;
import de.tutous.spring.boot.common.type.DiagnosticKwp;
import de.tutous.spring.boot.common.type.TransportProtocol;
import de.tutous.spring.boot.domain.DataContainerEntity;

public class DataContainerEntityUtil
{

    public static DataContainerEntity createDataContainer(Long id, String name, VehicleClassBO... vehicleClasses)
    {

        Stream<VehicleClassBO> vehicleClassStream = Arrays.asList(vehicleClasses).stream();

        Long index = new Random().nextLong();
        DataContainerEntity entity = new DataContainerEntity(id,
                /** name */
                name,
                /** description */
                "description " + index,
                /** type */
                DataContainerType.ZDC,
                /** vehicle classes */
                vehicleClassStream,
                /** generation */
                "2019/50",
                /** partNumber */
                "12345678912",
                /** diagnosticBus */
                DiagnosticBus.CAN,
                /** diagnosticAddress */
                "1A2D",
                /** diagnosticKwp */
                DiagnosticKwp.UDS,
                /** transportProtocol */
                TransportProtocol.TP2_0);
        return entity;
    }

    public static DataContainerEntity createDataContainer(Long id, VehicleClassBO... vehicleClasses)
    {
        String name = "DC " + new Random().nextLong();

        return createDataContainer(id, name, vehicleClasses);
    }
    
    public static DataContainerEntity createDataContainer(String name, Long id, VehicleClassBO... vehicleClasses)
    {
        return createDataContainer(id, name, vehicleClasses);
    }

    public static DataContainerEntity createNewDataContainer(VehicleClassBO... vehicleClasses)
    {
        String name = "DC " + new Random().nextLong();

        return createNewDataContainer(name, vehicleClasses);
    }

    public static DataContainerEntity createNewDataContainer(String name, VehicleClassBO... vehicleClasses)
    {
        Long id = null;
        return createDataContainer(id, name, vehicleClasses);
    }

}
