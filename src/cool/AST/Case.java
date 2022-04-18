package cool.AST;

import org.antlr.v4.runtime.Token;

import java.util.List;

public class Case extends Expression {
   public List<CaseOption> branches;
   public Expression expr;
   public Token lineToken;

   Case(List<CaseOption> branches, Expression expression, Token lineToken) {
      super("case");
      this.branches = branches;
      this.expr = expression;
      this.lineToken = lineToken;
   }

   public <T> T accept(ASTVisitor<T> visitor) {
      return visitor.visit(this);
   }
}
