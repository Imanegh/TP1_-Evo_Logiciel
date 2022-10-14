package Main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;


public class main {
	public static float NombreDeClasses;
	public static float nbdelignesducode;
	public static float NombreDeMethodes;
	public static float NombreAttributs;
	public static float nbtotalmethodes;
	public static List<String> packages = new ArrayList<>();
	public static Map<SimpleName, Integer> ClassNbAttributs = new HashMap<SimpleName, Integer>();
	public static Map<SimpleName, Integer> ClassNbMethodes = new HashMap<SimpleName, Integer>();
	public static Map<SimpleName, Integer> MethodNbLines = new HashMap<SimpleName, Integer>();
	public static Map<SimpleName, Integer> MethodNbParams = new HashMap<SimpleName, Integer>();

	public static void main(String[] args) throws IOException {		
		System.out.println(" Nombre de classes de l’application: " +  NombreDeClasses + "\n");
		System.out.println(" Nombre de lignes de code de l’application: " + nbdelignesducode + "\n");
		System.out.println(" Nombre d'attributs par classe: " + NombreAttributs + "\n");
		System.out.println(" Nombre de méthodes de l’application: " + NombreDeMethodes + "\n");	
		System.out.println(" Nombre total de méthodes de l’application: " + nbtotalmethodes + "\n");	
		System.out.println(" Nombre total de packages de l’application " + packages.size() + "\n");
		System.out.println(" Nombre moyen de méthodes par classe: " + NombreAttributs/NombreDeClasses + "\n");
		System.out.println(" Nombre moyen de lignes de code par méthode: " + nbtotalmethodes/NombreDeMethodes + "\n");
		System.out.println(" Nombre moyen d’attributs par classe: " + NombreDeMethodes/NombreDeClasses + "\n");			
	
	}	
}
