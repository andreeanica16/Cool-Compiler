package cool.AST;

import java.util.List;

public class Block extends Expression {
   public List<Expression> expressions;

   Block(List<Expression> expressions) {
      super("block");
      this.expressions = expressions;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
