package cool.structures.symbols;

import cool.structures.Scope;
import cool.structures.Symbol;
import cool.structures.SymbolTable;

import java.util.*;
import java.util.Map.Entry;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public class TypeSymbol extends Symbol implements Scope {
   public static TypeSymbol OBJECT = new TypeSymbol("", "Object");
   public static TypeSymbol INT = new TypeSymbol("Object", "Int");
   public static TypeSymbol FLOAT = new TypeSymbol("", "Float");
   public static TypeSymbol BOOL = new TypeSymbol("Object", "Bool");
   public static TypeSymbol STRING = new TypeSymbol("Object", "String");
   public static TypeSymbol IO = new TypeSymbol("Object", "IO");
   public static TypeSymbol SELF_TYPE = new TypeSymbol("", "SELF_TYPE");
   private Map<String, Symbol> symbols = new LinkedHashMap();
   // local methods to a class
   private Map<String, FunctionSymbol> methods = new LinkedHashMap();
   // all methods, including those inherited
   private Map<String, FunctionSymbol> allMethods = new LinkedHashMap();
   private TypeSymbol parent;
   private String parentName = "";
   private int symbolsSize = 0;
   private int methodsSize = 0;
   public LinkedHashSet<TypeSymbol> children = new LinkedHashSet<>();
   private static int currentTag = 0;
   private int tag = 0;
   private int maxTagInSubtree = 0;

   public int getMaxTagInSubtree() {
      return maxTagInSubtree;
   }
   public TypeSymbol(String parentName, String name) {
      super(name);
      this.parentName = parentName;
   }

   public void setParent(TypeSymbol parent) {
      this.parent = parent;
      this.parentName = parent.name;
      if (this != SELF_TYPE) {
         parent.children.add(this);
         this.methodsSize = parent.methodsSize;
         this.symbols.putAll(parent.getSymbolsMap());
      }

   }

   public int createTags() {
      this.tag = currentTag;
      this.maxTagInSubtree = this.tag;
      Iterator var1 = this.children.iterator();

      while(var1.hasNext()) {
         TypeSymbol child = (TypeSymbol)var1.next();
         if (child != SELF_TYPE) {
            ++currentTag;
            this.maxTagInSubtree = Math.max(this.tag, child.createTags());
         }
      }

      return this.maxTagInSubtree;
   }

   public int getMethodsSize() {
      return this.methodsSize;
   }

   public Map<String, Symbol> getSymbolsMap() {
      return this.symbols;
   }

   public String getClassMembersStr() {
      if (this == IO) {
         return "";
      } else if (this != INT && this != BOOL) {
         if (this == STRING) {
            return "\t.word\tint_const0\n\t.asciiz\t\"\"\n\t.align\t2";
         } else {
            String currentClassMembers = "";
            ArrayList<Symbol> reversedValues = new ArrayList(this.symbols.values());
            Collections.reverse(reversedValues);
            Iterator var3 = reversedValues.iterator();

            while(var3.hasNext()) {
               Symbol symbol = (Symbol)var3.next();
               if (!symbol.getName().equals("self")) {
                  String symbolType = ((IdSymbol)symbol).type.name;
                  if (symbolType.equals(BOOL.getName())) {
                     currentClassMembers = currentClassMembers + "\t.word\tbool_const0\n";
                  } else if (symbolType.equals(STRING.getName())) {
                     currentClassMembers = currentClassMembers + "\t.word\tstr_const0\n";
                  } else if (symbolType.equals(INT.getName())) {
                     currentClassMembers = currentClassMembers + "\t.word\tint_const0\n";
                  } else {
                     currentClassMembers = currentClassMembers + "\t.word\t0\n";
                  }
               }
            }

            return currentClassMembers;
         }
      } else {
         return "\t.word\t0";
      }
   }

   public ST createProtObj(STGroupFile templates) {
      int words = 3;

      for(TypeSymbol currentNode = this; currentNode != null; currentNode = currentNode.parent) {
         words += currentNode.symbolsSize;
      }

      if (this == BOOL || this == STRING || this == INT) {
         ++words;
      }

      if (this == STRING) {
         ++words;
      }

      return templates.getInstanceOf("classProtObj")
              .add("tag", this.tag)
              .add("class_name", this.name)
              .add("words", words)
              .add("classMembers", this.getClassMembersStr());
   }

   public ST createInitMethod(STGroupFile templates) {
      return this == OBJECT ?
              templates.getInstanceOf("initObject") :
              templates.getInstanceOf("initClass")
                      .add("class_name", this.name)
                      .add("parent_name", this.parentName);
   }

   public ST createDispatchTableEntry(STGroupFile templates, String methodName) {
      return templates.getInstanceOf("dispTabEntry").add("method", methodName);
   }

   private ArrayList<String> getMethodsFromAncestors() {
      ArrayList<String> allMethods = new ArrayList();
      HashSet<String> allMethodsWithoutClass = new HashSet();

      TypeSymbol nextParent = this;
      boolean hasObjectParent = false;

      while(nextParent != null) {
         if (nextParent.getName().equals("Object")) {
            hasObjectParent = true;
         }

         ArrayList<String> newToAdd = new ArrayList();

         for (var method : nextParent.methods.values()) {
            String methodName = method.getName();
            if (!allMethodsWithoutClass.contains(methodName)) {
               newToAdd.add(nextParent.name + "." + methodName);
               allMethodsWithoutClass.add(methodName);
            }
         }

         Collections.reverse(newToAdd);
         allMethods.addAll(newToAdd);

         nextParent = (TypeSymbol)nextParent.getParent();
         if (nextParent == null && !hasObjectParent) {
            hasObjectParent = true;
            nextParent = OBJECT;
         }
      }

      Collections.reverse(allMethods);
      return allMethods;
   }

   public void setMethodsOffsets() {
      Iterator var3;
      Entry entry;
      for(TypeSymbol current = this; current != null; current = current.parent) {
         ArrayList<Entry<String, FunctionSymbol>> reversedEntries = new ArrayList(current.getMethods().entrySet());
         Collections.reverse(reversedEntries);
         var3 = reversedEntries.iterator();

         while(var3.hasNext()) {
            entry = (Entry)var3.next();
            this.allMethods.put((String)entry.getKey(), (FunctionSymbol)entry.getValue());
         }
      }

      int offset = this.allMethods.size();

      for (var method : allMethods.entrySet()) {
         // deep copy of object, because we want to modify the offset
         FunctionSymbol newValue = new FunctionSymbol(this, method.getKey(), method.getValue().getReturnType());
         --offset;
         newValue.setOffset(offset);
         allMethods.put(method.getKey(), newValue);
      }
   }

   public ST createDispatchTable(STGroupFile templates) {
      ST dispatchTableMethods = templates.getInstanceOf("sequence");

      for (String method : this.getMethodsFromAncestors()) {
         dispatchTableMethods.add("e", this.createDispatchTableEntry(templates, method));
      }

      return templates.getInstanceOf("classDispTab")
              .add("class_name", this.name).add("methods", dispatchTableMethods);
   }

   public int getTag() {
      return this.tag;
   }

   public int getIndexOf(String s) {
      int i = 3;
      ArrayList<String> reversedKeys = new ArrayList(this.symbols.keySet());
      Collections.reverse(reversedKeys);

      for(Iterator var4 = reversedKeys.iterator(); var4.hasNext(); ++i) {
         String candidate = (String)var4.next();
         if (candidate.equals(s)) {
            break;
         }
      }

      return i;
   }

   public ArrayList<Symbol> getSymbols() {
      return new ArrayList(this.symbols.values());
   }

   public Map<String, FunctionSymbol> getMethods() {
      return this.methods;
   }

   public int getSymbolsSize() {
      return this.symbolsSize;
   }

   public void updateSymbol(Symbol sym) {
      symbols.put(sym.getName(), sym);
   }

   @Override
   public Symbol lookupNoParent(String name) {
      return null;
   }

   public boolean add(Symbol sym) {
      if (sym instanceof IdSymbol) {
         TypeSymbol type = ((IdSymbol)sym).getType();
         String typeName = type.getName();
         if (!typeName.equals(INT.getName()) && !typeName.equals(BOOL.getName()) && !typeName.equals(SELF_TYPE.getName())) {
            if (typeName.equals(STRING.getName())) {
               Object idSymbolValue = ((IdSymbol)sym).getValue();
               if (idSymbolValue != null) {
                  this.symbolsSize += (((String)idSymbolValue).length() + 1) / 4;
               } else {
                  ++this.symbolsSize;
               }
            } else {
               this.symbolsSize += type.getSymbolsSize();
            }
         } else {
            ++this.symbolsSize;
         }
      }

      if (this.symbols.containsKey(sym.getName())) {
         return false;
      } else {
         this.symbols.put(sym.getName(), sym);
         return true;
      }
   }

   public boolean addFunc(FunctionSymbol sym) {
      if (this.methods.containsKey(sym.getName())) {
         return false;
      } else {
         sym.setOffset(methodsSize);
         this.methods.put(sym.getName(), sym);
         return true;
      }
   }

   public Symbol lookup(String name) {
      Symbol sym = this.symbols.get(name);
      if (sym != null) {
         return sym;
      } else {
         return this.parent != null ? this.parent.lookup(name) : null;
      }
   }

   public TypeSymbol lookupFuncDefined(String name) {
      FunctionSymbol sym = this.methods.get(name);
      if (sym != null) {
         return this;
      } else {
         return !this.parentName.equals("") ?
                 ((TypeSymbol)SymbolTable.globals.lookup(this.parentName)).lookupFuncDefined(name) : null;
      }
   }

   public FunctionSymbol lookupAllFunc(String name) {
      FunctionSymbol sym = this.allMethods.get(name);

      if (sym != null) {
         return sym;
      } else {
         return !this.parentName.equals("") ?
                 ((TypeSymbol)SymbolTable.globals.lookup(this.parentName)).lookupAllFunc(name) : null;
      }
   }

   public FunctionSymbol lookupFunc(String name) {
      FunctionSymbol sym = this.methods.get(name);
      if (sym != null) {
         return sym;
      } else {
         return !this.parentName.equals("") ? ((TypeSymbol)SymbolTable.globals.lookup(this.parentName)).lookupFunc(name) : null;
      }
   }

   public String getParentName() {
      return this.parentName;
   }

   public Scope getParent() {
      return this.parent;
   }

   public String toString() {
      return this.symbols.values().toString();
   }
}
