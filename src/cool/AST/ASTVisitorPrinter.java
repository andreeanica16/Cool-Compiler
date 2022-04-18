package cool.AST;

import java.util.Iterator;

public class ASTVisitorPrinter implements ASTVisitor<String> {
   int indent = 0;

   private String getIndent() {
      String result = "";

      for(int i = 0; i < this.indent; ++i) {
         result = result + " ";
      }

      return result;
   }

   public String visit(Int intt) {
      String var10000 = this.getIndent();
      return var10000 + intt.symbol + "\n";
   }

   public String visit(ClassDef classDef) {
      String var10000 = this.getIndent();
      String result = var10000 + classDef.symbol + "\n";
      this.indent += 2;
      String currentIndentStr = this.getIndent();
      result = result + currentIndentStr + classDef.name.getText() + "\n";
      if (classDef.inherited != null) {
         result = result + currentIndentStr + classDef.inherited.getText() + "\n";
      }

      Feature f;
      for(Iterator var4 = classDef.features.iterator(); var4.hasNext(); result = result + (String)f.accept(this)) {
         f = (Feature)var4.next();
      }

      this.indent -= 2;
      return result;
   }

   public String visit(Program program) {
      String var10000 = this.getIndent();
      String progStr = var10000 + program.symbol + "\n";
      this.indent += 2;

      ClassDef c;
      for(Iterator var3 = program.classes.iterator(); var3.hasNext(); progStr = progStr + this.visit(c)) {
         c = (ClassDef)var3.next();
      }

      this.indent -= 2;
      return progStr;
   }

   public String visit(ClassMemberDef feature) {
      String var10000 = this.getIndent();
      String result = var10000 + feature.symbol + "\n";
      this.indent += 2;
      result = result + this.getIndent() + feature.name.getText() + "\n";
      result = result + this.getIndent() + feature.type.getText() + "\n";
      if (feature.expression != null) {
         result = result + (String)feature.expression.accept(this);
      }

      this.indent -= 2;
      return result;
   }

   public String visit(FuncDef feature) {
      String var10000 = this.getIndent();
      String result = var10000 + feature.symbol + "\n";
      this.indent += 2;
      result = result + this.getIndent() + feature.name.getText() + "\n";

      Iterator var3;
      Formal formal;
      for(var3 = feature.formals.iterator(); var3.hasNext(); result = result + (String)formal.accept(this)) {
         formal = (Formal)var3.next();
      }

      result = result + this.getIndent() + feature.type.getText() + "\n";

      Expression expression;
      for(var3 = feature.body.iterator(); var3.hasNext(); result = result + (String)expression.accept(this)) {
         expression = (Expression)var3.next();
      }

      this.indent -= 2;
      return result;
   }

   public String visit(Formal formal) {
      String var10000 = this.getIndent();
      String result = var10000 + formal.symbol + "\n";
      this.indent += 2;
      result = result + this.getIndent() + formal.name.getText() + "\n";
      result = result + this.getIndent() + formal.type.getText() + "\n";
      this.indent -= 2;
      return result;
   }

   public String visit(StrNode strNode) {
      String var10000 = this.getIndent();
      return var10000 + strNode.symbol + "\n";
   }

   public String visit(Bool bool) {
      String var10000 = this.getIndent();
      return var10000 + bool.symbol + "\n";
   }

   public String visit(Id id) {
      String var10000 = this.getIndent();
      return var10000 + id.symbol + "\n";
   }

   public String visit(BinaryOperation binaryOperation) {
      String var10000 = this.getIndent();
      String result = var10000 + binaryOperation.symbol + "\n";
      this.indent += 2;
      result = result + (String)binaryOperation.left.accept(this);
      result = result + (String)binaryOperation.right.accept(this);
      this.indent -= 2;
      return result;
   }

   public String visit(SingleExpr singleExpr) {
      String var10000 = this.getIndent();
      String result = var10000 + singleExpr.symbol + "\n";
      this.indent += 2;
      result = result + (String)singleExpr.expr.accept(this);
      this.indent -= 2;
      return result;
   }

   public String visit(New neww) {
      String var10000 = this.getIndent();
      String result = var10000 + neww.symbol + "\n";
      this.indent += 2;
      result = result + this.getIndent() + neww.type.getText() + "\n";
      this.indent -= 2;
      return result;
   }

