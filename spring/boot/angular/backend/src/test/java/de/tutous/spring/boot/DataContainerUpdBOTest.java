package de.tutous.spring.boot;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import de.tutous.spring.boot.bo.DataContainerUpdBO;

public class DataContainerUpdBOTest
{

    private static final String EMPTY_TO = "\"\"";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DataContainerUpdBOTest.class);

    @Test
    public void testValidated() throws Exception
    {
        ValidateExecuter exec = ValidateExecuter.getInstance();
        exec.execute( //
                () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().build();
                    exec.validate(bo, false);
                },
                /**
                 * name
                 */
                () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"DC\"", EMPTY_TO).build();
                    exec.validate(bo, true);
                }, () -> {
                    String to = "\"" + exec.toValue(101, "a") + "\"";
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"DC\"", to).build();
                    exec.validate(bo, true);
                },
                /**
                 * description
                 */
                () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"text\"", EMPTY_TO).build();
                    exec.validate(bo, false);
                }, () -> {
                    String to = "\"" + exec.toValue(4001, "a") + "\"";
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"text\"", to).build();
                    exec.validate(bo, true);
                },
                /**
                 * generation
                 */
                () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"2019/50\"", "\"2019/5a\"").build();
                    exec.validate(bo, true);
                },
                /**
                 * partNumber
                 */
                () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"12345678912\"", "\"1234567891x\"").build();
                    exec.validate(bo, true);
                }, () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"12345678912\"", "\"12345678\"").build();
                    exec.validate(bo, true);
                }, () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"12345678912\"", "\"123456789123\"").build();
                    exec.validate(bo, true);
                },
                /**
                 * diagnosticAddress
                 */
                () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"1A2D\"", "\"1A2\"").build();
                    exec.validate(bo, true);
                }, () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"1A2D\"", "\"1A2DE\"").build();
                    exec.validate(bo, true);
                }, () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"1A2D\"", "\"1A2G\"").build();
                    exec.validate(bo, true);
                },
                /**
                 * vehicleClasses
                 */
                () -> {
                    DataContainerUpdBO bo = DCUpdBOBuiler.get().replace("\"VC1\"", "\"\"").build();
                    exec.validate(bo, true);
                });
    }

}
