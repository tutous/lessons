package de.tutous.spring.boot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.tutous.spring.boot.DCNewBOBuiler;
import de.tutous.spring.boot.DCUpdBOBuiler;
import de.tutous.spring.boot.api.DcErrorCode;
import de.tutous.spring.boot.bo.DataContainerNewBO;
import de.tutous.spring.boot.bo.DataContainerUpdBO;
import de.tutous.spring.boot.common.exc.EntityNotFoundRuntimeException;
import de.tutous.spring.boot.common.session.HasSessionSupport;
import de.tutous.spring.boot.common.session.SessionInfo;
import de.tutous.spring.boot.common.type.UserRole;
import de.tutous.spring.boot.conf.AppMainConf;
import de.tutous.spring.boot.conf.AppSecurityAdapterConf;
import de.tutous.spring.boot.conf.MockMvcTestConfiguration;
import de.tutous.spring.boot.controller.DataContainerController;
import de.tutous.spring.boot.convert.DataContainerNewConverter;
import de.tutous.spring.boot.convert.DataContainerUpdConverter;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.DataContainerEntityUtil;
import de.tutous.spring.boot.domain.MemberEntityUtil;
import de.tutous.spring.boot.domain.RoleEntityUtil;
import de.tutous.spring.boot.domain.UserEntityUtil;
import de.tutous.spring.boot.domain.VehicleClassEntity;
import de.tutous.spring.boot.service.DataContainerService;
import de.tutous.spring.boot.service.VehicleClassService;

@ActiveProfiles("testmvc")
@RunWith(SpringRunner.class)
@WebMvcTest(controllers =
{ DataContainerController.class })
@ContextConfiguration(classes =
{ AppMainConf.class, AppSecurityAdapterConf.class, MockMvcTestConfiguration.class })
public class DataContainerControllerTest implements HasSessionSupport
{

    private static final String requestURL = "http://localhost:8080/rest/dc/";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private DataContainerService mockDataContainerService;
    @MockBean
    private VehicleClassService mockVehicleClassService;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testCreateWithFileAndEmptyData() throws Exception
    {

        SessionInfo info = new SessionInfo("/rest/dc/", "POST");
        register(() -> info, SessionInfo.class);

        String errorCode = DcErrorCode.INVALID_DATA_CONTAINER.getId();
        String errorMessage = DcErrorCode.INVALID_DATA_CONTAINER.getMessage(new String[]
        { DataContainerNewConverter.ERROR_MESSAGE });

        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart(requestURL) // multipart POST request
                .file(file) // RequestParam file
                .param("data", "")) // RequestParam data -> invalid JSON
                /** response expectations */
                .andExpect(status().is(400)) // must be 400 status
                .andExpect(jsonPath("$.code").value(errorCode)) //
                .andExpect(jsonPath("$.message").value(errorMessage)) //
                .andExpect(jsonPath("$.status").value("400")) //
                .andExpect(jsonPath("$.error").value("Bad Request")) //
                .andExpect(jsonPath("$.path").value(info.getCurrentRequestURI())) //
                .andExpect(jsonPath("$.method").value(info.getCurrentRequestMethod())) //
                .andExpect(jsonPath("$.logid").value(String.valueOf(info.getUuid()))) //
                .andDo(restDocument("testCreateWithFileAndEmptyData"));

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testCreateWithFileAndValidData() throws Exception
    {

        SessionInfo info = new SessionInfo("/rest/dc/", "POST");
        register(() -> info, SessionInfo.class);

        DataContainerEntity dataContainerEntity = DataContainerEntityUtil.createDataContainer("DC", new Long(1),
                new VehicleClassEntity("VC1"));
        dataContainerEntity.addResourceMember(
                MemberEntityUtil.createMember(new Long(1), UserEntityUtil.createUser(1, "firstName", "lastName"),
                        RoleEntityUtil.createNewRole(UserRole.DC_SUPERVISOR), dataContainerEntity));

        Mockito.when(mockDataContainerService.add(Mockito.any(DataContainerNewBO.class)))
                .thenReturn(dataContainerEntity);

        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart(requestURL) // multipart POST request
                .file(file) // RequestParam file
                .param("data", DCNewBOBuiler.get().getJson())); // RequestParam data -> valid JSON

