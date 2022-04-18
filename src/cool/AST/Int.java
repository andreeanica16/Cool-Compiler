package cool.AST;

public class Int extends Expression {
   Int(String symbol) {
      super(symbol);
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
