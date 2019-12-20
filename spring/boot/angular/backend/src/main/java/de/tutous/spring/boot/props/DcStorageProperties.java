package de.tutous.spring.boot.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class DcStorageProperties
{

    /**
     * Folder location for storing files
     */
    @Value("upload-dir")
    private String uploadDir;

    public String getUploadDir()
    {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir)
    {
        this.uploadDir = uploadDir;
    }

}