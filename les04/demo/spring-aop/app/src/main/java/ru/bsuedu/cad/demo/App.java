/*
 * This source file was generated by the Gradle 'init' task
 */
package ru.bsuedu.cad.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfigurationBean.class);
        ((App) ctx.getBean("app")).run();
    }

    final Person person;

    public App(@Qualifier("naruto") Person orator) {
        this.person = orator;

    }

    public void run() {
        person.say();
        person.scream();
    }
}
