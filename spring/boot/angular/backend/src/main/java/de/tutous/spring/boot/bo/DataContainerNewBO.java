package de.tutous.spring.boot.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.tutous.spring.boot.api.DcErrorCode;
import de.tutous.spring.boot.api.dc.DataContainerInNew;
import de.tutous.spring.boot.common.exc.BusinessRuntimeException;
import de.tutous.spring.boot.common.log.ToString;
import de.tutous.spring.boot.common.type.DataContainerType;
import de.tutous.spring.boot.common.type.DiagnosticKwp;
import de.tutous.spring.boot.common.validation.ParamLogger;

public class DataContainerNewBO extends DataContainerUpdBO implements ToString, DataContainerInNew
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private MultipartFile file;

    @NotNull(payload =
    { ParamLogger.class })
    @ToStringProperty
    private DataContainerType type;

    @NotNull(payload =
    { ParamLogger.class })
    @ToStringProperty
    private DiagnosticKwp diagnosticKwp;

    @Valid()
    @Size(min = 1, payload =
    { ParamLogger.class })
    @ToStringProperty
    @JsonDeserialize(as = CollectionMemberNewBO.class)
    private Collection<MemberNewBO> members;

    public DataContainerNewBO()
    {
        this.members = new ArrayList<MemberNewBO>();
    }

    public String getFileName()
    {
        if (isFile())
        {
            return StringUtils.cleanPath(file.getOriginalFilename());
        }
        else
        {
            return null;
        }
    }

    public byte[] getFileContent()
    {
        if (isFile())
        {
            try
            {
                return this.file.getBytes();
            }
            catch (IOException e)
            {
                // TODO: test this
                throw new BusinessRuntimeException(e, DcErrorCode.INVALID_DATA_CONTAINER, new String[]
                { "can't get file content" });
            }
        }
        else
        {
            return new byte[] {};
        }
    }

    public void setFile(MultipartFile file)
    {
        this.file = file;
    }

    public DataContainerType getType()
    {
        return type;
    }

    public DiagnosticKwp getDiagnosticKwp()
    {
        return diagnosticKwp;
    }

    public Collection<MemberNewBO> getResourceMembers()
    {
        return members;
    }

    @Override
    public String toString()
    {
        return propertiesToString();
    }

    public static class CollectionMemberNewBO extends ArrayList<MemberNewBO>
    {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

    }

    public boolean isFile()
    {
        return Objects.nonNull(this.file);
    }

}
