package cool.AST;

import cool.structures.symbols.TypeSymbol;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class ClassDef extends ASTNode {
    public Token name;
    public Token inherited;
    public List<Feature> features;
    public TypeSymbol type;

    ClassDef(Token name, Token inherited, List<Feature> features, ParserRuleContext context) {
        super("class", context);
        this.name = name;
        this.inherited = inherited;
        this.features = features;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}