import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import javax.sound.midi.Soundbank;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pojos.HealthProfile;
import pojos.Person;

public class HealthProfileReader {
	Document doc;
	XPath xpath;

	public void loadXML() throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		doc = builder.parse("people_data.xml");
        //creating xpath object
		getXPathObj();
	}

	public XPath getXPathObj() {

		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		return xpath;
	}

	public Person getPersonByName(String personFirstName, String personLastName) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("/people/person[firstname='" + personFirstName + "' and lastname='" + personLastName + "']");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		NodeList personDetails = node.getChildNodes();
		Person person = createPerson(personDetails);
		return person;
	}

    /* Question 3.1.a. Returns weight of the person of given personId.
       Expected Input: Long
       Expected Output: Double
    */
    public double getWeightById(Long personId) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + personId + "']/healthprofile/weight/text()");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        return Double.parseDouble(node.getNodeValue());
    }

    /* Question 3.1.b. Returns height of the person of given personId.
       Expected Input: Long
       Expected Output: Double
    */
    public double getHeightById(Long personId) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + personId + "']/healthprofile/height/text()");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        return Double.parseDouble(node.getNodeValue());
    }

    /* Question 3.2
       Obtains the list of all the persons and returns the list.
       Expected Input: -
       Expected Output: NodeList
    */
    public NodeList getPersonList() throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person");
        NodeList nodelist = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return nodelist;
    }

    /* Loops through the node list and prints the node items.
       Expected Input: NodeList (Not User Input)
       Expected Output: NodeItem
    */

    public void printPerson(NodeList nodelist) throws XPathExpressionException {
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Person person = createPerson(nodelist.item(i).getChildNodes());
            displayPersonInfo(person);
        }
    }

    /* Displays the node item attributes (Class attributes).
       Expected Input: NodeItem
       Expected Output:
        ********************************************
        Details of PersonId: Long
        ********************************************
        Firstname: String
        Lastname: String
        Birthday: String
        LastUpdate: String
        Weight: double
        Height: double
        BMI: double
    */

    public void  displayPersonInfo(Person person) throws XPathExpressionException {
        System.out.println("********************************************");
        System.out.println("Details of PersonId: " + person.getPersonId());
        System.out.println("********************************************");
        System.out.println("Firstname: "+person.getFirstname());
        System.out.println("Lastname: "+person.getLastname());
        System.out.println("Birthday: "+person.getBirthdate());
        displayHealthprofile(person.gethProfile());
    }

    /* Creates Person with the information obtained from the Nodelist
       Expected Input: Nodelist
       Expected Output: Object
    */

    private static Person createPerson(NodeList personDetails) {
        Person person = new Person();
        for (int k = 0; k < personDetails.getLength(); k++) {
            Node child = personDetails.item(k);
            if (child.getNodeType() != Node.TEXT_NODE)
            {
                if (child.getNodeName() == "firstname")
                    person.setFirstname(child.getFirstChild().getNodeValue());
                if (child.getNodeName() == "lastname")
                    person.setLastname(child.getFirstChild().getNodeValue());
                if (child.getNodeName() == "birthdate")
                    person.setBirthdate(child.getFirstChild().getNodeValue());
                if (child.getNodeName() == "healthprofile")
                {
                    NodeList healthProfileDetails = child.getChildNodes();
                    HealthProfile hp = createHealthProfile(healthProfileDetails);
                    person.sethProfile(hp);

                }
            }
        }
        return person;
    }

    /* Creates HealthProfile with the information obtained from the Nodelist
       Expected Input: Nodelist
       Expected Output: Object
   */
    private static HealthProfile createHealthProfile(NodeList healthProfileDetails)
    {
        HealthProfile healthProfile = new HealthProfile();
        for (int k = 0; k < healthProfileDetails.getLength(); k++)
        {
            Node child = healthProfileDetails.item(k);
            if (child.getNodeType() != Node.TEXT_NODE)
            {
                if (child.getNodeName() == "lastupdate")
                    healthProfile.setLastupdate(child.getFirstChild().getNodeValue());
                if (child.getNodeName() == "weight")
                    healthProfile.setWeight(Double.parseDouble(child.getFirstChild().getNodeValue()));
                if (child.getNodeName() == "height")
                    healthProfile.setHeight(Double.parseDouble(child.getFirstChild().getNodeValue()));
            }
        }
        return healthProfile;
    }

   /* Question 3.3
      Obtains person with input personId
      Expected Input: Long
      Expected Output: Nodes
   */

    public NodeList getHealthProfilebyId(Long personId) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + personId + "']/healthprofile");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return nodes;
    }

    /* Prints HealthProfile of given personId
       Expected Input: Long
       Expected Output: NodeItem
    */

    public void printHealthProfile(Long PersonId) throws XPathExpressionException
    {
        NodeList nodelist = getHealthProfilebyId(PersonId);
        for (int i = 0; i < nodelist.getLength(); i++)
        {
            HealthProfile healthProfile = createHealthProfile(nodelist.item(i).getChildNodes());
            System.out.println("\n*********************************************");
            System.out.println("HealthProfile Detail of PersonId: "+PersonId);
            System.out.println("\n*********************************************");
            displayHealthprofile(healthProfile);
        }

    }

    /* Displays the node item attributes (Class attributes).
       Expected Input: NodeItem
       Expected Output:
        LastUpdate: String
        Weight: double
        Height: double
        BMI: double
    */

    public void  displayHealthprofile(HealthProfile healthProfile) throws XPathExpressionException {
        System.out.println("LastUpdate: "+healthProfile.getLastupdate());
        System.out.println("Weight: "+healthProfile.getWeight());
        System.out.println("Height: "+healthProfile.getHeight());
        System.out.println("BMI: "+healthProfile.getBMI());
    }

    /* Question 3.4
        Returns weight of person who satisfies the given condition.
        Expected Input: String, String
        Expected Output: NodeList
    */
    public NodeList getPersonByWeightRange(String weight, String condition) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person/healthprofile[weight" + condition +" ' " +weight+"']/parent::*");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return nodes;
    }

	/**
	 * The health profile reader gets information from the command line about
	 * weight and height and calculates the BMI of the person based on this
	 * parameters
	 *
	 * @param args
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException {
		HealthProfileReader test = new HealthProfileReader();
		test.loadXML();

        /* Question 3.1 Prints Weight and Height of Person with PersonId 5
           Expected Input: Long
           Expected Output:
           Weight of person with personId 5 is 83.0kg
           Height of person with personId 5 is 1.86m
        */

        long personid = 5;
        System.out.println("Weight of person with personId " + personid + " is " + test.getWeightById(personid) + "kg");
        System.out.println("Height of person with personId " + personid + " is " + test.getHeightById(personid) + "m");

        /* Question 3.2 Prints all the people in the list and their details
            Expected Input: -
            Expected Output:
            List in the format below:
            ********************************************
            Details of PersonId: 4188
            ********************************************
            Firstname: Filippo
            Lastname: Costa
            Birthday: 1991-08-13T21:14:50.000+2:00
            LastUpdate: 2014-02-03T08:48:46.000+2:00
            Weight: 70.0
            Height: 1.75
            BMI: 22.857142857142858
        */
        test.printPerson(test.getPersonList());

        /* Question 3.3 Prints HealthProfile of the Person with the personId 5
            Expected Input: Long
            Expected Output:
            *********************************************
            HealthProfile Detail of PersonId: 5
            *********************************************
            LastUpdate: 2015-05-14T21:00:17.000+2:00
            Weight: 83.0
            Height: 1.86
            BMI: 23.991212856977683
         */

        test.printHealthProfile(personid);

        /* Question 3.4 Prints Person and Details of the person who fulfills the given weight criteria.
            Expected Input: 90,>
            Expected Output:
            ********************************************
            Details of PersonId: 8051
            ********************************************
            Firstname: Gabriele
            Lastname: Aldegani
            Birthday: 1998-03-15T03:23:54.000+2:00
            LastUpdate: 2014-06-02T00:15:24.000+2:00
            Weight: 92.0
            Height: 1.95
            BMI: 24.194608809993426
        */

        test.printPerson(test.getPersonByWeightRange("90",">"));

	}
  }