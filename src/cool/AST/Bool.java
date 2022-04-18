package cool.AST;

public class Bool extends Expression {
   Bool(String symbol) {
      super(symbol);
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
