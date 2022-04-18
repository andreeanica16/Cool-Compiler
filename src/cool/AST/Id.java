package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;

public class Id extends Expression {
   ParserRuleContext context;

   Id(String symbol, ParserRuleContext context) {
      super(symbol);
      this.context = context;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
