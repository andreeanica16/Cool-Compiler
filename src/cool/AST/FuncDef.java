package cool.AST;

import cool.structures.symbols.FunctionSymbol;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class FuncDef extends Feature {
    public Token name;
    public Token type;
    public List<Formal> formals;
    public List<Expression> body;
    public FunctionSymbol symbol;

    FuncDef(Token name, Token type, List<Formal> formals, List<Expression> body, ParserRuleContext context) {
        super("method", context);
        this.name = name;
        this.type = type;
        this.formals = formals;
        this.body = body;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}