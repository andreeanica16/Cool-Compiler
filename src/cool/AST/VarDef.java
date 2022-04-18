package cool.AST;

import cool.structures.symbols.IdSymbol;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class VarDef extends ASTNode {
   public Token name;
   public Token type;
   public Expression val;
   public IdSymbol idSymbol;
   public ParserRuleContext context;

   VarDef(Token name, Token type, Expression val, ParserRuleContext context) {
      super("local");
      this.name = name;
      this.type = type;
      this.val = val;
      this.context = context;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
