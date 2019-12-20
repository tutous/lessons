package de.tutous.spring.boot;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import de.tutous.spring.boot.bo.DataContainerNewBO;

public class DataContainerNewBOTest
{

    private static final String EMPTY_TO = "\"\"";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DataContainerNewBOTest.class);

    @Test
    public void testValidated() throws Exception
    {
        ValidateExecuter exec = ValidateExecuter.getInstance();
        exec.execute( //
                () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().build();
                    exec.validate(bo, false);
                },
                /**
                 * name
                 */
                () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"DC\"", EMPTY_TO).build();
                    exec.validate(bo, true);
                }, () -> {
                    String to = "\"" + exec.toValue(101, "a") + "\"";
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"DC\"", to).build();
                    exec.validate(bo, true);
                },
                /**
                 * description
                 */
                () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"text\"", EMPTY_TO).build();
                    exec.validate(bo, false);
                }, () -> {
                    String to = "\"" + exec.toValue(4001, "a") + "\"";
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"text\"", to).build();
                    exec.validate(bo, true);
                },
                /**
                 * generation
                 */
                () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"2019/50\"", "\"2019/5a\"").build();
                    exec.validate(bo, true);
                },
                /**
                 * partNumber
                 */
                () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"12345678912\"", "\"1234567891x\"").build();
                    exec.validate(bo, true);
                }, () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"12345678912\"", "\"12345678\"").build();
                    exec.validate(bo, true);
                }, () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"12345678912\"", "\"123456789123\"").build();
                    exec.validate(bo, true);
                },
                /**
                 * diagnosticAddress
                 */
                () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"1A2D\"", "\"1A2\"").build();
                    exec.validate(bo, true);
                }, () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"1A2D\"", "\"1A2DE\"").build();
                    exec.validate(bo, true);
                }, () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"1A2D\"", "\"1A2G\"").build();
                    exec.validate(bo, true);
                },
                /**
                 * vehicleClasses
                 */
                () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get().replace("\"VC1\"", "\"\"").build();
                    exec.validate(bo, true);
                },
                /**
                 * members
                 */
                () -> {
                    DataContainerNewBO bo = DCNewBOBuiler.get()
                            .replace("\\{ \"userId\": 1, \"userRole\": \"DATA_CONTAINER_REPRESENTATIVE\" \\}", "").build();
                    exec.validate(bo, true);
                });
    }

}
