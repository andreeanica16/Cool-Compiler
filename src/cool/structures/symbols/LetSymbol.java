package cool.structures.symbols;

import cool.structures.Scope;
import cool.structures.Symbol;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LetSymbol implements Scope {
   protected Map<String, IdSymbol> symbols = new LinkedHashMap();
   protected Scope parent;

   public LetSymbol(Scope parent) {
      this.parent = parent;
   }

   public void updateSymbol(Symbol sym) {
      this.symbols.put(sym.getName(), (IdSymbol) sym);
   }

   @Override
   public IdSymbol lookupNoParent(String name) {
      return symbols.get(name);
   }

   @Override
   public String getName() {
      return null;
   }

   public boolean add(IdSymbol sym) {
      if (this.symbols.containsKey(sym.getName())) {
         return false;
      } else {
         this.symbols.put(sym.getName(), sym);
         return true;
      }
   }

   public void setOffsets() {
      int offset = -4;

      for(Iterator var2 = this.symbols.entrySet().iterator(); var2.hasNext(); offset -= 4) {
         Entry<String, IdSymbol> localVar = (Entry)var2.next();
         IdSymbol newIdSymbol = (IdSymbol)localVar.getValue();
         newIdSymbol.setOffset(offset);
         this.symbols.put((String)localVar.getKey(), newIdSymbol);
      }

   }

   public int getOffsetOf(String s) {
      return ((IdSymbol)this.lookup(s)).getOffset();
   }

   public boolean add(Symbol sym) {
      return false;
   }

   public Symbol lookup(String s) {
      IdSymbol sym = (IdSymbol)this.symbols.get(s);
      if (sym != null) {
         return sym;
      } else {
         return this.parent != null ? this.parent.lookup(s) : null;
      }
   }

   public Scope getParent() {
      return this.parent;
   }

   public void addFuncToClass(String name, FunctionSymbol symbol) {
   }
}
