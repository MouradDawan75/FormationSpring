package fr.dawan.demoSpringCore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/*
 * @ComponentScan: scan le package de base et les sous packages
 * Si des classes sont définiés en dehors du package de base, elles ne seront pas prises en compte
 */

@SpringBootApplication
//@ComponentScan( basePackages = {"fr.dawan.models"}) // à ajouter si package en dehors du package de base
public class DemoSpringCoreApplication {

	static ConfigurableApplicationContext context;
	
	public static void main(String[] args) {
		//la méthode run initialise le context de l'application
		// elle instancie tous les objets + gestion des dépendances
		context = SpringApplication.run(DemoSpringCoreApplication.class, args);
		
		test();
		//bean: mot clé de Spring qui désigne un objet crée par Spring
		allBeans();
	}

	private static void allBeans() {
		
		String[] beans = context.getBeanDefinitionNames();
		for(String b : beans) {
			System.out.println(b);
		}
		
	}

	private static void test() {
		Voiture v = context.getBean(Voiture.class);
		v.demarrer(); //vérifier si moteur est injecté ou pas
		
		
	}

}
