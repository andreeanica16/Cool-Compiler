package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class BinaryOperation extends Expression {
   public Expression left;
   public Expression right;
   ParserRuleContext context;
   public Token opTok;

   BinaryOperation(String op, Expression left, Expression right, ParserRuleContext context, Token opTok) {
      super(op);
      this.left = left;
      this.right = right;
      this.context = context;
      this.opTok = opTok;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
