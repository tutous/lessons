package de.tutous.spring.boot.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.collectionjson.Jackson2CollectionJsonModule;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class BasicHateoasTest
{

    //	@Test
    //	public void testEmployeeModelEntity() throws JsonProcessingException {
    //		ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    //
    //		// hateoas RepresentationModelAssemblerSupport
    //		EmployeeEntityModelAssembler<Employee> assembler = new EmployeeEntityModelAssembler<Employee>();
    //
    //		System.out.println(om.writeValueAsString(assembler.toModel(new Employee(1, "foreName", "lastName"))));
    //
    //	}

    //@Test
    public void testEmployeeRepresentationModelAssembler() throws JsonProcessingException
    {
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        om.registerModule(new Jackson2CollectionJsonModule());

        // EmployeeRepresentationModelAssembler
        EmployeeRepresentationModelAssembler assembler = new EmployeeRepresentationModelAssembler();

        System.out.println(om.writeValueAsString(assembler.toModel(new Employee(1, "foreName", "lastName"))));

        System.out.println(om.writeValueAsString(
                assembler.toCollectionModel(Arrays.asList(new Employee(1, "foreName", "lastName")))));

    }

    //@Test
    public void testEmployeeRepresentationModel() throws JsonProcessingException
    {
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        // EmployeeRepresentationModel
        EmployeeRepresentationModel model = new EmployeeRepresentationModel(new Employee(1, "foreName", "lastName"));
        model.add(new Link("/employee/01"));

        System.out.println(om.writeValueAsString(model));

    }

    /**
     * classes
     *
     */

    //	static class EmployeeEntityModelAssembler<T extends Employee>
    //			extends RepresentationModelAssemblerSupport<T, EntityModel<T>> {
    //
    //		public EmployeeEntityModelAssembler() {
    //			super(EmployeeController.class,EntityModel<T>.class);
    //		}
    //
    //		@Override
    //		public EntityModel<T> toModel(T entity) {
    //			EntityModel<T> model = new EntityModel<T>(entity);
    //			model.add(linkTo(methodOn(EmployeeController.class).get(entity.id)).withSelfRel());
    //			model.add(linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees"));
    //			return model;
    //		}
    //
    //	}

    public static class EmployeeRepresentationModelAssembler
            extends RepresentationModelAssemblerSupport<Employee, EmployeeRepresentationModel>
    {

        public EmployeeRepresentationModelAssembler()
        {
            super(EmployeeController.class, EmployeeRepresentationModel.class);
        }

        @Override
        public EmployeeRepresentationModel toModel(Employee entity)
        {
            EmployeeRepresentationModel model = new EmployeeRepresentationModel(entity);
            model.add(linkTo(methodOn(EmployeeController.class).get(entity.id)).withSelfRel());
            model.add(linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees"));
            return model;
        }

    }

    @Relation(collectionRelation = "employees", itemRelation = "employee")
    public static class EmployeeRepresentationModel extends RepresentationModel<EmployeeRepresentationModel>
    {

        @JsonProperty
        private int id;
        @JsonProperty
        private String foreName;
        @JsonProperty
        private String lastName;

        public EmployeeRepresentationModel(Employee employee)
        {
            this.foreName = employee.foreName;
            this.lastName = employee.lastName;
            this.id = employee.id;
        }

    }

    @RestController
    @RequestMapping("employee")
    public static class EmployeeController
    {

        @GetMapping(path = "/")
        public Iterable<Employee> getAll()
        {
            return new ArrayList();
        }

        @PostMapping(path = "/{id}")
        public Employee get(@PathVariable int id)
        {
            return null;
        }

    }

    public static class Employee
    {

        int id;
        String foreName;
        String lastName;

        public Employee(int id, String foreName, String lastName)
        {
            super();
            this.id = id;
            this.foreName = foreName;
            this.lastName = lastName;
        }
    }

}
