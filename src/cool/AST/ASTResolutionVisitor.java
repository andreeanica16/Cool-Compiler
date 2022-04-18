package cool.AST;

import cool.parser.CoolParser.AttribContext;
import cool.parser.CoolParser.ClassMemberDefContext;
import cool.parser.CoolParser.ExplicitDispatchContext;
import cool.parser.CoolParser.ExprContext;
import cool.parser.CoolParser.FuncDefContext;
import cool.parser.CoolParser.IfContext;
import cool.parser.CoolParser.ImplicitDispatchContext;
import cool.parser.CoolParser.VarDefContext;
import cool.parser.CoolParser.WhileContext;
import cool.structures.CaseBranch;
import cool.structures.Scope;
import cool.structures.SymbolTable;
import cool.structures.symbols.FunctionSymbol;
import cool.structures.symbols.IdSymbol;
import cool.structures.symbols.LetSymbol;
import cool.structures.symbols.TypeSymbol;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class ASTResolutionVisitor implements ASTVisitor<TypeSymbol> {
   Scope currentScope;
   boolean isSelfType = false;

   public TypeSymbol visit(ClassDef classDef) {
      this.isSelfType = false;
      if (classDef.type == null) {
         return null;
      } else {
         this.currentScope = classDef.type;
         if (classDef.inherited != null) {
            String className = classDef.name.getText();
            TypeSymbol parentScope = (TypeSymbol)this.currentScope;

            while(parentScope != null) {
               String parentName = parentScope.getParentName();
               if (parentName.equals(className)) {
                  SymbolTable.error(classDef.context, classDef.name, "Inheritance cycle for class " + className);
                  return null;
               }

               parentScope = (TypeSymbol)SymbolTable.globals.lookup(parentName);
               if (parentScope == null && !parentName.equals("")) {
                  SymbolTable.error(classDef.context, classDef.inherited, "Class " + className + " has undefined parent " + parentName);
                  return null;
               }
            }

            TypeSymbol parent = (TypeSymbol)SymbolTable.globals.lookup(((TypeSymbol)this.currentScope).getParentName());
            ((TypeSymbol)this.currentScope).setParent(parent);
            classDef.type.setParent(parent);
         } else {
            TypeSymbol parent = (TypeSymbol)SymbolTable.globals.lookup("Object");
            ((TypeSymbol)this.currentScope).setParent(parent);
            classDef.type.setParent(parent);
         }

         Iterator var6 = classDef.features.iterator();

         while(var6.hasNext()) {
            Feature feature = (Feature)var6.next();
            feature.accept(this);
         }
         return null;
      }
   }


   public TypeSymbol visit(Program program) {
      this.isSelfType = false;
      Iterator var2 = program.classes.iterator();

      while(var2.hasNext()) {
         ClassDef cl = (ClassDef)var2.next();
         cl.accept(this);
      }

      return null;
   }

   public TypeSymbol visit(ClassMemberDef classMemberDef) {
      this.isSelfType = false;
      IdSymbol symbol = classMemberDef.symbol;
      if (symbol == null) {
         return null;
      } else {
         String symbolName = classMemberDef.name.getText();
         Scope parentScope = this.currentScope.getParent();
         String className = ((TypeSymbol)this.currentScope).getName();
         TypeSymbol classMemberType = (TypeSymbol)SymbolTable.globals.lookup(classMemberDef.type.getText());
         if (classMemberType == null) {
            SymbolTable.error(classMemberDef.context, classMemberDef.type, "Class " + className + " has attribute " + symbolName + " with undefined type " + classMemberDef.type.getText());
            return null;
         } else {
            while(parentScope != null) {
               if (parentScope.lookup(symbolName) != null) {
                  SymbolTable.error(classMemberDef.context, classMemberDef.name, "Class " + className + " redefines inherited attribute " + symbol);
                  return null;
               }

               parentScope = parentScope.getParent();
            }

            if (classMemberDef.expression != null) {
               TypeSymbol assignType = (TypeSymbol)classMemberDef.expression.accept(this);
               if (assignType != null && !this.isAncestor(assignType, classMemberType)) {
                  String type = this.isSelfType ? "SELF_TYPE" : assignType.getName();
                  SymbolTable.error(classMemberDef.context, ((ClassMemberDefContext)classMemberDef.context).val.start, "Type " + type + " of initialization expression of attribute " + symbolName + " is incompatible with declared type " + classMemberType.getName());
                  this.isSelfType = false;
                  return null;
               }
            }

            return classMemberType;
         }
      }
   }

   public TypeSymbol visit(Int intt) {
      this.isSelfType = false;
      return TypeSymbol.INT;
   }

   public TypeSymbol visit(FuncDef funcDef) {
      this.isSelfType = false;
      FunctionSymbol symbol = funcDef.symbol;
      if (symbol == null) {
         return null;
      } else {
         String className = ((TypeSymbol)this.currentScope).getName();
         Scope parentScope = this.currentScope.getParent();
         String funcName = funcDef.name.getText();
         TypeSymbol returnType = (TypeSymbol)SymbolTable.globals.lookup(funcDef.type.getText());
         if (returnType == null) {
            SymbolTable.error(funcDef.context, funcDef.type, "Class " + className + " has method " + funcName + " with undefined return type " + funcDef.type.getText());
            return null;
         } else {
            List<Formal> actualParams = funcDef.formals;
            Iterator var8 = actualParams.iterator();

            String actualRetType;
            while(var8.hasNext()) {
               Formal formal = (Formal)var8.next();
               actualRetType = formal.type.getText();
               if (SymbolTable.globals.lookup(actualRetType) == null) {
                  SymbolTable.error(formal.context, formal.type, "Method " + funcName + " of class " + className + " has formal parameter " + formal.name.getText() + " with undefined type " + actualRetType);
                  return null;
               }
            }

            FunctionSymbol overridden = null;
            if (parentScope != null) {
               overridden = ((TypeSymbol)parentScope).lookupFunc(funcName);
               if (overridden != null) {
                  String formalRetType = overridden.getReturnType();
                  if (formalRetType.equals("SELF_TYPE")) {
                     returnType = ((TypeSymbol)parentScope).lookupFuncDefined(funcName);
                  } else {
                     returnType = (TypeSymbol)SymbolTable.globals.lookup(formalRetType);
                  }

                  actualRetType = funcDef.type.getText();
                  if (!formalRetType.equals(actualRetType)) {
                     SymbolTable.error(funcDef.context, funcDef.type, "Class " + className + " overrides method " + funcName + " but changes return type from " + formalRetType + " to " + actualRetType);
                     return null;
                  }

                  ArrayList<IdSymbol> formalParams = overridden.getFormals();
                  if (formalParams.size() != actualParams.size()) {
                     SymbolTable.error(funcDef.context, funcDef.name, "Class " + className + " overrides method " + funcName + " with different number of formal parameters");
                     return null;
                  }

                  for(int i = 0; i < formalParams.size(); ++i) {
                     if (((IdSymbol)formalParams.get(i)).getType() != null) {
                        TypeSymbol formalType = formalParams.get(i).getType();
                        TypeSymbol actualType = (TypeSymbol)SymbolTable.globals.lookup(actualParams.get(i).type.getText());
                        if (!this.isAncestor(formalType, actualType)) {
                           String formalName = actualParams.get(i).name.getText();
                           SymbolTable.error(funcDef.context, ((FuncDefContext)funcDef.context).formal(i).stop, "Class " + className + " overrides method " + funcName + " but changes type of formal parameter " + formalName + " from " + formalType.getName() + " to " + actualType.getName());
                           return null;
                        }
                     }
                  }
               }
            }

            this.currentScope = symbol;
            Iterator var18 = funcDef.formals.iterator();

            while(var18.hasNext()) {
               Formal formal = (Formal)var18.next();
               formal.accept(this);
            }

            TypeSymbol bodyType = null;
            Iterator var21 = funcDef.body.iterator();

            while(var21.hasNext()) {
               Expression expr = (Expression)var21.next();
               TypeSymbol exprType = expr.accept(this);
               if (exprType != null) {
                  bodyType = exprType;
               }
            }

            this.currentScope = symbol.getParent();
            if (bodyType != null && overridden != null &&
                    funcDef.type.getText().equals(TypeSymbol.SELF_TYPE.getName()) &&
                    !this.isAncestor(returnType, bodyType)) {
               if (overridden.getReturnType().equals(TypeSymbol.SELF_TYPE.getName())) {
                  var classNameType =  (TypeSymbol)SymbolTable.globals.lookup(className);
                  if (!isAncestor(bodyType, classNameType)) {
                     SymbolTable.error(funcDef.context,
                             ((FuncDefContext)funcDef.context).expr.start, "Type " + bodyType.getName() + " of the body of method " +
                                     funcName + " is incompatible with declared return type " + classNameType.getName());

                     return null;
                  }
               }
            } else if (bodyType != null && !this.isAncestor(bodyType, returnType)) {
               SymbolTable.error(funcDef.context, ((FuncDefContext)funcDef.context).expr.start,
                       "Type " +
                               bodyType.getName() +
                               " of the body of method " +
                               funcName + " is incompatible with declared return type " + returnType.getName());
               return null;
            }

            if (returnType.getName().equals(TypeSymbol.SELF_TYPE.getName())) {
               returnType = (TypeSymbol)SymbolTable.globals.lookup(className);
            }

            return returnType;
         }
      }
   }

   public TypeSymbol visit(Formal formal) {
      this.isSelfType = false;
      return null;
   }

   public TypeSymbol visit(StrNode strNode) {
      this.isSelfType = false;
      return TypeSymbol.STRING;
   }

   public TypeSymbol visit(Bool bool) {
      this.isSelfType = false;
      return TypeSymbol.BOOL;
   }

   public TypeSymbol visit(Id id) {
      this.isSelfType = false;
      IdSymbol symbol = (IdSymbol)this.currentScope.lookup(id.symbol);
      if (symbol != null) {
         return symbol.getType();
      } else if (!id.symbol.equals("self")) {
         SymbolTable.error(id.context, id.context.stop, "Undefined identifier " + id.symbol);
         return null;
      } else {
         this.isSelfType = true;

         Scope scope;
         for(scope = this.currentScope; !(scope instanceof TypeSymbol); scope = scope.getParent()) {
         }

         return (TypeSymbol)scope;
      }
   }

   boolean canCompareEqual(TypeSymbol left, TypeSymbol right) {
      String leftName = left.getName();
      String rightName = right.getName();
      if ((!leftName.equals(TypeSymbol.INT.getName()) || rightName.equals(TypeSymbol.INT.getName())) && (!rightName.equals(TypeSymbol.INT.getName()) || leftName.equals(TypeSymbol.INT.getName()))) {
         if ((!leftName.equals(TypeSymbol.BOOL.getName()) || rightName.equals(TypeSymbol.BOOL.getName())) && (!rightName.equals(TypeSymbol.BOOL.getName()) || leftName.equals(TypeSymbol.BOOL.getName()))) {
            return (!leftName.equals(TypeSymbol.STRING.getName()) || rightName.equals(TypeSymbol.STRING.getName())) && (!rightName.equals(TypeSymbol.STRING.getName()) || leftName.equals(TypeSymbol.STRING.getName()));
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public TypeSymbol visit(BinaryOperation operation) {
      this.isSelfType = false;
      String op = operation.symbol;
      TypeSymbol left = (TypeSymbol)operation.left.accept(this);
      TypeSymbol right = (TypeSymbol)operation.right.accept(this);
      if (left != null && right != null) {
         if (op.equals("=") && !this.canCompareEqual(left, right)) {
            ParserRuleContext var10000 = operation.context;
            Token var10001 = operation.opTok;
            String var10002 = left.getName();
            SymbolTable.error(var10000, var10001, "Cannot compare " + var10002 + " with " + right.getName());
            return null;
         } else {
            String leftName = left.getName();
            String rightName = right.getName();
            if (op.equals("-") || op.equals("+") || op.equals("~") || op.equals("*") || op.equals("/") || op.equals("<") || op.equals("<=")) {
               if (!leftName.equals(TypeSymbol.INT.getName()) && rightName.equals(TypeSymbol.INT.getName())) {
                  SymbolTable.error(operation.context, operation.context.start, "Operand of " + op + " has type " + leftName + " instead of Int");
                  return null;
               }

               if (!rightName.equals(TypeSymbol.INT.getName()) && leftName.equals(TypeSymbol.INT.getName())) {
                  SymbolTable.error(operation.context, operation.context.stop, "Operand of " + op + " has type " + rightName + " instead of Int");
                  return null;
               }
            }

            return operation instanceof Relational ? TypeSymbol.BOOL : TypeSymbol.INT;
         }
      } else {
         return operation instanceof Relational ? TypeSymbol.BOOL : TypeSymbol.INT;
      }
   }

   public TypeSymbol visit(SingleExpr singleExpr) {
      this.isSelfType = false;
      TypeSymbol expr = (TypeSymbol)singleExpr.expr.accept(this);
      if (expr == null) {
         return null;
      } else {
         String exprName = expr.getName();
         if (singleExpr.symbol.equals("not") && !exprName.equals(TypeSymbol.BOOL.getName())) {
            SymbolTable.error(singleExpr.context, singleExpr.context.stop, "Operand of not has type " + exprName + " instead of Bool");
            return TypeSymbol.BOOL;
         } else if (singleExpr.symbol.equals("~") && !exprName.equals(TypeSymbol.INT.getName())) {
            SymbolTable.error(singleExpr.context, singleExpr.context.stop, "Operand of ~ has type " + exprName + " instead of Int");
            return TypeSymbol.INT;
         } else {
            return singleExpr.symbol.equals("isvoid") ? TypeSymbol.BOOL : expr;
         }
      }
   }

   public TypeSymbol visit(New neww) {
      this.isSelfType = false;
      String typeName = neww.type.getText();
      if (!typeName.equals("SELF_TYPE")) {
         TypeSymbol newType = (TypeSymbol)SymbolTable.globals.lookup(typeName);
         if (newType == null) {
            SymbolTable.error(neww.context, neww.context.stop, "new is used with undefined type " + typeName);
         }
         return newType;
      } else {
         Scope scope = this.currentScope;
         this.isSelfType = true;

         while(!(scope instanceof  TypeSymbol)) {
            scope = scope.getParent();
         }

         return (TypeSymbol)scope;
      }
   }

   private TypeSymbol getIdType(String name) {
      this.isSelfType = false;
      if (!name.equals("SELF_TYPE")) {
         return (TypeSymbol)SymbolTable.globals.lookup(name);
      } else {
         this.isSelfType = true;

         Scope scope;
         for(scope = this.currentScope; !(scope instanceof TypeSymbol); scope = scope.getParent()) {
         }

         return (TypeSymbol)scope;
      }
   }

   private boolean isAncestor(TypeSymbol symbolA, TypeSymbol symbolB) {
      if (symbolA != null && symbolB != null) {
         if (symbolB.getName().equals(TypeSymbol.SELF_TYPE.getName())) {
            Scope scope;
            for(scope = this.currentScope; !(scope instanceof TypeSymbol); scope = scope.getParent()) {
            }

            String selfName = ((TypeSymbol)scope).getName();
            return symbolA.getName().equals(selfName);
         } else if (symbolB.getName().equals(TypeSymbol.OBJECT.getName())) {
            return true;
         } else {
            TypeSymbol baseClass = (TypeSymbol)SymbolTable.globals.lookup(symbolA.getName());
            if (baseClass == null) {
               return false;
            } else {
               for(TypeSymbol parentClass = (TypeSymbol)SymbolTable.globals.lookup(baseClass.getParentName()); parentClass != null && !baseClass.getName().equals(symbolB.getName()); parentClass = (TypeSymbol)SymbolTable.globals.lookup(parentClass.getParentName())) {
                  baseClass = parentClass;
               }

               return baseClass.getName().equals(symbolB.getName());
            }
         }
      } else {
         return false;
      }
   }

   public TypeSymbol visit(Attrib attrib) {
      this.isSelfType = false;
      String idName = attrib.name;
      if (idName.equals("self")) {
         SymbolTable.error(attrib.context, ((AttribContext)attrib.context).name, "Cannot assign to self");
         return null;
      } else {
         IdSymbol idSymbol = (IdSymbol)this.currentScope.lookup(idName);
         if (idSymbol == null) {
            return null;
         } else {
            TypeSymbol identifierType = this.getIdType(idSymbol.getType().getName());
            TypeSymbol attribType = attrib.val.accept(this);
            if (identifierType != null && attribType != null) {
               String attribTypeName = attribType.getName();
               String identifierTypeName = identifierType.getName();

               for(Scope classScope = this.currentScope; !(classScope instanceof TypeSymbol); classScope = classScope.getParent()) {
               }

               if (!identifierTypeName.equals(TypeSymbol.OBJECT.getName()) &&
                       !this.isAncestor(attribType, identifierType)) {
                  var selfTypeToWrite = attribTypeName;
                  if (this.isSelfType) {
                     selfTypeToWrite = "SELF_TYPE";
                  }

                  SymbolTable.error(attrib.context,
                          ((AttribContext)attrib.context).val.start,
                          "Type " + selfTypeToWrite + " of assigned expression is incompatible with declared type " +
                                  idSymbol.getType().getName() + " of identifier " + idName);
               }

               return attribType;
            } else {
               return null;
            }
         }
      }
   }

   public TypeSymbol visit(ImplicitDispatch implicitDispatch) {
      this.isSelfType = false;
      String methodName = implicitDispatch.name;

      Scope scope;
      for(scope = this.currentScope; !(scope instanceof TypeSymbol); scope = scope.getParent()) {
      }

      String parentName = ((TypeSymbol)scope).getName();
      TypeSymbol clType = (TypeSymbol)SymbolTable.globals.lookup(parentName);
      FunctionSymbol methodType = clType.lookupFunc(methodName);
      if (methodType == null) {
         SymbolTable.error(implicitDispatch.context,
                 ((ImplicitDispatchContext)implicitDispatch.context).ID().getSymbol(),
                 "Undefined method " + methodName + " in class " + clType.getName());
         return TypeSymbol.OBJECT;
      } else {
         ArrayList<IdSymbol> definedFormals = methodType.getFormals();
         List<Expression> actualFormals = implicitDispatch.args;
         if (definedFormals.size() != actualFormals.size()) {
            SymbolTable.error(implicitDispatch.context,
                    ((ImplicitDispatchContext)implicitDispatch.context).ID().getSymbol(),
                    "Method " + methodName + " of class " + clType.getName()
                            + " is applied to wrong number of arguments");
            return TypeSymbol.OBJECT;
         } else {
            for(int i = 0; i < definedFormals.size(); ++i) {
               IdSymbol defined = definedFormals.get(i);
               Expression actual = actualFormals.get(i);
               TypeSymbol actualType = actual.accept(this);
               if (!this.isAncestor(actualType, defined.getType())) {
                  SymbolTable.error(implicitDispatch.context,
                          ((ImplicitDispatchContext)implicitDispatch.context).expr().get(i).start,
                          "In call to method " + methodName + " of class " + clType.getName() +
                                  ", actual type " + actualType.getName() + " of formal parameter "
                                  + defined.getName() + " is incompatible with declared type " +
                                  defined.getType().getName());
               }
            }

            if (methodType.getReturnType().equals(TypeSymbol.SELF_TYPE.getName())) {
               return clType;
            } else {
               return (TypeSymbol)SymbolTable.globals.lookup(methodType.getReturnType());
            }
         }
      }
   }

   public TypeSymbol visit(ExplicitDispatch explicitDispatch) {
      this.isSelfType = false;
      if (explicitDispatch.atType != null && explicitDispatch.atType.getText().equals(TypeSymbol.SELF_TYPE.getName())) {
         SymbolTable.error(explicitDispatch.context, explicitDispatch.atType, "Type of static dispatch cannot be SELF_TYPE");
         return TypeSymbol.OBJECT;
      } else {
         for(Scope classType = this.currentScope; !(classType instanceof TypeSymbol); classType = classType.getParent()) {
         }

         TypeSymbol callerType = explicitDispatch.initial.accept(this);
         if (callerType != null) {
            callerType = (TypeSymbol)SymbolTable.globals.lookup(callerType.getName());
         }
         explicitDispatch.setCallerType(callerType);

         TypeSymbol dispatchType = (TypeSymbol)
                 (explicitDispatch.atType != null ?
                         SymbolTable.globals.lookup(explicitDispatch.atType.getText()) :
                         callerType);

         String funcName = explicitDispatch.name;
         if (dispatchType == null) {
            SymbolTable.error(explicitDispatch.context, explicitDispatch.atType, "Type " + explicitDispatch.atType.getText() + " of static dispatch is undefined");
            return TypeSymbol.OBJECT;
         } else if (explicitDispatch.atType != null && !this.isAncestor(callerType, dispatchType)) {
            ParserRuleContext var10000 = explicitDispatch.context;
            Token var10001 = explicitDispatch.atType;
            String var10002 = dispatchType.getName();
            SymbolTable.error(var10000, var10001, "Type " + var10002 + " of static dispatch is not a superclass of type " + callerType.getName());
            return TypeSymbol.OBJECT;
         } else {
            if (dispatchType.getName().equals(TypeSymbol.SELF_TYPE.getName())) {
               var dispatchTypeScope = currentScope;

               while (!(dispatchTypeScope instanceof TypeSymbol)) {
                  dispatchTypeScope = dispatchTypeScope.getParent();
               }

               dispatchType = (TypeSymbol) dispatchTypeScope;
            }

            FunctionSymbol methodType = dispatchType.lookupFunc(funcName);

            for(TypeSymbol parent = (TypeSymbol)SymbolTable.globals.lookup(dispatchType.getParentName());
                methodType == null && parent != null && parent.getParentName() != null;
                parent = (TypeSymbol)SymbolTable.globals.lookup(parent.getParentName())) {
               methodType = parent.lookupFunc(funcName);
            }

            if (methodType == null) {
               SymbolTable.error(explicitDispatch.context,
                       ((ExplicitDispatchContext)explicitDispatch.context).ID().getSymbol(),
                       "Undefined method " + funcName + " in class " + dispatchType.getName());
               return TypeSymbol.OBJECT;
            } else {
               ArrayList<IdSymbol> definedFormals = methodType.getFormals();
               List<Expression> actualFormals = explicitDispatch.args;
               if (definedFormals.size() != actualFormals.size()) {
                  SymbolTable.error(explicitDispatch.context,
                          ((ExplicitDispatchContext)explicitDispatch.context).ID().getSymbol(),
                          "Method " + funcName + " of class " + dispatchType.getName() + " is applied to wrong number of arguments");
                  return TypeSymbol.OBJECT;
               } else {
                  for(int i = 0; i < definedFormals.size(); ++i) {
                     IdSymbol defined = definedFormals.get(i);
                     Expression actual = actualFormals.get(i);
                     TypeSymbol actualType = actual.accept(this);
                     if (!this.isAncestor(actualType, defined.getType())) {
                        SymbolTable.error(explicitDispatch.context,
                                ((ExplicitDispatchContext)explicitDispatch.context).expr().get(i + 1).start,
                                "In call to method " + funcName + " of class " + dispatchType.getName()
                                        + ", actual type " + actualType.getName() + " of formal parameter " +
                                        defined.getName() + " is incompatible with declared type " + defined.getType().getName());
                     }
                  }

                  if (methodType.getReturnType().equals(TypeSymbol.SELF_TYPE.getName())) {
                     return callerType;
                  } else {
                     return (TypeSymbol)SymbolTable.globals.lookup(methodType.getReturnType());
                  }
               }
            }
         }
      }
   }

   public TypeSymbol visit(If iff) {
      this.isSelfType = false;
      TypeSymbol condType = (TypeSymbol)iff.cond.accept(this);
      if (!condType.getName().equals(TypeSymbol.BOOL.getName())) {
         SymbolTable.error(iff.context, ((IfContext)iff.context).cond.start, "If condition has type " + condType.getName() + " instead of Bool");
      }

      TypeSymbol typeThen = (TypeSymbol)iff.then.accept(this);
      TypeSymbol typeElse = (TypeSymbol)iff.elsee.accept(this);
      if (this.isAncestor(typeThen, typeElse)) {
         return typeElse;
      } else {
         return this.isAncestor(typeElse, typeThen) ? typeThen : TypeSymbol.OBJECT;
      }
   }

   public TypeSymbol visit(While whilee) {
      this.isSelfType = false;
      TypeSymbol condType = (TypeSymbol)whilee.cond.accept(this);
      if (!condType.getName().equals(TypeSymbol.BOOL.getName())) {
         SymbolTable.error(whilee.context, ((WhileContext)whilee.context).cond.start, "While condition has type " + condType.getName() + " instead of Bool");
      }

      return TypeSymbol.OBJECT;
   }

   public TypeSymbol visit(VarDef varDef) {
      this.isSelfType = false;
      IdSymbol varSymb = varDef.idSymbol;
      if (varSymb == null) {
         IdSymbol idSymbol;
         if (varDef.val != null) {
            idSymbol = new IdSymbol(varDef.name.getText(), varDef.val.symbol);
         } else {
            if (varDef.type.getText().equals(TypeSymbol.STRING.getName())) {
               idSymbol = new IdSymbol(varDef.name.getText(), "");
            } else if (varDef.type.getText().equals(TypeSymbol.BOOL.getName())) {
               idSymbol = new IdSymbol(varDef.name.getText(), "false");
            } else if (varDef.type.getText().equals(TypeSymbol.INT.getName())) {
               idSymbol = new IdSymbol(varDef.name.getText(), 0);
            } else {
               idSymbol = new IdSymbol(varDef.name.getText(), 0);
            }
         }
         idSymbol.setType((TypeSymbol) SymbolTable.globals.lookup(varDef.type.getText()));
         varDef.idSymbol = idSymbol;
      }
      varSymb = varDef.idSymbol;

      String typeStr = varDef.type.getText();
      TypeSymbol type = (TypeSymbol)SymbolTable.globals.lookup(typeStr);
      if (type == null) {
         ParserRuleContext var10000 = varDef.context;
         Token var10001 = varDef.type;
         String var10002 = varDef.name.getText();
         SymbolTable.error(var10000, var10001, "Let variable " + var10002 + " has undefined type " + typeStr);
         return null;
      } else {
         if (varDef.val != null) {
            TypeSymbol actualType = (TypeSymbol)varDef.val.accept(this);
            if (actualType != null && !this.isAncestor(actualType, type)) {
               SymbolTable.error(varDef.context, ((VarDefContext)varDef.context).val.start, "Type " + actualType.getName() + " of initialization expression of identifier " + varSymb + " is incompatible with declared type " + type.getName());
               return null;
            }
         }

         return type;
      }
   }

   public TypeSymbol visit(Let let) {
      this.isSelfType = false;
      LetSymbol letSymb = let.letSymbol;
      if (letSymb == null) {
         let.letSymbol = new LetSymbol(currentScope);
      }
      this.currentScope = let.letSymbol;

      for (VarDef varDef : let.varDefs) {
         varDef.accept(this);
         if (varDef.idSymbol != null) {
            ((LetSymbol) this.currentScope).add(varDef.idSymbol);
         }
      }

      TypeSymbol retType = let.body.accept(this);
      this.currentScope = this.currentScope.getParent();
      return retType;
   }

   public TypeSymbol visit(CaseOption caseOption) {
      this.isSelfType = false;
      IdSymbol symbol = caseOption.idSymbol;
      if (caseOption.idSymbol == null) {
         return null;
      }
      String type = caseOption.type.getText();
      if (SymbolTable.globals.lookup(type) == null) {
         ParserRuleContext var10000 = caseOption.context;
         Token var10001 = caseOption.type;
         SymbolTable.error(var10000, var10001, "Case variable " + caseOption.id.getText() +
                 " has undefined type " + var10001);
      }

      this.currentScope = new CaseBranch(symbol, this.currentScope);
      this.currentScope.add(symbol);
      caseOption.setCaseScope((CaseBranch) currentScope);
      TypeSymbol returnType = caseOption.body.accept(this);
      this.currentScope = this.currentScope.getParent();

      return returnType;
   }

   public TypeSymbol visit(Case casee) {
      this.isSelfType = false;
      TypeSymbol retType = null;
      Iterator var3 = casee.branches.iterator();

      while(true) {
         while(true) {
            TypeSymbol caseOptionRetType;
            do {
               do {
                  if (!var3.hasNext()) {
                     return retType;
                  }

                  CaseOption caseOption = (CaseOption)var3.next();
                  caseOptionRetType = (TypeSymbol)caseOption.accept(this);
               } while(caseOptionRetType == null);
            } while(retType != null && retType.equals(TypeSymbol.OBJECT));

            if (retType != null && !this.isAncestor(retType, caseOptionRetType)) {
               if (!this.isAncestor(caseOptionRetType, retType)) {
                  retType = TypeSymbol.OBJECT;
               }
            } else {
               retType = caseOptionRetType;
            }
         }
      }
   }

   public TypeSymbol visit(Block block) {
      this.isSelfType = false;
      TypeSymbol retType = null;
      Iterator var3 = block.expressions.iterator();

      while(var3.hasNext()) {
         Expression expr = (Expression)var3.next();
         TypeSymbol exprRetType = (TypeSymbol)expr.accept(this);
         if (exprRetType != null) {
            retType = exprRetType;
         }
      }

      return retType;
   }
}
