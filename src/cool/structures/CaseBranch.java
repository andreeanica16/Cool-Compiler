package cool.structures;

import cool.structures.symbols.FunctionSymbol;
import cool.structures.symbols.IdSymbol;

public class CaseBranch implements Scope {
   private IdSymbol symbol;
   private Scope parent;

   public CaseBranch(IdSymbol symbol, Scope parent) {
      this.symbol = symbol;
      this.parent = parent;
   }

   public boolean add(Symbol sym) {
      return false;
   }

   public Symbol lookup(String str) {
      return (Symbol)(this.symbol.getName().equals(str) ? this.symbol : this.parent.lookup(str));
   }

   public Scope getParent() {
      return this.parent;
   }

   public void addFuncToClass(String name, FunctionSymbol symbol) {
   }

   @Override
   public void updateSymbol(Symbol s) {

   }

   @Override
   public Symbol lookupNoParent(String name) {
      return symbol.getName().equals(name) ? symbol : null;
   }

   @Override
   public String getName() {
      return null;
   }
}
