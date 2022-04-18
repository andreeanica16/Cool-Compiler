package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class New extends Expression {
   public Token type;
   public ParserRuleContext context;

   New(Token type, ParserRuleContext context) {
      super("new");
      this.type = type;
      this.context = context;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
