package cool.structures;

import cool.compiler.Compiler;
import cool.parser.CoolParser.ProgramContext;
import cool.structures.symbols.FunctionSymbol;
import cool.structures.symbols.IdSymbol;
import cool.structures.symbols.TypeSymbol;
import java.io.File;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class SymbolTable {
   public static DefaultScope globals;
   private static boolean semanticErrors;

   private static void addBasicTypes() {
      globals.add(TypeSymbol.OBJECT);
      TypeSymbol.INT.setParent(TypeSymbol.OBJECT);
      globals.add(TypeSymbol.INT);
      globals.add(TypeSymbol.FLOAT);
      TypeSymbol.BOOL.setParent(TypeSymbol.OBJECT);
      globals.add(TypeSymbol.BOOL);
      TypeSymbol.STRING.setParent(TypeSymbol.OBJECT);
      globals.add(TypeSymbol.STRING);
      TypeSymbol.IO.setParent(TypeSymbol.OBJECT);
      globals.add(TypeSymbol.IO);
      TypeSymbol.SELF_TYPE.setParent(TypeSymbol.OBJECT);
      globals.add(TypeSymbol.SELF_TYPE);
   }

   private static void addObjectClass() {
      globals.addFuncToClass("Object", new FunctionSymbol((TypeSymbol)globals.lookup("Object"), "abort", "Object"));
      globals.addFuncToClass("Object", new FunctionSymbol((TypeSymbol)globals.lookup("Object"), "copy", "SELF_TYPE"));
      globals.addFuncToClass("Object", new FunctionSymbol((TypeSymbol)globals.lookup("Object"), "type_name", "String"));
   }

   private static void addIOClass() {
      globals.addFuncToClass("IO", new FunctionSymbol((TypeSymbol)globals.lookup("IO"), "in_string", "String"));
      globals.addFuncToClass("IO", new FunctionSymbol((TypeSymbol)globals.lookup("IO"), "in_int", "Int"));
      globals.addFuncToClass("String", new FunctionSymbol((TypeSymbol)globals.lookup("String"), "length", "Int"));
      FunctionSymbol outString = new FunctionSymbol((TypeSymbol)globals.lookup("IO"), "out_string", "SELF_TYPE");
      IdSymbol strToOutput = new IdSymbol("x");
      strToOutput.setType(TypeSymbol.STRING);
      outString.add(strToOutput);
      globals.addFuncToClass("IO", outString);
      FunctionSymbol outInt = new FunctionSymbol((TypeSymbol)globals.lookup("IO"), "out_int", "SELF_TYPE");
      IdSymbol intToOutput = new IdSymbol("x");
      intToOutput.setType(TypeSymbol.INT);
      outInt.add(intToOutput);
      globals.addFuncToClass("IO", outInt);
      FunctionSymbol concat = new FunctionSymbol((TypeSymbol)globals.lookup("String"), "concat", "String");
      IdSymbol s1 = new IdSymbol("s");
      s1.setType(TypeSymbol.STRING);
      concat.add(s1);
      globals.addFuncToClass("String", concat);
      FunctionSymbol substr = new FunctionSymbol((TypeSymbol)globals.lookup("String"), "substr", "String");
      IdSymbol i = new IdSymbol("i");
      i.setType(TypeSymbol.INT);
      IdSymbol l = new IdSymbol("l");
      l.setType(TypeSymbol.INT);
      substr.add(i);
      substr.add(l);
      globals.addFuncToClass("String", substr);
   }

   public static void defineBasicClasses() {
      globals = new DefaultScope((Scope)null);
      semanticErrors = false;
      addBasicTypes();
      addObjectClass();
      addIOClass();
      TypeSymbol.STRING.setMethodsOffsets();
      TypeSymbol.IO.setMethodsOffsets();
      TypeSymbol.INT.setMethodsOffsets();
      TypeSymbol.BOOL.setMethodsOffsets();
   }

   public static void error(ParserRuleContext ctx, Token info, String str) {
      while(!(ctx.getParent() instanceof ProgramContext)) {
         ctx = ctx.getParent();
      }

      String var10000 = (new File((String)Compiler.fileNames.get(ctx))).getName();
      String message = "\"" + var10000 + "\", line " + info.getLine() + ":" + (info.getCharPositionInLine() + 1) + ", Semantic error: " + str;
      System.err.println(message);
      semanticErrors = true;
   }

   public static void error(String str) {
      String message = "Semantic error: " + str;
      System.err.println(message);
      semanticErrors = true;
   }

   public static boolean hasSemanticErrors() {
      return semanticErrors;
   }
}
