package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;

public class If extends Expression {
   public Expression cond;
   public Expression then;
   public Expression elsee;
   public ParserRuleContext context;

   If(Expression cond, Expression then, Expression elsee, ParserRuleContext context) {
      super("if");
      this.cond = cond;
      this.then = then;
      this.elsee = elsee;
      this.context = context;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
