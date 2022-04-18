package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Feature extends ASTNode {
   Feature(String symbol, ParserRuleContext context) {
      super(symbol, context);
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return null;
   }
}
