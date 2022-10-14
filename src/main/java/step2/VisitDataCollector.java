package step2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.SimpleName;

public class VisitDataCollector {
	
	public static float NombreDeClasses;
	public static float NombreDeMethodes;
	public static float NombreAttributs;
	public static float nbdelignesducode;
	public static float nbtotalmethodes;
	
	public static List<String> packages = new ArrayList<>();
	public static Map<SimpleName, Integer> ClassNbAttributs = new HashMap<SimpleName, Integer>();
	public static Map<SimpleName, Integer> ClassNbMethodes = new HashMap<SimpleName, Integer>();
	public static Map<SimpleName, Integer> MethodNbLines = new HashMap<SimpleName, Integer>();
	public static Map<SimpleName, Integer> MethodNbParams = new HashMap<SimpleName, Integer>();	
    
    public static void main(String[] args) throws IOException {
    
    	// Nombre de methodes de classe
    Map<SimpleName, Integer>  ClassNbMethodesSorted =  ClassNbMethodes
	        .entrySet()
	        .stream()
	        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
			.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
					(entry1, entry2) -> entry2, LinkedHashMap::new));
    System.out.println("Nombre de methodes: " + ClassNbMethodesSorted + "\n");
    
    	// Nombre d'attributs de classe
    Map<SimpleName, Integer> ClassNbAttributsSorted = ClassNbAttributs
 	        .entrySet()
 	        .stream()
 	        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
 			.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
 					(entry1, entry2) -> entry2, LinkedHashMap::new));
     System.out.println("Nombre d'attributs: " + ClassNbAttributsSorted + "\n");
     
    	// 10% Des classes avec plus grand nombre d'attributs
    int top10ClasNum = Math.round(NombreDeClasses*0.1) < 1 ? 1 : (int)Math.round(NombreDeClasses*0.1);
    
    List<SimpleName> clasAttributeTop = new ArrayList<SimpleName>(ClassNbAttributsSorted.keySet())
    		.subList(0, top10ClasNum);
    System.out.println(" 10% Des classes avec plus grand nombre d'attributs : ");
    clasAttributeTop.forEach(System.out::println);
    
    	// 10% Des classes avec plus grand nombre de methodes
    List<SimpleName> clasMethodTop = new ArrayList<SimpleName>(ClassNbMethodesSorted.keySet())
    		.subList(0, top10ClasNum);
    System.out.println("\n" + " 10% Des classes avec plus grand nombre de methodes: ");
    clasMethodTop.forEach(System.out::println);
    
    	//Les classes  qui font partie en même temps des deux catégories précédentes
    List<SimpleName> commonClasTop = new ArrayList<SimpleName>(clasAttributeTop);
    commonClasTop.retainAll(clasMethodTop);
    System.out.println("\n" + " 10% Des classes  qui font partie en même temps des deux catégories précédentes: ");
    commonClasTop.forEach(System.out::println);
    
    	//10% Des methodes avec le plus grand nombre de lignes de code 
    Map<SimpleName, Integer> MethodNbLinesSorted = MethodNbLines
	        .entrySet()
	        .stream()
	        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
			.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
					(entry1, entry2) -> entry2, LinkedHashMap::new));
    System.out.println("\n" + "Nombre de lignes de méthode: " + MethodNbLinesSorted + "\n");
    
    int top10MethodNum = Math.round(NombreDeMethodes*0.1) < 1 ? 1 : (int)Math.round(NombreDeMethodes*0.1);
    List<SimpleName> methodStateTop = new ArrayList<SimpleName>(MethodNbLinesSorted.keySet())
    		.subList(0, top10MethodNum);
    System.out.println("10% Des methodes avec le plus grand nombre de lignes de code: ");
    methodStateTop.forEach(System.out::println);

    }
}
