package step1;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import step2.ClassDeclarationVisitor;
import step2.FieldAccessVisitor;
import step2.FieldDeclarationVisitor;
import step2.MethodDeclarationVisitor;
import step2.PackageDeclarationVisitor;
import step2.StatementAllVisitor;
import step2.StatementVisitor;
import step2.VariableDeclarationFragmentVisitor;



public class Parser {
	
	public static final String projectPath = "C:\\Users\\imane\\eclipse-workspace\\CabinetV";
	public static final String projectSourcePath = projectPath + "/src";
	public static final String jrePath = "C:\\Program Files\\Java\\jre1.8.0_51\\lib\\rt.jar";
	
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

		// read java files
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder);

		// every file
		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			// System.out.println(content);

			CompilationUnit parse = parse(content.toCharArray());
			// print package info
			printPackageInfo(parse);
			
			// print class info
			printClassInfo(parse);
			
			// print attribute info
			printAttributeInfo(parse);
			
			// print methods info
			printMethodInfo(parse);

			// print variables info
			printVariableInfo(parse);
			
			// print statement info
			printStatementInfo(parse);						
		}
	}	
	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				javaFiles.add(fileEntry);
			}
		}
		return javaFiles;
	}
	// create AST
	private static CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); 
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		parser.setBindingsRecovery(true);
 
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(classSource);
		
		return (CompilationUnit) parser.createAST(null); 
	}	
	// navigate package information
	public static void printPackageInfo(CompilationUnit parse) {
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);
		
		for (PackageDeclaration pack : visitor.getPackages()) {
			System.out.println("Package: " + pack);
			if (!packages.contains(pack.toString())) {
				packages.add(pack.toString());
			}
		}
	}
	// navigate class information
	public static void printClassInfo(CompilationUnit parse) {
		ClassDeclarationVisitor visitor = new ClassDeclarationVisitor();
		parse.accept(visitor);

		for (TypeDeclaration clas : visitor.getClasses()) {
			System.out.println("Class name: " + clas.getName());
		}
		NombreDeClasses = NombreDeClasses + visitor.getClassesNumber();
	}	
	// navigate field information
	public static void printFieldAccessInfo(CompilationUnit parse) {
		FieldAccessVisitor visitor = new FieldAccessVisitor();
		parse.accept(visitor);
		
		for (SimpleName field : visitor.getFields()) {
			System.out.println("Field access name: " + field.getFullyQualifiedName());
		}
	}	
	// navigate attribute information
	public static void printAttributeInfo(CompilationUnit parse) {
		ClassDeclarationVisitor visitor1 = new ClassDeclarationVisitor();
		parse.accept(visitor1);
		for (TypeDeclaration clas : visitor1.getClasses()) {
			Integer attributeEachClass = 0;
			FieldDeclarationVisitor visitor2 = new FieldDeclarationVisitor();
			clas.accept(visitor2);
			
			for (FieldDeclaration field : visitor2.getFields()) {
				
				VariableDeclarationFragmentVisitor visitor3 = new VariableDeclarationFragmentVisitor();
				field.accept(visitor3);

				for (VariableDeclarationFragment variableDeclarationFragment : visitor3
						.getVariables()) {
					System.out.println("Attribute name in this class: "
							+ variableDeclarationFragment.getName());
				}
				NombreAttributs = NombreAttributs + visitor3.getVariablesNumber();
				attributeEachClass = attributeEachClass + visitor3.getVariablesNumber();
			}
			ClassNbAttributs.put(clas.getName(), (Integer)attributeEachClass);
		}
	}
		
	// navigate method information
	public static void printMethodInfo(CompilationUnit parse) {
		ClassDeclarationVisitor visitor1 = new ClassDeclarationVisitor();
		parse.accept(visitor1);
		
		for (TypeDeclaration clas : visitor1.getClasses()) {
			
			MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
			clas.accept(visitor);
		
			for (MethodDeclaration method : visitor.getMethods()) {
				System.out.println("Nom de methode: " + method.getName()
						+ ". Return type: " + method.getReturnType2());
				MethodNbParams.put(method.getName(), method.parameters().size());
			}
			NombreDeMethodes = NombreDeMethodes + visitor.getMethodsNumber();
			ClassNbMethodes.put(clas.getName(), (Integer)visitor.getMethodsNumber());	
		}
	}
	// navigate variables inside method
	public static void printVariableInfo(CompilationUnit parse) {

		MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
		parse.accept(visitor1);
		for (MethodDeclaration method : visitor1.getMethods()) {

			VariableDeclarationFragmentVisitor visitor2 = new VariableDeclarationFragmentVisitor();
			method.accept(visitor2);

			for (VariableDeclarationFragment variableDeclarationFragment : visitor2
					.getVariables()) {
				System.out.println("variable name: "
						+ variableDeclarationFragment.getName()
						+ ". variable Initializer: "
						+ variableDeclarationFragment.getInitializer());
			}

		}
	}	
	// navigate statements inside method
	public static void printStatementInfo(CompilationUnit parse) {
		
		MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
		parse.accept(visitor1);
		for (MethodDeclaration method : visitor1.getMethods()) {
			
			StatementVisitor visitor2 = new StatementVisitor();
			method.accept(visitor2);
			nbtotalmethodes = nbtotalmethodes + visitor2.getStatementsNumber();
			MethodNbLines.put(method.getName(), (Integer)visitor2.getStatementsNumber());
		}
	}
	// navigate all statements in application
		public static void printAllStatementInfo(CompilationUnit parse) {
			
			StatementAllVisitor visitor = new StatementAllVisitor();
			parse.accept(visitor);
	
         
         }
	}