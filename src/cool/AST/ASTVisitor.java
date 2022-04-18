package cool.AST;

public interface ASTVisitor<T> {
   T visit(ClassDef var1);

   T visit(Program var1);

   T visit(ClassMemberDef var1);

   T visit(Int var1);

   T visit(FuncDef var1);

   T visit(Formal var1);

   T visit(StrNode var1);

   T visit(Bool var1);

   T visit(Id var1);

   T visit(BinaryOperation var1);

   T visit(SingleExpr var1);

   T visit(New var1);

   T visit(Attrib var1);

   T visit(ImplicitDispatch var1);

   T visit(ExplicitDispatch var1);

   T visit(If var1);

   T visit(While var1);

   T visit(VarDef var1);

   T visit(Let var1);

   T visit(CaseOption var1);

   T visit(Case var1);

   T visit(Block var1);
}
