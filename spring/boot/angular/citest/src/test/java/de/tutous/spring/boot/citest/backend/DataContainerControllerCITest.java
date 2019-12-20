package de.tutous.spring.boot.citest.backend;

import static de.tutous.spring.boot.citest.backend.ResponseEntityBuilder.*;
import static de.tutous.spring.boot.citest.backend.api.DataContainerUtil.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.tutous.spring.boot.citest.backend.BackendTestManager;
import de.tutous.spring.boot.citest.backend.MemberRole;

public class DataContainerControllerCITest
{

    // @Test
    public void testCreate() throws URISyntaxException, JsonProcessingException
    {

        BackendTestManager.execute(MemberRole.DC_MEMBER, env -> {

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", getTestFile());
            body.add("data", createDataContainerAsJson());

            create(env.getBaseUrl() + "/rest/dc/", env.getUserName(), env.getUserPwd())//
                    .setContentType(MediaType.MULTIPART_FORM_DATA)//
                    .setAccept(Arrays.asList(MediaType.APPLICATION_JSON))//
                    .postForEntity(body, Object.class);

        });

    }

    private FileSystemResource getTestFile() throws URISyntaxException
    {
        URI uri = DataContainerControllerCITest.class.getResource("/testfile.txt").toURI();
        return new FileSystemResource(Paths.get(uri));
    }

}
