package people;

import people.generated.*;

import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

public class JAXBUnMarshaller {
    /* Outputs Object Person Details
       Expected Input: XMLDocument
       Expected Output: Object
   */
	public void unMarshall(File xmlDocument) {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance("people.generated");

			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File(
					"people_data.xsd"));
			unMarshaller.setSchema(schema);
			CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
			unMarshaller.setEventHandler(validationEventHandler);

			JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller
					.unmarshal(xmlDocument);

			PeopleType people = peopleElement.getValue();

			List<PersonType> personList = people.getPerson();

            for (int i = 0; i < personList.size(); i++) {
                PersonType person = (PersonType) personList.get(i);
				printPerson(person);
            }

		}
        catch (JAXBException e) {
			System.out.println(e.toString());
		}
        catch (SAXException e) {
			System.out.println(e.toString());
		}
	}

    /* Displays the Class attributes
       Expected Input: Object
       Expected Output:
        Firstname: String
        Lastname: String
        Birthday: String
        LastUpdate: String
        Weight: float
        Height: float
        BMI: float
    */
	public static void printPerson(PersonType person) {
		System.out.println(person.getFirstname());
		System.out.println(person.getLastname());
		System.out.println(person.getBirthdate());
		HealthProfileType healthProfileDetail = (HealthProfileType) person.getHealthprofile();
		System.out.println(healthProfileDetail.getLastupdate());
		System.out.println(healthProfileDetail.getHeight());
		System.out.println(healthProfileDetail.getWeight());
		System.out.println(healthProfileDetail.getBmi());
	}

    /* Executes JAXBUnMarshaller to generate Java Object */
	public static void main(String[] argv) {
		File xmlDocument = new File("people.xml");
		JAXBUnMarshaller jaxbUnmarshaller = new JAXBUnMarshaller();

		jaxbUnmarshaller.unMarshall(xmlDocument);

	}

	class CustomValidationEventHandler implements ValidationEventHandler {
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() == ValidationEvent.WARNING) {
				return true;
			}
			if ((event.getSeverity() == ValidationEvent.ERROR)
					|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

				System.out.println("Validation Error:" + event.getMessage());

				ValidationEventLocator locator = event.getLocator();
				System.out.println("At line number:" + locator.getLineNumber());
				System.out.println("Unmarshalling Terminated");
				return false;
			}
			return true;
		}

	}
}
