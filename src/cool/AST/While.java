package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;

public class While extends Expression {
   public Expression cond;
   public Expression body;
   ParserRuleContext context;

   While(Expression cond, Expression body, ParserRuleContext context) {
      super("while");
      this.cond = cond;
      this.body = body;
      this.context = context;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
