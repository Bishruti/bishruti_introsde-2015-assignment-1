package people;

import people.generated.*;

import javax.xml.bind.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class JAXBMarshaller {
    /* Generates XML Document
      Expected Input: File
      Expected Output: XMLDocument
   */
	public void generateXMLDocument(File xmlDocument) {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance("people.generated");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
			people.generated.ObjectFactory factory = new people.generated.ObjectFactory();

            PeopleType people = factory.createPeopleType();

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

			marshaller.marshal(factory.createPeople(people),
					new FileOutputStream(xmlDocument));

		} catch (IOException e) {
			System.out.println(e.toString());

		} catch (JAXBException e) {
			System.out.println(e.toString());

		}

	}

    /* Creates Object Person
       Expected Input: Object, String, String, String, Object
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

    /* Creates Object healthProfile
       Expected Input: Object, String, Float, Float, Float
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

    /* Executes JAXBMarshaller to generate people.xml file */

	public static void main(String[] argv) {
		String xmlDocument = "people.xml";
		JAXBMarshaller jaxbMarshaller = new JAXBMarshaller();
		jaxbMarshaller.generateXMLDocument(new File(xmlDocument));
	}
}
