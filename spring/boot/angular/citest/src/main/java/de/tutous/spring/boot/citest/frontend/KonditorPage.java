package de.tutous.spring.boot.citest.frontend;

public enum KonditorPage
{

	DATA_CONTAINER_CREATE("dataContainerCreate"),
    DATA_CONTAINER_EDIT("dataContainerEdit"),
    DATA_CONTAINER_DETAIL_VIEW("dataContainerDetailView");

    String pageId;

    private KonditorPage(String pageId)
    {
        this.pageId = pageId;
    }
    
    @Override
    public String toString()
    {
        return pageId;
    }

}