        /** response expectations */
        result.andExpect(status().is(200)) // must be 200 status
                .andExpect(jsonPath("$.id").value(1))//
                .andDo(restDocument("testCreateWithFileAndValidData"));

        /** check content */
        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        Map<?, ?> actual = new ObjectMapper().readValue(jsonResponse, Map.class);
        assertThat(actual.get("name")).isEqualTo(dataContainerEntity.getName());
        assertThat(actual.get("description")).isEqualTo(dataContainerEntity.getDescription());
        assertThat(actual.get("diagnosticAddress")).isEqualTo(dataContainerEntity.getDiagnosticAddress());
        assertThat(actual.get("diagnosticBus")).isEqualTo(dataContainerEntity.getDiagnosticBus().toString());
        assertThat(actual.get("diagnosticKwp")).isEqualTo(dataContainerEntity.getDiagnosticKwp().toString());
        assertThat(actual.get("fileName")).isEqualTo(dataContainerEntity.getFileName());
        assertThat(actual.get("generation")).isEqualTo(dataContainerEntity.getGeneration());
        assertThat(actual.get("partNumber")).isEqualTo(dataContainerEntity.getPartNumber());
        assertThat(actual.get("transportProtocol")).isEqualTo(dataContainerEntity.getTransportProtocol().toString());
        assertThat(actual.get("type")).isEqualTo(dataContainerEntity.getType().toString());
        // check vehicle
        assertThat(((Collection<String>) actual.get("vehicleClasses")).stream().findFirst().get())//
                .isEqualTo(dataContainerEntity.getVehicleClasses().findFirst().get().getName());
        // cehck member 
        Map<?, ?> member = ((Collection<Map<?, ?>>) ((Map<?, ?>) actual.get("members")).get("data")).stream()
                .findFirst().get();
        assertThat(member.get("fullName")).isEqualTo("lastName, firstName");
        assertThat(member.get("userRole")).isEqualTo("DC_SUPERVISOR");

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testCreateWithFileAndUnvalidData() throws Exception
    {

        SessionInfo info = new SessionInfo("/rest/dc/", "POST");
        register(() -> info, SessionInfo.class);

        DataContainerEntity dataContainerEntity = DataContainerEntityUtil.createDataContainer(new Long(1));
        Mockito.when(mockDataContainerService.add(Mockito.any(DataContainerNewBO.class)))
                .thenReturn(dataContainerEntity);

        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());

        // invalid name attribute
        String data = DCNewBOBuiler.get().getJson().replace("\"DC\"", "\"\"");

