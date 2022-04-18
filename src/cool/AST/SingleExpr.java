package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;

public class SingleExpr extends Expression {
   public Expression expr;
   ParserRuleContext context;

   SingleExpr(String tok, Expression expression, ParserRuleContext context) {
      super(tok);
      this.expr = expression;
      this.context = context;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
