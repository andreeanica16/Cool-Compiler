package cool.AST;

import cool.structures.symbols.IdSymbol;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class ClassMemberDef extends Feature {
   public Token name;
   public Token type;
   public Expression expression;
   public IdSymbol symbol;

   ClassMemberDef(Token name, Token type, Expression expression, ParserRuleContext context) {
      super("attribute", context);
      this.name = name;
      this.type = type;
      this.expression = expression;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
