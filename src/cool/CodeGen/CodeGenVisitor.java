package cool.CodeGen;

import cool.AST.*;
import cool.structures.*;
import cool.structures.symbols.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.Pair;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import javax.print.attribute.standard.Finishings;

public class CodeGenVisitor implements ASTVisitor<ST> {
   static STGroupFile templates = new STGroupFile("cgen.stg");
   private ST constants;
   private ST classNames;
   private ST objTabs;
   private ST dispatchTables;
   private ST classProtObjs;
   private ST methods;
   Scope currentScope;

   int methodCount = 0;
   int ifCount = 0;
   int isvoid_count = 0;
   int eq_cnt = 0;
   int not_count = 0;
   int neg_count = 0;
   int compare_cnt = 0;
   int while_count = 0;
   int case_count = 0;
   int case_branch_count = 0;

   String fileName;
   HashSet<Integer> intConstants;
   ArrayList<String> stringConstants;
   HashMap<String, String> stringConstantsValues = new HashMap<>();

   public ST visit(ClassDef classDef) {
      String className = classDef.name.getText();
      TypeSymbol classType = (TypeSymbol)SymbolTable.globals.lookup(className);
      this.currentScope = classType;

      String classMemberDefs = classDef.features.stream()
              .filter((featurex) -> featurex instanceof ClassMemberDef && ((ClassMemberDef)featurex).expression != null)
              .map((classMember) -> classMember.accept(this))
              .filter(Objects::nonNull).map(ST::render)
              .collect(Collectors.joining("\n"));

      ST initMethod = templates.getInstanceOf("initClass")
              .add("class_name", className)
              .add("parent_name", classType.getParentName())
              .add("classMembers", classMemberDefs);

      this.methods.add("e", initMethod);
      Collections.reverse(classDef.features);
      Iterator var6 = classDef.features.iterator();

      while(var6.hasNext()) {
         Feature feature = (Feature)var6.next();
         if (feature instanceof FuncDef) {
            this.methods.add("e", feature.accept(this));
         }
      }

      return null;
   }

   public CodeGenVisitor(String fileName) {
      this.constants = templates.getInstanceOf("sequence");
      this.classNames = templates.getInstanceOf("sequence");
      this.objTabs = templates.getInstanceOf("sequence");
      this.dispatchTables = templates.getInstanceOf("sequence");
      this.classProtObjs = templates.getInstanceOf("sequence");
      this.methods = templates.getInstanceOf("sequenceSpaced");
      this.methodCount = -1;
      this.intConstants = new HashSet();
      this.stringConstants = new ArrayList<>();
      this.fileName = fileName;

      this.createNewIntConstant(0);
      this.createStringConstant("");
      TypeSymbol[] var2 = new TypeSymbol[]{TypeSymbol.OBJECT, TypeSymbol.BOOL, TypeSymbol.STRING, TypeSymbol.INT, TypeSymbol.IO};
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TypeSymbol basicType = var2[var4];
         this.methods.add("e", basicType.createInitMethod(templates));
      }

      List<Symbol> sortedSymbols = SymbolTable.globals.getSymbols()
              .stream().sorted(Comparator.comparingInt((ts) -> ((TypeSymbol)ts).getTag()))
              .collect(Collectors.toList());

