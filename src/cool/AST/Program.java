package cool.AST;

import java.util.List;

public class Program extends ASTNode {
   public List<ClassDef> classes;

   Program(List<ClassDef> classes) {
      super("program");
      this.classes = classes;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
