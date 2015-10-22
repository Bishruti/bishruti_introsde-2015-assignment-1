import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import people.generated.HealthProfileType;
import people.generated.ObjectFactory;
import people.generated.PeopleType;
import people.generated.PersonType;

public class JAXBJson {
    public static PersonType person = new PersonType();
    public static PeopleType people = new PeopleType();

    /* Initialize Database with Class Attributes
       Expected Input: -
       Expected Output: Object
    */

    public static void initializeDB() {

        people.generated.ObjectFactory factory = new people.generated.ObjectFactory();

        people = factory.createPeopleType();

        HealthProfileType healthProfile = healthProfileInfo(factory,"2014-05-08T21:16:51.000+2:00", 1.88F, 80F, 24.9F);
        PersonType person = personInfo(factory, "Ned", "Stark", "1973-06-28T15:19:44.000+2:00", healthProfile);
        people.getPerson().add(person);

        healthProfile = healthProfileInfo(factory,"2015-09-09T01:51:53.000+2:00", 1.78F, 72F, 23.1F);
        person = personInfo(factory, "Cersi", "Lannister", "1976-06-25T15:30:14.000+2:00", healthProfile);
        people.getPerson().add(person);

        healthProfile = healthProfileInfo(factory,"2014-10-18T17:53:31.000+2:00", 1.89F, 88F, 22.0F);
        person = personInfo(factory, "Jon", "Snow", "1973-01-04T01:33:45.000+2:00", healthProfile);
        people.getPerson().add(person);

        healthProfile = healthProfileInfo(factory,"2015-10-06T15:16:34.000+2:00", 1.81F, 75F, 21.6F);
        person = personInfo(factory, "Danarys", "Targeryan", "1989-06-23T05:25:35.000+2:00", healthProfile);
        people.getPerson().add(person);

        healthProfile = healthProfileInfo(factory,"2015-05-14T21:00:17.000+2:00", 1.86F, 83F, 20.9F);
        person = personInfo(factory, "Margery", "Tyrell", "1977-02-02T17:32:38.000+2:00", healthProfile);
        people.getPerson().add(person);

    }

    /* Sets value for Object (Person)
       Expected Input: ObjectFactory, String, String, String, Object
       Expected Output: Object
    */

    public static PersonType personInfo(ObjectFactory factory, String firstname, String lastname, String birthdate, HealthProfileType healthprofile) {
        PersonType person = factory.createPersonType();
        person.setFirstname(firstname);
        person.setLastname(lastname);
        person.setBirthdate(birthdate);
        person.setHealthprofile(healthprofile);
        return person;
    }

    /* Sets value for Object (HealthProfile)
       Expected Input: ObjectFactory, String, Float, Float, Float
       Expected Output: Object
    */

    public static HealthProfileType healthProfileInfo(ObjectFactory factory, String lastupdate, Float height, Float weight, Float bmi) {
        HealthProfileType healthProfile = factory.createHealthProfileType();
        healthProfile.setLastupdate(lastupdate);
        healthProfile.setHeight(height);
        healthProfile.setWeight(weight);
        healthProfile.setBmi(bmi);
        return healthProfile;
    }

    /* Executes JAXBJson to generate people.json file */

    public static void main(String[] args) throws Exception {

        initializeDB();

        // Jackson Object Mapper
        ObjectMapper mapper = new ObjectMapper();

        // Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();

        mapper.registerModule(module);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(new File("people.json"), people);
    }
}