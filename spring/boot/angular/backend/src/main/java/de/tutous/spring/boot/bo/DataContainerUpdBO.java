package de.tutous.spring.boot.bo;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.tutous.spring.boot.api.DcErrorCode;
import de.tutous.spring.boot.api.dc.DataContainerInUpd;
import de.tutous.spring.boot.common.bo.BusinessObject;
import de.tutous.spring.boot.common.exc.BusinessRuntimeException;
import de.tutous.spring.boot.common.log.ToString;
import de.tutous.spring.boot.common.type.DiagnosticBus;
import de.tutous.spring.boot.common.type.TransportProtocol;
import de.tutous.spring.boot.common.validation.ParamLogger;
import de.tutous.spring.boot.common.validation.ValidationConstants;

public class DataContainerUpdBO implements BusinessObject<DataContainerUpdBO, Long>, ToString, DataContainerInUpd
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private MultipartFile file;

    @JsonIgnore
    @ToStringProperty
    private Long id;

    @NotEmpty(payload =
    { ParamLogger.class })
    @Length(max = 100)
    @ToStringProperty
    private String name;

    @Length(max = 4000)
    @ToStringProperty
    private String description;

    @NotEmpty(payload =
    { ParamLogger.class })
    @Pattern(regexp = ValidationConstants.YEAR_KW_PATTERN)
    @ToStringProperty
    private String generation;

    @NotEmpty(payload =
    { ParamLogger.class })
    @Length(min = 9, max = 11)
    @Pattern(regexp = ValidationConstants.HEX_PATTERN)
    @ToStringProperty
    private String partNumber;

    @NotNull(payload =
    { ParamLogger.class })
    @ToStringProperty
    private DiagnosticBus diagnosticBus;

    @NotEmpty(payload =
    { ParamLogger.class })
    @Length(min = 4, max = 4)
    @Pattern(regexp = ValidationConstants.HEX_PATTERN)
    @ToStringProperty
    private String diagnosticAddress;

    /** optional */
    @ToStringProperty
    private TransportProtocol transportProtocol;
    
    @Size(min = 1, payload =
    { ParamLogger.class })
    @ToStringProperty
    private Collection<String> vehicleClasses;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getGeneration()
    {
        return generation;
    }

    public String getPartNumber()
    {
        return partNumber;
    }

    public DiagnosticBus getDiagnosticBus()
    {
        return diagnosticBus;
    }

    public String getDiagnosticAddress()
    {
        return diagnosticAddress;
    }

    public TransportProtocol getTransportProtocol()
    {
        return transportProtocol;
    }

    @Override
    public DataContainerUpdBO toSafeBO()
    {
        return this;
    }

    public String getFileName()
    {
        return StringUtils.cleanPath(file.getOriginalFilename());
    }

    public byte[] getFileContent()
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

    public void setFile(MultipartFile file)
    {
        this.file = file;
    }

    public boolean isFile()
    {
        return Objects.nonNull(this.file);
    }

    public Collection<String> getVehicleClasses()
    {
        return vehicleClasses;
    }

}
