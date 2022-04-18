package cool.AST;

import cool.structures.symbols.IdSymbol;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Formal extends ASTNode {
    Token name;
    Token type;
    IdSymbol idSymbol;
    ParserRuleContext context;

    Formal(Token name, Token type, ParserRuleContext context) {
        super("formal");
        this.name = name;
        this.type = type;
        this.context = context;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}