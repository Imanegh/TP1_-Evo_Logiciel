package Main;


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

	public static void Fonctionalités(){
		System.out.println("Nombreeeee de classes: " +  NombreDeClasses + "\n");
		System.out.println("Nombre de lignes du code: " + nbdelignesducode + "\n");
		System.out.println("Nombre de methodes: " + NombreDeMethodes + "\n");
		System.out.println("Nombre d'attributs: " + NombreAttributs + "\n");
		System.out.println("Package number of application: " + packages.size() + "\n");
		System.out.println("Nombre total de methodes: " + nbtotalmethodes + "\n");
		System.out.println("Nombre moyen de méthodes par classe: " + NombreAttributs/NombreDeClasses + "\n");
		System.out.println("Nombre moyen d’attributs par classe: " + NombreDeMethodes/NombreDeClasses + "\n");
		System.out.println("Nombre moyen de lignes de code par méthode: " + nbtotalmethodes/NombreDeMethodes + "\n");
	
		
		 ASTParser parser = ASTParser.newParser(AST.JLS4);
	parser.setSource("public class A { int i = 9;  \n int j; \n ArrayList<Integer> al = new ArrayList<Integer>();j=1000; }".toCharArray());
	
	parser.setKind(ASTParser.K_COMPILATION_UNIT);
	
	final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	cu.accept(new ASTVisitor() {

		Set names = new HashSet();

		public boolean visit(VariableDeclarationFragment node) {
			SimpleName name = node.getName();
			this.names.add(name.getIdentifier());
			System.out.println("Declaration of '"+name+"' at line"+cu.getLineNumber(name.getStartPosition()));
			return false; 
		}

		public boolean visit(SimpleName node) {
			if (this.names.contains(node.getIdentifier())) {
			System.out.println("Usage of '" + node + "' at line " +	cu.getLineNumber(node.getStartPosition()));
			}
			return true;
		}

	});
}
}
