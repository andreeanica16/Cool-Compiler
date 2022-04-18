package cool.AST;

import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class ImplicitDispatch extends Expression {
   public String name;
   public List<Expression> args;
   public ParserRuleContext context;
   public Token functionToken;

   ImplicitDispatch(String name, List<Expression> args, ParserRuleContext context, Token functionToken) {
      super("implicit dispatch");
      this.name = name;
      this.args = args;
      this.context = context;
      this.functionToken = functionToken;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
