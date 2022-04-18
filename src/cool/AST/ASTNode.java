package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ASTNode {
   public String symbol;
   ParserRuleContext context;

   ASTNode(String symbol) {
      this.symbol = symbol;
   }

   ASTNode(String symbol, ParserRuleContext context) {
      this.symbol = symbol;
      this.context = context;
   }

   public abstract <T> T accept(ASTVisitor<T> var1);
}
