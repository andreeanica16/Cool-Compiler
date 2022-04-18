package cool.compiler;

import cool.AST.ASTBaseVisitor;
import cool.AST.ASTDefinitionVisitor;
import cool.AST.ASTNode;
import cool.AST.ASTResolutionVisitor;
import cool.CodeGen.CodeGenVisitor;
import cool.lexer.CoolLexer;
import cool.parser.CoolParser;
import cool.parser.CoolParser.ProgramContext;
import cool.structures.SymbolTable;
import cool.structures.symbols.TypeSymbol;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

public class Compiler {
   public static ParseTreeProperty<String> fileNames = new ParseTreeProperty();

   public static void main(String[] args) throws IOException {
      if (args.length == 0) {
         System.err.println("No file(s) given");
      } else {
         CoolLexer lexer = null;
         CommonTokenStream tokenStream = null;
         CoolParser parser = null;
         ParserRuleContext globalTree = null;
         boolean lexicalSyntaxErrors = false;
         String[] var6 = args;
         int var7 = args.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String fileName = var6[var8];
            CharStream input = CharStreams.fromFileName(fileName);
            if (lexer == null) {
               lexer = new CoolLexer(input);
            } else {
               lexer.setInputStream(input);
            }

            if (tokenStream == null) {
               tokenStream = new CommonTokenStream(lexer);
            } else {
               tokenStream.setTokenSource(lexer);
            }

            if (parser == null) {
               parser = new CoolParser(tokenStream);
            } else {
               parser.setTokenStream(tokenStream);
            }

            var errorListener =  new BaseErrorListener() {
               public boolean errors = false;

               @Override
               public void syntaxError(Recognizer<?, ?> recognizer,
                                       Object offendingSymbol,
                                       int line, int charPositionInLine,
                                       String msg,
                                       RecognitionException e) {
                  String newMsg = "\"" + new File(fileName).getName() + "\", line " +
                          line + ":" + (charPositionInLine + 1) + ", ";

                  Token token = (Token)offendingSymbol;
                  if (token.getType() == CoolLexer.ERROR)
                     newMsg += "Lexical error: " + token.getText();
                  else
                     newMsg += "Syntax error: " + msg;

                  System.err.println(newMsg);
                  errors = true;
               }
            };

            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);
            ProgramContext tree = parser.program();
            int i;
            if (globalTree == null) {
               globalTree = tree;
            } else {
               for(i = 0; i < tree.getChildCount(); ++i) {
                  globalTree.addAnyChild(tree.getChild(i));
               }
            }

            for(i = 0; i < tree.getChildCount(); ++i) {
               ParseTree child = tree.getChild(i);
               if (child instanceof ParserRuleContext) {
                  fileNames.put(child, fileName);
               }
            }

            lexicalSyntaxErrors |= errorListener.errors;
         }

         if (lexicalSyntaxErrors) {
            System.err.println("Compilation halted");
         } else {
            SymbolTable.defineBasicClasses();
            ASTBaseVisitor astBaseVisitor = new ASTBaseVisitor();
            ASTNode ast = (ASTNode)astBaseVisitor.visit(globalTree);
            ast.accept(new ASTDefinitionVisitor());
            ast.accept(new ASTResolutionVisitor());
            if (SymbolTable.hasSemanticErrors()) {
               System.err.println("Compilation halted");
            }

            String[] fileName = args[0].split("/");
            TypeSymbol.OBJECT.createTags();
            ST codeGen = ast.accept(new CodeGenVisitor(fileName[fileName.length - 1]));
            System.out.println(codeGen.render());
         }
      }
   }
}
