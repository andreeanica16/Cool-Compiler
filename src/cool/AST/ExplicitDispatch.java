package cool.AST;

import cool.structures.symbols.TypeSymbol;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class ExplicitDispatch extends Expression {
   public String name;
   public Expression initial;
   public List<Expression> args;
   public Token atType;
   public ParserRuleContext context;
   public Token functionToken;
   public TypeSymbol callerType;

   public void setCallerType(TypeSymbol callerType) {
      this.callerType = callerType;
   }

   ExplicitDispatch(Expression initial, List<Expression> args, Token atType, String name, ParserRuleContext context, Token token) {
      super(".");
      this.initial = initial;
      this.args = args;
      this.atType = atType;
      this.name = name;
      this.context = context;
      this.functionToken = token;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
