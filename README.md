#### Introduction to Service Design and Engineering Assignment 1

#### Introduction

This consists of some of the basics of XML, XSD, XPATH, Marshalling, Unmarshalling XML and converting to JSON using JAXB XJC.

#### Structure

The root folder consists of the following:

**Source Folder (src)**
This folder possess the necessary Java files for execution of the program. It mainly consists the following sub folders and file: 

*People*

This folder has the following java classes:

1. `JAXBMarshaller.java`: Performs `JAXB XJC Marshalling`

2. `JAXBUnMarshaller.java`: Performs `JAXB XJC UnMarshalling`

3. `JAXBJson.java`: Converts java application to `JSON`

*pojos*

This folder has the following java classes:

`1. Person.java`: Consists of the detail information on `Person` class.

`2. HealthProfile.java`: Consists of the detail information on `HealthProfile class.

*HealthProfileReader.java*

Consists of all the functions to perform various actions like

1. Returns weight and height of the person of given personId.

2. Prints all people in the list with their details.

3. Returns HealthProfile of given personId

4. Returns weight of person who satisfies the given condition.

**build.xml**

It is a low-level mechanism to package. It compiles and archives source code into a single `JAR` file.

**ivy.xml**

 Contains description of the dependencies of a module, its published artifacts and its configurations.
 
**people_data.xml**

XML file for the given list of people and their details.

 **people_data.xsd**
 
 XML Schema for `people_data.xml`
 
 **people.xml**
 
 Output for file of `JAXBMarshaller`
 
 **people.json**
 
 Output for file of `JAXBJson`

#### Program Execution
1. Open the terminal .
2. Go to root directory of the program.
3. Run `ant execute.evaluation`
 
