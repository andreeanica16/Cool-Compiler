package cool.AST;

public class StrNode extends Expression {
   StrNode(String symbol) {
      super(symbol);
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
