package cool.AST;

import cool.structures.CaseBranch;
import cool.structures.DefaultScope;
import cool.structures.Scope;
import cool.structures.SymbolTable;
import cool.structures.symbols.FunctionSymbol;
import cool.structures.symbols.IdSymbol;
import cool.structures.symbols.LetSymbol;
import cool.structures.symbols.TypeSymbol;
import java.util.HashSet;
import java.util.Iterator;

public class ASTDefinitionVisitor implements ASTVisitor<Void> {
   Scope currentScope = null;
   HashSet<String> illegalParents = new HashSet();

   public Void visit(ClassDef classDef) {
      String name = classDef.name.getText();
      if (name.equals(TypeSymbol.SELF_TYPE.getName())) {
         SymbolTable.error(classDef.context, classDef.name, "Class has illegal name SELF_TYPE");
         return null;
      } else {
         String parentName = classDef.inherited == null ? "" : classDef.inherited.getText();
         TypeSymbol classSymbol = new TypeSymbol(parentName, name);
         if (!SymbolTable.globals.add(classSymbol)) {
            SymbolTable.error(classDef.context, classDef.name, "Class " + name + " is redefined");
            return null;
         } else if (classDef.inherited != null && this.illegalParents.contains(parentName)) {
            SymbolTable.error(classDef.context, classDef.inherited, "Class " + name + " has illegal parent " + parentName);
            return null;
         } else {
            classDef.type = classSymbol;
            this.currentScope = classSymbol;
            Iterator var5 = classDef.features.iterator();

            while(var5.hasNext()) {
               Feature feature = (Feature)var5.next();
               feature.accept(this);
            }

            return null;
         }
      }
   }

   public Void visit(Program program) {
      this.currentScope = new DefaultScope((Scope)null);
      this.illegalParents = new HashSet();
      this.illegalParents.add(TypeSymbol.INT.getName());
      this.illegalParents.add(TypeSymbol.BOOL.getName());
      this.illegalParents.add(TypeSymbol.STRING.getName());
      this.illegalParents.add(TypeSymbol.SELF_TYPE.getName());
      Iterator var2 = program.classes.iterator();

      while(var2.hasNext()) {
         ClassDef cl = (ClassDef)var2.next();
         cl.accept(this);
      }

      return null;
   }

   public Void visit(ClassMemberDef classMemberDef) {
      String classMemberName = classMemberDef.name.getText();
      String parentClassName = ((TypeSymbol)this.currentScope).getName();
      if (classMemberName.equals("self")) {
         SymbolTable.error(classMemberDef.context, classMemberDef.name, "Class " + parentClassName + " has attribute with illegal name " + classMemberName);
         return null;
      } else {
         IdSymbol symbol;
         if (classMemberDef.expression != null) {
            symbol = new IdSymbol(classMemberName, classMemberDef.expression.symbol);
         } else {
            symbol = new IdSymbol(classMemberName);
         }

         symbol.setType(new TypeSymbol(parentClassName, classMemberDef.type.getText()));
         if (!this.currentScope.add(symbol)) {
            SymbolTable.error(classMemberDef.context, classMemberDef.name, "Class " + parentClassName + " redefines attribute " + classMemberName);
            return null;
         } else {
            classMemberDef.symbol = symbol;
            if (classMemberDef.expression != null) {
               classMemberDef.expression.accept(this);
            }

            return null;
         }
      }
   }

   public Void visit(Int intt) {
      return null;
   }

   public Void visit(FuncDef funcDef) {
      String funcName = funcDef.name.getText();
      String parentClassName = ((TypeSymbol)this.currentScope).getName();
      String funcTypeTxt = funcDef.type.getText();
      FunctionSymbol funcSymbol = new FunctionSymbol((TypeSymbol)this.currentScope, funcName, funcTypeTxt);
      if (!((TypeSymbol)this.currentScope).addFunc(funcSymbol)) {
         SymbolTable.error(funcDef.context, funcDef.name, "Class " + parentClassName + " redefines method " + funcName);
         return null;
      } else {
         funcDef.symbol = funcSymbol;
         this.currentScope = funcSymbol;
         Iterator var6 = funcDef.formals.iterator();

         while(var6.hasNext()) {
            Formal param = (Formal)var6.next();
            param.accept(this);
         }

         var6 = funcDef.body.iterator();

         while(var6.hasNext()) {
            Expression expr = (Expression)var6.next();
            expr.accept(this);
         }

         this.currentScope = this.currentScope.getParent();
         return null;
      }
   }

