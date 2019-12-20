package de.tutous.spring.boot.common.api;

public class CollectionModelTest
{

//    @Test
//    public void test() throws JsonProcessingException
//    {
//
//        Iterable<TestBO> model = new CollectionModelTestBO(Arrays.asList(new TestBO("test")));
//
//        ObjectMapper om = new ObjectMapper();
//        om.enable(SerializationFeature.INDENT_OUTPUT);
//        System.out.println(om.writeValueAsString(model));
//
//    }
//
//    public class CollectionModelTestBO implements CollectionModel<TestBO, TestIF>
//    {
//
//        Collection<TestBO> values;
//
//        protected CollectionModelTestBO(Collection<TestBO> values)
//        {
//            this.values = values;
//        }
//
//        @Override
//        public Iterable<TestBO> getBoms()
//        {
//            return values;
//        }
//
//        public Links getLinks()
//        {
//            return Links.of(Arrays.asList(new Link("self", "http://test.de")));
//        }
//
//    }
//
//    public interface TestIF
//    {
//        @JsonGetter("name")
//        public String getName();
//    }
//
//    public class TestBO extends BusinessObjectModel<TestBO, Integer> implements TestIF
//    {
//
//        private String name;
//
//        public TestBO(String name)
//        {
//            super(this);
//            this.name = name;
//        }
//
//        public String getName()
//        {
//            return name;
//        }
//
//    }

}