   public String visit(Attrib attrib) {
      String var10000 = this.getIndent();
      String result = var10000 + attrib.symbol + "\n";
      this.indent += 2;
      result = result + this.getIndent() + attrib.name + "\n";
      result = result + (String)attrib.val.accept(this);
      this.indent -= 2;
      return result;
   }

   public String visit(ImplicitDispatch implicitDispatch) {
      String var10000 = this.getIndent();
      String result = var10000 + implicitDispatch.symbol + "\n";
      this.indent += 2;
      result = result + this.getIndent() + implicitDispatch.name + "\n";

      Expression arg;
      for(Iterator var3 = implicitDispatch.args.iterator(); var3.hasNext(); result = result + (String)arg.accept(this)) {
         arg = (Expression)var3.next();
      }

      this.indent -= 2;
      return result;
   }

   public String visit(ExplicitDispatch explicitDispatch) {
      String var10000 = this.getIndent();
      String result = var10000 + explicitDispatch.symbol + "\n";
      this.indent += 2;
      result = result + (String)explicitDispatch.initial.accept(this);
      if (explicitDispatch.atType != null) {
         result = result + this.getIndent() + explicitDispatch.atType.getText() + "\n";
      }

      if (explicitDispatch.name != null) {
         result = result + this.getIndent() + explicitDispatch.name + "\n";
      }

      Expression arg;
      for(Iterator var3 = explicitDispatch.args.iterator(); var3.hasNext(); result = result + (String)arg.accept(this)) {
         arg = (Expression)var3.next();
      }

      this.indent -= 2;
      return result;
   }

   public String visit(If iff) {
      String var10000 = this.getIndent();
      String result = var10000 + iff.symbol + "\n";
      this.indent += 2;
      result = result + (String)iff.cond.accept(this);
      result = result + (String)iff.then.accept(this);
      if (iff.elsee != null) {
         result = result + (String)iff.elsee.accept(this);
      }

      this.indent -= 2;
      return result;
   }

   public String visit(While whilee) {
      String var10000 = this.getIndent();
      String result = var10000 + whilee.symbol + "\n";
      this.indent += 2;
      result = result + (String)whilee.cond.accept(this);
      result = result + (String)whilee.body.accept(this);
      this.indent -= 2;
      return result;
   }

   public String visit(VarDef varDef) {
      String var10000 = this.getIndent();
      String result = var10000 + varDef.symbol + "\n";
      this.indent += 2;
      result = result + this.getIndent() + varDef.name.getText() + "\n";
      result = result + this.getIndent() + varDef.type.getText() + "\n";
      if (varDef.val != null) {
         result = result + (String)varDef.val.accept(this);
      }

      this.indent -= 2;
      return result;
   }

   public String visit(Let let) {
      String var10000 = this.getIndent();
      String result = var10000 + let.symbol + "\n";
      this.indent += 2;

      VarDef v;
      for(Iterator var3 = let.varDefs.iterator(); var3.hasNext(); result = result + (String)v.accept(this)) {
         v = (VarDef)var3.next();
      }

      result = result + (String)let.body.accept(this);
      this.indent -= 2;
      return result;
   }

   public String visit(CaseOption caseOption) {
      String var10000 = this.getIndent();
      String result = var10000 + caseOption.symbol + "\n";
      this.indent += 2;
      result = result + this.getIndent() + caseOption.id.getText() + "\n";
      result = result + this.getIndent() + caseOption.type.getText() + "\n";
      result = result + (String)caseOption.body.accept(this);
      this.indent -= 2;
      return result;
   }

   public String visit(Case casee) {
      String var10000 = this.getIndent();
      String result = var10000 + casee.symbol + "\n";
      this.indent += 2;
      result = result + (String)casee.expr.accept(this);

      CaseOption caseOption;
      for(Iterator var3 = casee.branches.iterator(); var3.hasNext(); result = result + (String)caseOption.accept(this)) {
         caseOption = (CaseOption)var3.next();
      }

      this.indent -= 2;
      return result;
   }

   public String visit(Block block) {
      String var10000 = this.getIndent();
      String result = var10000 + block.symbol + "\n";
      this.indent += 2;

      Expression expression;
      for(Iterator var3 = block.expressions.iterator(); var3.hasNext(); result = result + (String)expression.accept(this)) {
         expression = (Expression)var3.next();
      }

      this.indent -= 2;
      return result;
   }
}
