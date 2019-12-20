package de.tutous.spring.boot.bo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.hateoas.Link;

import de.tutous.spring.boot.common.bo.BusinessObjectModelAssembler;
import de.tutous.spring.boot.controller.DataContainerController;

public class DataContainerBOMAssembler extends BusinessObjectModelAssembler<DataContainerBOM, DataContainerBO, Long>
{

    public DataContainerBOMAssembler()
    {
        super(DataContainerController.class, DataContainerBOM.class);
    }

    @Override
    public DataContainerBOM toModel(DataContainerBO bo)
    {
        DataContainerBOM model = new DataContainerBOM(bo);
        model.add(createLinks(bo.getId(), bo.getName()));
        return model;
    }

    private Collection<Link> createLinks(Long id, String name)
    {
        return Arrays.asList(
                /** self rel */
                linkTo(methodOn(DataContainerController.class).getById(id)).withSelfRel(),
                /** name rel */
                linkTo(methodOn(DataContainerController.class).getByName(name)).withRel("name"),
                /** name rel */
                linkTo(methodOn(DataContainerController.class).delete(id)).withRel("delete"),
                /** all rel */
                linkTo(methodOn(DataContainerController.class).getAllDc()).withRel("all"));
    }

}