        mockMvc.perform(MockMvcRequestBuilders.multipart(requestURL) // multipart POST request
                .file(file) // RequestParam file
                .param("data", data)) // RequestParam data -> valid JSON
                /** response expectations */
                .andExpect(status().is(400)) // must be 400 status
                .andExpect(jsonPath("$.message").value("create.dataContainerNew: DC not valid"))
                .andDo(restDocument("testCreateWithFileAndUnvalidData"));

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testUpdateWithFileAndEmptyData() throws Exception
    {

        SessionInfo info = new SessionInfo("/rest/dc/1", "PUT");
        register(() -> info, SessionInfo.class);

        String errorCode = DcErrorCode.INVALID_DATA_CONTAINER.getId();
        String errorMessage = DcErrorCode.INVALID_DATA_CONTAINER.getMessage(new String[]
        { DataContainerUpdConverter.ERROR_MESSAGE });

        mockMvc.perform(MockMvcRequestBuilders.put(requestURL + "/1") // multipart POST request
                .contentType(MediaType.MULTIPART_FORM_DATA).param("file", "content") // RequestParam file
                .param("data", "")) // RequestParam data -> invalid JSON
                /** response expectations */
                .andExpect(status().is(400)) // must be 400 status
                .andExpect(jsonPath("$.code").value(errorCode)) //
                .andExpect(jsonPath("$.message").value(errorMessage)) //
                .andExpect(jsonPath("$.status").value("400")) //
                .andExpect(jsonPath("$.error").value("Bad Request")) //
                .andExpect(jsonPath("$.path").value(info.getCurrentRequestURI())) //
                .andExpect(jsonPath("$.method").value(info.getCurrentRequestMethod())) //
                .andExpect(jsonPath("$.logid").value(String.valueOf(info.getUuid()))) //
                .andDo(restDocument("testUpdateWithFileAndEmptyData"));

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testUpateWithFileAndValidData() throws Exception
    {

        SessionInfo info = new SessionInfo("/rest/dc/1", "PUT");
        register(() -> info, SessionInfo.class);

        DataContainerEntity dataContainerEntity = DataContainerEntityUtil.createDataContainer("DC", new Long(1),
                new VehicleClassEntity("VC1"));
        dataContainerEntity.addResourceMember(
                MemberEntityUtil.createMember(new Long(1), UserEntityUtil.createUser(1, "firstName", "lastName"),
                        RoleEntityUtil.createNewRole(UserRole.DC_SUPERVISOR), dataContainerEntity));

        Mockito.when(mockDataContainerService.update(Mockito.any(DataContainerUpdBO.class)))
                .thenReturn(dataContainerEntity);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(requestURL + "/1") // multipart POST request
                .contentType(MediaType.MULTIPART_FORM_DATA) //
                .param("file", "content") // RequestParam file
                .param("data", DCUpdBOBuiler.get().getJson())); // RequestParam data -> valid JSON

        /** response expectations */
        result.andExpect(status().is(200)) // must be 200 status
                .andExpect(jsonPath("$.id").value(1))//
                .andDo(restDocument("testUpateWithFileAndValidData"));

        /** check content */
        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        Map<?, ?> actual = new ObjectMapper().readValue(jsonResponse, Map.class);
        assertThat(actual.get("name")).isEqualTo(dataContainerEntity.getName());
        assertThat(actual.get("description")).isEqualTo(dataContainerEntity.getDescription());
        assertThat(actual.get("diagnosticAddress")).isEqualTo(dataContainerEntity.getDiagnosticAddress());
        assertThat(actual.get("diagnosticBus")).isEqualTo(dataContainerEntity.getDiagnosticBus().toString());
        assertThat(actual.get("diagnosticKwp")).isEqualTo(dataContainerEntity.getDiagnosticKwp().toString());
        assertThat(actual.get("fileName")).isEqualTo(dataContainerEntity.getFileName());
        assertThat(actual.get("generation")).isEqualTo(dataContainerEntity.getGeneration());
        assertThat(actual.get("partNumber")).isEqualTo(dataContainerEntity.getPartNumber());
        assertThat(actual.get("transportProtocol")).isEqualTo(dataContainerEntity.getTransportProtocol().toString());
        assertThat(actual.get("type")).isEqualTo(dataContainerEntity.getType().toString());
        // check vehicle
        assertThat(((Collection<String>) actual.get("vehicleClasses")).stream().findFirst().get())//
                .isEqualTo(dataContainerEntity.getVehicleClasses().findFirst().get().getName());
        // cehck member 
        Map<?, ?> member = ((Collection<Map<?, ?>>) ((Map<?, ?>) actual.get("members")).get("data")).stream()
                .findFirst().get();
        assertThat(member.get("fullName")).isEqualTo("lastName, firstName");
        assertThat(member.get("userRole")).isEqualTo("DC_SUPERVISOR");

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testUpateWithFileAndDcNotFound() throws Exception
    {

        SessionInfo info = new SessionInfo("/rest/dc/1", "PUT");
        register(() -> info, SessionInfo.class);

        Mockito.when(mockDataContainerService.update(Mockito.any(DataContainerUpdBO.class)))
                .thenThrow(new EntityNotFoundRuntimeException(DcErrorCode.DATA_CONTAINER_NOT_FOUND, new String[] {}));

        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.put(requestURL + "/1") // multipart POST request
                .contentType(MediaType.MULTIPART_FORM_DATA).param("file", "content") // RequestParam file
                .param("data", DCUpdBOBuiler.get().getJson())) //
                .andExpect(status().is(404)) // must be 404 status
                .andExpect(jsonPath("$.code").value("101"))//
                .andDo(restDocument("testUpateWithFileAndDcNotFound"));

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testDeleteExistingDataContainer() throws Exception
    {

        mockMvc.perform(MockMvcRequestBuilders.delete(requestURL + "/1")) // 
                /** response expectations */
                .andExpect(status().is(204)) // 
                .andDo(restDocument("testDeleteExistingDataContainer"));

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testGetExistingDataContainer() throws Exception
    {

        DataContainerEntity dataContainerEntity = DataContainerEntityUtil.createDataContainer("DC", new Long(1),
                new VehicleClassEntity("VC1"));
        dataContainerEntity.addResourceMember(
                MemberEntityUtil.createMember(new Long(1), UserEntityUtil.createUser(1, "firstName", "lastName"),
                        RoleEntityUtil.createNewRole(UserRole.DC_ASSIGNEE), dataContainerEntity));

        Mockito.when(mockDataContainerService.getById(new Long(1))).thenReturn(dataContainerEntity);

        mockMvc.perform(MockMvcRequestBuilders.get(requestURL + "/1"))
                /** response expectations */
                .andExpect(status().is(200)) // 
                .andExpect(jsonPath("$.id").value(1))//
                .andExpect(jsonPath("$.name").value("DC"))//
                .andDo(restDocument("testGetExistingDataContainer"));

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testGetAllDataContainers() throws Exception
    {

        DataContainerEntity dataContainerEntity = DataContainerEntityUtil.createDataContainer("DC", new Long(1),
                new VehicleClassEntity("VC1"));
        dataContainerEntity.addResourceMember(
                MemberEntityUtil.createMember(new Long(1), UserEntityUtil.createUser(1, "firstName", "lastName"),
                        RoleEntityUtil.createNewRole(UserRole.DC_ASSIGNEE), dataContainerEntity));

        Mockito.when(mockDataContainerService.findAll())
                .thenReturn(() -> Arrays.asList(dataContainerEntity.toSafeBO()).stream());

        mockMvc.perform(MockMvcRequestBuilders.get(requestURL))
                /** response expectations */
                .andExpect(status().is(200)) // 
                .andExpect(jsonPath("$._embedded.data[0].id").value(1))//
                .andExpect(jsonPath("$._embedded.data[0].name").value("DC"))//
                .andDo(restDocument("testGetAllDataContainers"));

    }

    @Test
    @WithMockUser(username = "user", roles =
    { "USER" })
    public void testGetExistingDataContainerByName() throws Exception
    {

        DataContainerEntity dataContainerEntity = DataContainerEntityUtil.createDataContainer("DC", new Long(1),
                new VehicleClassEntity("VC1"));
        dataContainerEntity.addResourceMember(
                MemberEntityUtil.createMember(new Long(1), UserEntityUtil.createUser(1, "firstName", "lastName"),
                        RoleEntityUtil.createNewRole(UserRole.DC_ASSIGNEE), dataContainerEntity));

        Mockito.when(mockDataContainerService.getByName("DC")).thenReturn(dataContainerEntity);

        mockMvc.perform(MockMvcRequestBuilders.get(requestURL + "/name/DC"))
                /** response expectations */
                .andExpect(status().is(200)) // 
                .andExpect(jsonPath("$.id").value(1))//
                .andExpect(jsonPath("$.name").value("DC"))//
                .andDo(restDocument("testGetExistingDataContainerByName"));

    }

    private static RestDocumentationResultHandler restDocument(String name)
    {
        return document(name, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }

}