   public Void visit(Formal formal) {
      String parentFuncName = ((FunctionSymbol)this.currentScope).getName();
      String className = ((TypeSymbol)this.currentScope.getParent()).getName();
      String formalName = formal.name.getText();
      if (formalName.equals("self")) {
         SymbolTable.error(formal.context, formal.name, "Method " + parentFuncName + " of class " + className + " has formal parameter with illegal name self");
         return null;
      } else {
         String formalType = formal.type.getText();
         if (formalType.equals(TypeSymbol.SELF_TYPE.getName())) {
            SymbolTable.error(formal.context, formal.type, "Method " + parentFuncName + " of class " + className + " has formal parameter " + formalName + " with illegal type SELF_TYPE");
            return null;
         } else {
            IdSymbol symbol = new IdSymbol(formalName, formal.symbol);
            symbol.setType(new TypeSymbol(formalName, formalType));
            if (!((FunctionSymbol)this.currentScope).add(symbol)) {
               SymbolTable.error(formal.context, formal.name, "Method " + parentFuncName + " of class " + className + " redefines formal parameter " + formalName);
               return null;
            } else {
               formal.idSymbol = symbol;
               return null;
            }
         }
      }
   }

   public Void visit(StrNode strNode) {
      return null;
   }

   public Void visit(Bool bool) {
      return null;
   }

   public Void visit(Id id) {
      return null;
   }

   public Void visit(BinaryOperation operation) {
      operation.right.accept(this);
      operation.left.accept(this);
      return null;
   }

   public Void visit(SingleExpr singleExpr) {
      singleExpr.expr.accept(this);
      return null;
   }

   public Void visit(New neww) {
      return null;
   }

   public Void visit(Attrib attrib) {
      attrib.val.accept(this);
      return null;
   }

   public Void visit(ImplicitDispatch implicitDispatch) {
      return null;
   }

   public Void visit(ExplicitDispatch explicitDispatch) {
      return null;
   }

   public Void visit(If iff) {
      iff.then.accept(this);
      if (iff.elsee != null) {
         iff.elsee.accept(this);
      }

      return null;
   }

   public Void visit(While whilee) {
      whilee.body.accept(this);
      return null;
   }

   public Void visit(VarDef varDef) {
      String name = varDef.name.getText();
      if (name.equals("self")) {
         SymbolTable.error(varDef.context, varDef.name, "Let variable has illegal name self");
         return null;
      } else {
         IdSymbol varSymbol;
         if (varDef.val != null) {
            varSymbol = new IdSymbol(name, varDef.val.symbol);
         } else {
            if (varDef.type.getText().equals(TypeSymbol.STRING.getName())) {
               varSymbol = new IdSymbol(name, "");
            } else if (varDef.type.getText().equals(TypeSymbol.BOOL.getName())) {
               varSymbol = new IdSymbol(name, "false");
            } else if (varDef.type.getText().equals(TypeSymbol.INT.getName())) {
               varSymbol = new IdSymbol(name, 0);
            } else {
               varSymbol = new IdSymbol(name, 0);
            }
         }

         varSymbol.setType((TypeSymbol) SymbolTable.globals.lookup(varDef.type.getText()));
         varDef.idSymbol = varSymbol;
         if (varDef.val != null) {
            varDef.val.accept(this);
         }

         return null;
      }
   }

   public Void visit(Let let) {
      LetSymbol letSymb = new LetSymbol(this.currentScope);
      let.letSymbol = letSymb;
      this.currentScope = letSymb;

      Iterator var3 = let.varDefs.iterator();

      while(var3.hasNext()) {
         VarDef varDef = (VarDef)var3.next();
         varDef.accept(this);
      }

      this.currentScope = this.currentScope.getParent();

      return null;
   }

   public Void visit(CaseOption caseOption) {
      String varName = caseOption.id.getText();
      if (varName.equals("self")) {
         SymbolTable.error(caseOption.context, caseOption.id, "Case variable has illegal name self");
         return null;
      } else {
         String caseType = caseOption.type.getText();
         if (caseType.equals("SELF_TYPE")) {
            SymbolTable.error(caseOption.context, caseOption.type, "Case variable " + varName + " has illegal type SELF_TYPE");
            return null;
         } else {
            IdSymbol symbol = new IdSymbol(caseOption.id.getText());
            symbol.setType(new TypeSymbol(this.currentScope.getName(), caseType));
            caseOption.idSymbol = symbol;
            CaseBranch caseScope = new CaseBranch(symbol, this.currentScope);
            caseScope.add(symbol);
            this.currentScope = caseScope;
            caseOption.body.accept(this);
            this.currentScope = caseScope.getParent();
            return null;
         }
      }
   }

   public Void visit(Case casee) {
      Iterator var2 = casee.branches.iterator();

      while(var2.hasNext()) {
         CaseOption caseOption = (CaseOption)var2.next();
         caseOption.accept(this);
      }

      return null;
   }

   public Void visit(Block block) {
      Iterator var2 = block.expressions.iterator();

      while(var2.hasNext()) {
         Expression expr = (Expression)var2.next();
         expr.accept(this);
      }

      return null;
   }
}
