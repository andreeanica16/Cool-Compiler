package cool.AST;

import cool.structures.symbols.LetSymbol;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class Let extends Expression {
   public List<VarDef> varDefs;
   public Expression body;
   public LetSymbol letSymbol;
   public ParserRuleContext context;

   Let(List<VarDef> varDefs, Expression body, ParserRuleContext context) {
      super("let");
      this.varDefs = varDefs;
      this.body = body;
      this.context = context;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