      for (Symbol classSymbol : sortedSymbols) {
         String classSymbolName = classSymbol.getName();
         if (!classSymbolName.equals(TypeSymbol.SELF_TYPE.getName()) && !classSymbolName.equals(TypeSymbol.FLOAT.getName())) {
            ((TypeSymbol) classSymbol).setMethodsOffsets();
            ST var10000 = this.classNames;
            String var10002 = this.createStringConstant(classSymbolName);
            var10000.add("e", "\t.word\t" + var10002);
            this.objTabs.add("e", templates.getInstanceOf("classObjTab").add("className", classSymbolName));
            ST dispTable = ((TypeSymbol) classSymbol).createDispatchTable(templates);
            this.dispatchTables.add("e", dispTable);
            this.classProtObjs.add("e", ((TypeSymbol) classSymbol).createProtObj(templates));
         }
      }
   }

   public ST visit(Program program) {
      ST intTag = templates.getInstanceOf("intStrBoolTag").add("name", "int").add("tag", TypeSymbol.INT.getTag());
      ST strTag = templates.getInstanceOf("intStrBoolTag").add("name", "string").add("tag", TypeSymbol.STRING.getTag());
      ST boolTag = templates.getInstanceOf("intStrBoolTag").add("name", "bool").add("tag", TypeSymbol.BOOL.getTag());
      ST intStrBoolTags = templates.getInstanceOf("sequence").add("e", intTag).add("e", strTag).add("e", boolTag);
      program.classes.forEach(this::visit);
      return templates.getInstanceOf("program").add("intStrBoolTags", intStrBoolTags).add("constants", this.constants).add("classes_name", this.classNames).add("classes_obj", this.objTabs).add("classes_disp_table", this.dispatchTables).add("methods", this.methods).add("boolTag", TypeSymbol.BOOL.getTag()).add("classes_prot_obj", this.classProtObjs);
   }

   public ST visit(ClassMemberDef classMemberDef) {
      ST leftPartST = (ST)classMemberDef.expression.accept(this);
      return leftPartST == null ? null :
              templates.getInstanceOf("attribClassMember")
                      .add("val", leftPartST.render())
                      .add("offset",
                              ((TypeSymbol)this.currentScope).getIndexOf(classMemberDef.name.getText()) * 4);
   }

   String createNewIntConstant(Integer intToAdd) {
      String constantName = "int_const" + intToAdd;
      if (!this.intConstants.contains(intToAdd)) {
         this.intConstants.add(intToAdd);
         this.constants.add("e", templates.getInstanceOf("intConstant").add("intTag", TypeSymbol.INT.getTag()).add("value", intToAdd));
      }

      return constantName;
   }

   public ST visit(Int intt) {
      return templates.getInstanceOf("literal")
              .add("value", this.createNewIntConstant(Integer.parseInt(intt.symbol)));
   }

   public ST visit(FuncDef funcDef) {
      String className = ((TypeSymbol)this.currentScope).getName();
      this.currentScope = funcDef.symbol;

      String bodyResult = "";

      for (var expr : funcDef.body) {
         bodyResult += expr.accept(this).render();
      }

      ST funcST = templates.getInstanceOf("funcDef")
              .add("className", className)
              .add("funcName", funcDef.name.getText())
              .add("paramsOffset", funcDef.formals.size() * 4 + 12)
              .add("body", bodyResult);

      this.currentScope = this.currentScope.getParent();
      return funcST;
   }

   public ST visit(Formal formal) {
      return null;
   }

   private String createStringConstant(String str) {
      int position = this.stringConstants.size();
      if (!this.stringConstants.contains(str)) {
         this.stringConstants.add(str);
         int str_len = str.length();
         ST stringConstant = templates
                 .getInstanceOf("strConstant")
                 .add("position", position)
                 .add("strTag", TypeSymbol.STRING.getTag())
                 .add("value", str)
                 .add("words", (str_len + 1) / 4 + 5)
                 .add("actual_len", this.createNewIntConstant(str_len));

         this.stringConstantsValues.put("str_const" + position, str);
         this.constants.add("e", stringConstant);
      } else {
         position = this.stringConstants.indexOf(str);
      }

      return "str_const" + position;
   }

   public ST visit(StrNode strNode) {
      return templates.getInstanceOf("literal").
              add("value", this.createStringConstant(strNode.symbol));
   }

   public ST visit(Bool bool) {
      Integer value = bool.symbol.equals("false") ? 0 : 1;
      return templates.getInstanceOf("literal").add("value", "bool_const" + value);
   }

   private TypeSymbol getParentClassScope(Scope typeToLookInto) {
      var currentParent = typeToLookInto;

      while(!(currentParent instanceof TypeSymbol)) {
         currentParent = currentParent.getParent();
      }

      return (TypeSymbol) currentParent;
   }

   public Object[] getIndexFromLet(Scope scope, String idName, TypeSymbol parentScope) {
      int index = 0;
      String templateName = "ClassMember";

      if (scope.lookupNoParent(idName) != null) {
         index = ((LetSymbol) scope).getOffsetOf(idName);
         templateName = "LocalVar";
      } else if (scope.getParent() instanceof FunctionSymbol && scope.getParent().lookupNoParent(idName) != null) {
         templateName = "LocalVar";
         index = ((FunctionSymbol) scope.getParent()).getIndexOf(idName) + 12;
      } else {
         index = parentScope.getIndexOf(idName) * 4;
      }

      return new Object[]{index, templateName};
   }

   public ST visit(Id id) {
      String idName = id.symbol;

      if (idName.equals("self")) {
         return templates.getInstanceOf("self");
      }

      String templateName = "getClassMember";
      TypeSymbol parentScope = this.getParentClassScope(currentScope);

      int index = 0;
      // function or let
      if (currentScope instanceof FunctionSymbol && currentScope.lookupNoParent(idName) != null) {
         templateName = "getLocalVar";
         index = ((FunctionSymbol) this.currentScope).getIndexOf(idName) + 12;
      } else if (currentScope instanceof LetSymbol) {
         var indexAndTemplate = getIndexFromLet(currentScope, idName, parentScope);
         index = (int) indexAndTemplate[0];
         templateName = "get" + indexAndTemplate[1];
      } else if (currentScope instanceof CaseBranch) {
         if (currentScope.lookupNoParent(idName) != null) {
            templateName = "getLocalVar";
            index = -4;
         } else if (currentScope.getParent() instanceof FunctionSymbol && currentScope.getParent().lookupNoParent(idName) != null) {
            templateName = "getLocalVar";
            index = ((FunctionSymbol) this.currentScope.getParent()).getIndexOf(idName) + 12;
         } else if (currentScope.getParent() instanceof LetSymbol) {
            var indexAndTemplate = getIndexFromLet(currentScope.getParent(), idName, parentScope);
            index = (int) indexAndTemplate[0];
            templateName = "get" + indexAndTemplate[1];
         } else {
            index = parentScope.getIndexOf(idName) * 4;
         }
      } else {
         index = parentScope.getIndexOf(idName) * 4;
      }

      return templates.getInstanceOf(templateName).add("offset", index);
   }

   public ST visit(BinaryOperation operation) {
      var leftST = operation.left.accept(this);
      var rightST = operation.right.accept(this);
      var opToken = operation.opTok.getText();

      if (opToken.equals("+")) {
         return templates.getInstanceOf("binaryOperation")
                 .add("left", leftST.render())
                 .add("right", rightST.render())
                 .add("op", "add");
      } else if (opToken.equals("-")) {
         return templates.getInstanceOf("binaryOperation")
                 .add("left", leftST.render())
                 .add("right", rightST.render())
                 .add("op", "sub");
      } else if (opToken.equals("*")) {
         return templates.getInstanceOf("binaryOperation")
                 .add("left", leftST.render())
                 .add("right", rightST.render())
                 .add("op", "mul");
      } else if (opToken.equals("/")) {
         return templates.getInstanceOf("binaryOperation")
                 .add("left", leftST.render())
                 .add("right", rightST.render())
                 .add("op", "div");
      } else if (opToken.equals("=")) {
         return templates.getInstanceOf("equality")
                 .add("left", leftST.render())
                 .add("right", rightST.render())
                 .add("eq_cnt", eq_cnt++);
      }

      var compareST = templates.getInstanceOf("compare")
                 .add("left", leftST.render())
                 .add("right", rightST.render())
                 .add("compare_cnt", compare_cnt++);
      String op = "";
      if (opToken.equals("<")) {
         op = "blt";
      } else if (opToken.equals(">")) {
         op = "bgt";
      } else if (opToken.equals(">=")) {
         op = "bge";
      } else if (opToken.equals("<=")) {
         op = "ble";
      }

      return compareST.add("op", op);
   }

   public ST visit(SingleExpr singleExpr) {
      if (singleExpr.symbol.equals("isvoid")) {
         return templates.getInstanceOf("isvoid")
                 .add("expr", singleExpr.expr.accept(this))
                 .add("isvoid_count", isvoid_count++);
      } else if (singleExpr.symbol.equals("not")) {
         return templates.getInstanceOf("not")
                 .add("expr", singleExpr.expr.accept(this))
                 .add("not_count", not_count++);
      } else if (singleExpr.symbol.equals("~")) {
         return templates.getInstanceOf("neg")
                 .add("expr", singleExpr.expr.accept(this))
                 .add("neg_count", neg_count++);
      }
      return null;
   }

   public ST visit(New neww) {
      var newTypeName = neww.type.getText();

      if (newTypeName.equals(TypeSymbol.SELF_TYPE.getName())) {
         return templates.getInstanceOf("newSelfType");
      }

      return templates.getInstanceOf("new").add("class_name", newTypeName);
   }

   public ST visit(Attrib attrib) {
      boolean isLocalVar = false;
      var idName = attrib.name;
      TypeSymbol parentScope = getParentClassScope(currentScope);

      if (currentScope instanceof LetSymbol || currentScope instanceof FunctionSymbol) {
         if (currentScope.lookupNoParent(idName) != null ||
                 (currentScope instanceof LetSymbol && currentScope.getParent().lookupNoParent(idName) != null)) {
            isLocalVar = true;
         }
      }

      Scope scopeToUpdate;

      // Get the scope we need to update the new value in
      if ((currentScope instanceof FunctionSymbol || currentScope instanceof LetSymbol)
              && currentScope.lookupNoParent(idName) != null) {
         scopeToUpdate = currentScope;
      } else if (currentScope instanceof LetSymbol && currentScope.getParent().lookupNoParent(idName) != null) {
         scopeToUpdate = currentScope.getParent();
      } else {
         scopeToUpdate = parentScope;
      }

      ST leftPartST = attrib.val.accept(this);
      var idSymbol = (IdSymbol) currentScope.lookup(attrib.name);
      idSymbol.setValue(attrib.val.symbol);
      scopeToUpdate.updateSymbol(idSymbol);

      int index = 0;
      String templateName = "attribLocalVar";

      if (isLocalVar) {
         if (currentScope instanceof FunctionSymbol) {
            index = ((FunctionSymbol)this.currentScope).getIndexOf(attrib.name) + 12;
         } else if (currentScope instanceof LetSymbol) {
            var indexAndTemplate = getIndexFromLet(currentScope, attrib.name, parentScope);
            index = (int) indexAndTemplate[0];
            templateName = "attrib" + indexAndTemplate[1];
         } else if (currentScope instanceof CaseBranch) {
            if (currentScope.lookupNoParent(attrib.name) != null) {
               index = -4;
            } else if (currentScope.getParent()  instanceof LetSymbol) {
               var indexAndTemplate = getIndexFromLet(currentScope.getParent(), attrib.name, parentScope);
               index = (int) indexAndTemplate[0];
               templateName = "attrib" + indexAndTemplate[1];
            }
         }
      } else {
         index = parentScope.getIndexOf(attrib.name) * 4;
         templateName = "attribClassMember";
      }

      return templates.getInstanceOf(templateName)
              .add("val", leftPartST).add("offset", index);
   }

   public String putParamsOnStack(List<Expression> params) {
      String paramsOnStack = "";

      for(var param : params) {
         paramsOnStack += templates.getInstanceOf("funcParam")
                 .add("param", param.accept(this)).render() + "\n";
      }

      return paramsOnStack;
   }

   public ST visit(ImplicitDispatch implicitDispatch) {
      ++this.methodCount;
      List<Expression> params = implicitDispatch.args;
      Collections.reverse(params);
      TypeSymbol parentScope = this.getParentClassScope(currentScope);
      FunctionSymbol functionSymbol = parentScope.lookupAllFunc(implicitDispatch.name);

      return templates.getInstanceOf("implicit_dispatch")
              .add("method_id", this.methodCount)
              .add("filename", this.createStringConstant(this.fileName))
              .add("line", implicitDispatch.functionToken.getLine())
              .add("method_offset", functionSymbol.getOffset() * 4)
              .add("params", this.putParamsOnStack(params));
   }

   public ST visit(ExplicitDispatch explicitDispatch) {
      ++this.methodCount;

      List<Expression> params = explicitDispatch.args;
      Collections.reverse(params);

      TypeSymbol typeToLookInto = explicitDispatch.callerType;

      if (typeToLookInto == null) {
         var symb = (IdSymbol) currentScope.lookup(explicitDispatch.initial.symbol);
         typeToLookInto = (TypeSymbol) SymbolTable.globals.lookup(symb.getType().getName());
      }

      if (typeToLookInto == TypeSymbol.SELF_TYPE) {
         typeToLookInto = getParentClassScope(currentScope);
      }

      FunctionSymbol functionSymbol = typeToLookInto.lookupAllFunc(explicitDispatch.name);
      var offset = 0;

      ST explicitST = templates.getInstanceOf("explicit_dispatch")
              .add("method_id", this.methodCount)
              .add("filename", this.createStringConstant(this.fileName))
              .add("line", explicitDispatch.functionToken.getLine())
              .add("params", this.putParamsOnStack(params))
              .add("member_caller", explicitDispatch.initial.accept(this));

      if (explicitDispatch.atType != null) {
         explicitST.add("atType", explicitDispatch.atType.getText());
         var dispatchClass = (TypeSymbol) SymbolTable.globals.lookup(explicitDispatch.atType.getText());
         var functionInDispatchClass = dispatchClass.lookupAllFunc(explicitDispatch.name);
         offset = functionInDispatchClass.getOffset();
      } else {
         offset = functionSymbol.getOffset();
      }

      return explicitST.add("method_offset", offset * 4);
   }

   public ST visit(If iff) {
      return templates.getInstanceOf("if")
              .add("cond", iff.cond.accept(this).render())
              .add("then", iff.then.accept(this).render())
              .add("elsee", iff.elsee.accept(this).render())
              .add("if_count", ifCount++);
   }

   public ST visit(While whilee) {
      return templates.getInstanceOf("while")
              .add("cond", whilee.cond.accept(this))
              .add("body", whilee.body.accept(this))
              .add("while_count", while_count++);
   }

   public ST visit(VarDef varDef) {
      var idSymb = varDef.idSymbol;

      String init_constant;
      if (varDef.val == null) {
         // init with zero
         if (idSymb.getType().getName().equals(TypeSymbol.INT.getName())) {
            init_constant = "\tla\t$a0 int_const0";
         } else if (idSymb.getType().getName().equals(TypeSymbol.STRING.getName())) {
            init_constant = "\tla\t$a0 str_const0";
         } else if (idSymb.getType().getName().equals(TypeSymbol.BOOL.getName())) {
            init_constant = "\tla\t$a0 bool_const0";
         } else {
            // other objects are 0 by default
            init_constant = "\tli\t$a0 0";
         }
      } else {
         init_constant = varDef.val.accept(this).render();
      }

      return templates.getInstanceOf("attribLocalVar")
              .add("val", init_constant)
              .add("offset", idSymb.getOffset());
   }

   public ST visit(Let let) {
      int offset = let.varDefs.size() * 4;
      ST letLocalsAlloc = templates.getInstanceOf("letLocalsAlloc").add("offset", -offset);
      ST letLocalsDealloc = templates.getInstanceOf("letLocalsAlloc").add("offset", offset);
      LetSymbol letSymbol = let.letSymbol;
      letSymbol.setOffsets();

      this.currentScope = letSymbol;

      String varDefsST = "";

      for (var varDef : let.varDefs) {
         varDefsST += varDef.accept(this).render() + "\n";
      }

      letLocalsAlloc.add("stored_params", varDefsST);

      ST letST = templates.getInstanceOf("sequence")
              .add("e", letLocalsAlloc)
              .add("e", let.body.accept(this))
              .add("e", letLocalsDealloc);
      this.currentScope = this.currentScope.getParent();

      return letST;
   }

   public ST visit(CaseOption caseOption) {
      case_branch_count++;
      var caseType = (TypeSymbol) SymbolTable.globals.lookup(caseOption.type.getText());

      currentScope = caseOption.caseScope;

      // maxTag = maximul tagului caruia i se poate face downcast la un anumit tip
      var branchST =  templates.getInstanceOf("caseOption")
              .add("next_branch_count", case_branch_count)
              .add("case_count", case_count)
              .add("body", caseOption.body.accept(this))
              .add("class_tag", caseType.getTag())
              .add("class_max_tag", caseType.getMaxTagInSubtree());

      currentScope = currentScope.getParent();
      return branchST;
   }

   public ST visit(Case casee) {
      // branch-urile sunt ordonate in functie de tag-ul tipurilor
      casee.branches.sort(
              Comparator.comparingInt(
                      branch -> -1 * ((TypeSymbol)SymbolTable.globals.lookup(branch.type.getText())).getTag()));

      var branchesSTRendered = "";

      for (var branch : casee.branches) {
         branchesSTRendered += branch.accept(this).render() + "\n";
      }

      return templates.getInstanceOf("case")
              .add("filename", createStringConstant(fileName))
              .add("line", casee.lineToken.getLine())
              .add("branches", branchesSTRendered)
              .add("expr", casee.expr.accept(this))
              .add("case_count", case_count++);
   }

   public ST visit(Block block) {
      ST blockSeq = templates.getInstanceOf("sequence");
      block.expressions.forEach((expr) -> {
         blockSeq.add("e", expr.accept(this));
      });
      return blockSeq;
   }
}
