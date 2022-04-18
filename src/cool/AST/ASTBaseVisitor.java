package cool.AST;

import cool.parser.CoolParser;
import cool.parser.CoolParserBaseVisitor;

import java.util.stream.Collectors;

public class ASTBaseVisitor extends CoolParserBaseVisitor<ASTNode> {
    public ASTNode visitClassDef(CoolParser.ClassDefContext ctx) {
        var features = ctx.features.stream()
                .map(feature -> (Feature) visit(feature))
                .collect(Collectors.toList());

        return new ClassDef(ctx.type, ctx.inherited, features, ctx);
    }

    public ASTNode visitProgram(CoolParser.ProgramContext ctx) {
        var classes = ctx.classes.stream()
                .map(classDef -> (ClassDef) visit(classDef))
                .collect(Collectors.toList());

        return new Program(classes);
    }

    public ASTNode visitInt(CoolParser.IntContext ctx) {
        return new Int(ctx.INT().getText());
    }

    public ASTNode visitString(CoolParser.StringContext ctx) {
        return new StrNode(ctx.getText());
    }

    public ASTNode visitBool(CoolParser.BoolContext ctx) {
        return new Bool(ctx.getText());
    }

    public ASTNode visitId(CoolParser.IdContext ctx) {
        return new Id(ctx.getText(), ctx);
    }

    public ASTNode visitClassMemberDef(CoolParser.ClassMemberDefContext ctx) {
        var exprValue = ctx.val != null ? (Expression) visit(ctx.val) : null;

        return new ClassMemberDef(ctx.name, ctx.type, exprValue, ctx);
    }

    public ASTNode visitFormal(CoolParser.FormalContext ctx) {
        return new Formal(ctx.name, ctx.type, ctx);
    }

    public ASTNode visitFuncDef(CoolParser.FuncDefContext ctx) {
        var formals =
                ctx.formals != null ?
                        ctx.formals.stream().map(f -> (Formal) visitFormal(f)).collect(Collectors.toList()) : null;

        var expressions =
                ctx.body != null ?
                        ctx.body.stream().map(expr -> (Expression) visit(expr)).collect(Collectors.toList()) : null;

        return new FuncDef(ctx.name, ctx.type, formals, expressions, ctx);
    }

    public ASTNode visitPlusMinus(CoolParser.PlusMinusContext ctx) {
        return new PlusMinus(ctx.op.getText(), (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx, ctx.op);
    }

    public ASTNode visitMultDiv(CoolParser.MultDivContext ctx) {
        return new MulDiv(ctx.op.getText(), (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx, ctx.op);
    }

    public ASTNode visitRelational(CoolParser.RelationalContext ctx) {
        return new Relational(ctx.op.getText(), (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx, ctx.op);
    }

    public ASTNode visitParen(CoolParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    public ASTNode visitNot(CoolParser.NotContext ctx) {
        return new SingleExpr(ctx.NOT().getText(), (Expression) visit(ctx.expr()), ctx);
    }

    public ASTNode visitIsvoid(CoolParser.IsvoidContext ctx) {
        return new SingleExpr(ctx.ISVOID().getText(), (Expression) visit(ctx.expr()), ctx);
    }

    public ASTNode visitNeg(CoolParser.NegContext ctx) {
        return new SingleExpr(ctx.NEG().getText(), (Expression) visit(ctx.expr()), ctx);
    }

    public ASTNode visitNew(CoolParser.NewContext ctx) {
        return new New(ctx.TYPE().getSymbol(), ctx);
    }

    public ASTNode visitAttrib(CoolParser.AttribContext ctx) {
        return new Attrib(ctx.name.getText(), (Expression) visit(ctx.val), ctx);
    }

    public ASTNode visitImplicitDispatch(CoolParser.ImplicitDispatchContext ctx) {
        var args = ctx.args != null ?
                ctx.args.stream().map(arg -> (Expression) visit(arg)).collect(Collectors.toList()) : null;

        return new ImplicitDispatch(ctx.name.getText(), args, ctx, ctx.name);
    }

    public ASTNode visitExplicitDispatch(CoolParser.ExplicitDispatchContext ctx) {
        var args = ctx.args != null ?
                ctx.args.stream().map(arg -> (Expression) visit(arg)).collect(Collectors.toList()) : null;
        var type = ctx.TYPE() == null ? null: ctx.TYPE().getSymbol();
        var id = ctx.ID() == null ? null : ctx.ID().getSymbol().getText();

        return new ExplicitDispatch((Expression) visit(ctx.initial), args, type, id, ctx, ctx.initial.getStart());
    }

    public ASTNode visitIf(CoolParser.IfContext ctx) {
        var elsee = ctx.branch2 != null ? (Expression) visit(ctx.branch2) : null;

        return new If((Expression) visit(ctx.cond), (Expression) visit(ctx.branch1), elsee, ctx);
    }

    public ASTNode visitWhile(CoolParser.WhileContext ctx) {
        return new While((Expression) visit(ctx.cond), (Expression) visit(ctx.body), ctx);
    }

    public ASTNode visitVarDef(CoolParser.VarDefContext ctx) {
        var value = ctx.val != null ? (Expression) visit(ctx.val) : null;

        return new VarDef(ctx.name, ctx.type, value, ctx);
    }

    public ASTNode visitLet(CoolParser.LetContext ctx) {
        var values = ctx.vars.stream().map(v -> (VarDef) visit(v)).collect(Collectors.toList());

        return new Let(values, (Expression) visit(ctx.body), ctx);
    }

    public ASTNode visitCaseOption(CoolParser.CaseOptionContext ctx) {
        return new CaseOption(ctx.id, ctx.type, (Expression) visit(ctx.body), ctx);
    }

    public ASTNode visitCase(CoolParser.CaseContext ctx) {
        var cases = ctx.cases.stream().map(c -> (CaseOption) visit(c)).collect(Collectors.toList());

        return new Case(cases, (Expression) visit(ctx.expr()), ctx.start);
    }

    public ASTNode visitBody(CoolParser.BodyContext ctx) {
        var expressions = ctx.expr().stream().map(e -> (Expression) visit(e)).collect(Collectors.toList());
        return new Block(expressions);
    }
}
