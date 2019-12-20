package de.tutous.spring.boot;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.tutous.spring.boot.api.MemberOut;
import de.tutous.spring.boot.bo.MemberBO;
import de.tutous.spring.boot.bo.MemberBOM;
import de.tutous.spring.boot.bo.MemberBOMSAssembler;
import de.tutous.spring.boot.bo.RoleBO;
import de.tutous.spring.boot.bo.UserBO;
import de.tutous.spring.boot.common.api.CollectionModel;
import de.tutous.spring.boot.common.type.UserRole;
import de.tutous.spring.boot.domain.RoleEntityUtil;
import de.tutous.spring.boot.domain.UserEntityUtil;

public class MemberBOMSAssemblerTest
{

    @Test
    public void test() throws JsonProcessingException
    {

        MemberBO bo = new MemberBO()
        {

            @Override
            public UserRole getUserRole()
            {
                return UserRole.DC_ASSIGNEE;
            }

            @Override
            public UserBO getUser()
            {
                return UserEntityUtil.createUser(1);
            }

            @Override
            public RoleBO getRole()
            {
                return RoleEntityUtil.createRole(1, UserRole.DC_ASSIGNEE);
            }

            @Override
            public Long getId()
            {
                return new Long(1);
            }

            @Override
            public String getFullName()
            {
                return "fullname";
            }
        };

        Collection<MemberBOM> bos = Arrays.asList(new MemberBOM[]
        { new MemberBOM(bo) });

        CollectionModel<MemberOut> boms = new MemberBOMSAssembler(new Long(1)).toModel(bos);

        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println(om.writeValueAsString(boms));
    }

}
