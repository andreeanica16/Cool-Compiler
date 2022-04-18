package cool.structures;

import cool.structures.symbols.FunctionSymbol;
import cool.structures.symbols.TypeSymbol;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DefaultScope implements Scope {
   private HashMap<String, Symbol> symbols = new LinkedHashMap();
   private Scope parent;

   public DefaultScope(Scope parent) {
      this.parent = parent;
   }

   public ArrayList<Symbol> getSymbols() {
      return new ArrayList(this.symbols.values());
   }

   public boolean add(Symbol sym) {
      if (this.symbols.containsKey(sym.getName())) {
         return false;
      } else {
         this.symbols.put(sym.getName(), sym);
         return true;
      }
   }

   public void addFuncToClass(String className, FunctionSymbol symbol) {
      ((TypeSymbol)this.symbols.get(className)).addFunc(symbol);
   }

   @Override
   public void updateSymbol(Symbol s) {

   }

   @Override
   public Symbol lookupNoParent(String name) {
      return null;
   }

   @Override
   public String getName() {
      return null;
   }

   public Symbol lookup(String name) {
      Symbol sym = (Symbol)this.symbols.get(name);
      if (sym != null) {
         return sym;
      } else {
         return this.parent != null ? this.parent.lookup(name) : null;
      }
   }

   public Scope getParent() {
      return this.parent;
   }

   public String toString() {
      return this.symbols.values().toString();
   }
}
