package com.wdxxl.xml.sax.employee.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.wdxxl.xml.sax.employee.handler.SaxEmployeeHandler;
import com.wdxxl.xml.sax.employee.model.Employee;

import org.xml.sax.SAXException;

public class XMLParserSAX {
    public static void main(String args[]) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxEmployeeHandler handler = new SaxEmployeeHandler();
            saxParser.parse(new File(System.getProperty("user.dir")
                    + "/src/test/resources/employees.xml"),
                    handler);
            // Get Employees list
            List<Employee> empList = handler.getEmpList();
            // print employee information
            for (Employee emp : empList) {
                System.out.println(emp);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
