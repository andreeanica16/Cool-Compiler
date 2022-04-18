package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;

public class Attrib extends Expression {
   public String name;
   public Expression val;
   public ParserRuleContext context;

   Attrib(String name, Expression val, ParserRuleContext context) {
      super("<-");
      this.name = name;
      this.val = val;
      this.context = context;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
