package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Relational extends BinaryOperation {
   Relational(String op, Expression left, Expression right, ParserRuleContext context, Token opTok) {
      super(op, left, right, context, opTok);
   }
}
