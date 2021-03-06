package com.worktoken.helloworld;

import com.worktoken.engine.ClassListAnnotationDictionary;
import com.worktoken.engine.WorkSessionImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;

/**
 * @author Alex Pavlov (alex@rushproject.com)
 */
public class HelloWorldApp {

    public static void main(String[] args) throws SQLException, InterruptedException, IOException {

        LogManager.getLogManager().readConfiguration(LogManager.class.getResourceAsStream("/logging.properties"));
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:helloworld", "sa", "");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloWorldPU");

        // Prepare and verify annotation library
        List<Class> annotatedClasses = new ArrayList<Class>();
        annotatedClasses.add(SayHello.class);
        ClassListAnnotationDictionary dictionary = new ClassListAnnotationDictionary(annotatedClasses);
        dictionary.build();

        // Create work session and load process definition
        WorkSessionImpl session = new WorkSessionImpl("com.worktoken.helloworld", emf, dictionary);
        session.readDefinitions(HelloWorldApp.class.getResourceAsStream("helloworld.bpmn"));
        session.start();

        // Create process
        session.createProcess("helloWorld");

        // Allow the process to reach User Task node (Say Hello)
        Thread.sleep(1000);

        // Fetch the task
        SayHello sayHello = (SayHello) session.getUserTasks().get(0);

        // Complete user task
        sayHello.complete();

        // Allow the process to finish
        Thread.sleep(1000);

        // Clean up: close session, entity manager factory and shutdown database
        session.close();
        if (emf != null) {
            emf.close();
        }
        connection.createStatement().execute("SHUTDOWN");
    }
}
