package cool.AST;

import cool.structures.CaseBranch;
import cool.structures.symbols.IdSymbol;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class CaseOption extends ASTNode {
    public Token id;
    public Token type;
    public Expression body;
    ParserRuleContext context;
    public IdSymbol idSymbol;
    public CaseBranch caseScope;

    CaseOption(Token id, Token type, Expression body, ParserRuleContext context) {
        super("case branch");
        this.id = id;
        this.type = type;
        this.body = body;
        this.context = context;
    }

    public void setCaseScope(CaseBranch caseScope) {
        this.caseScope = caseScope;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}