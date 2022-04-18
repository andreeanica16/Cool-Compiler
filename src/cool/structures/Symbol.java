package cool.structures;

import cool.structures.symbols.FunctionSymbol;

public class Symbol implements Scope {
   protected String name;

   public Symbol(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.getName();
   }

   public boolean add(Symbol sym) {
      return false;
   }

   public Symbol lookup(String str) {
      return null;
   }

   public Scope getParent() {
      return null;
   }

   public void addFuncToClass(String name, FunctionSymbol symbol) {
   }

   @Override
   public void updateSymbol(Symbol s) {

   }

   @Override
   public Symbol lookupNoParent(String name) {
      return null;
   }
}
