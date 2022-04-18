package cool.structures.symbols;

import cool.structures.Scope;
import cool.structures.Symbol;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionSymbol extends IdSymbol implements Scope {
   protected Map<String, IdSymbol> symbols = new LinkedHashMap();
   protected TypeSymbol parent;
   private String returnType;
   private int offset;

   public Map<String, IdSymbol> getSymbols() {
      return symbols;
   }

   public IdSymbol lookupNoParent(String name) {
      return symbols.get(name);
   }

   public void setOffset(int offset) {
      this.offset = offset;
   }

   public int getOffset() {
      return this.offset;
   }

   public FunctionSymbol(TypeSymbol parent, String name, String returnType) {
      super(name);
      this.parent = parent;
      this.returnType = returnType;
      this.offset = parent.getMethodsSize();
   }

   public void updateSymbol(Symbol sym) {
      this.symbols.put(sym.getName(), (IdSymbol) sym);
   }

   public String getReturnType() {
      return this.returnType;
   }

   public boolean add(IdSymbol sym) {
      if (this.symbols.containsKey(sym.getName())) {
         return false;
      } else {
         this.symbols.put(sym.getName(), sym);
         return true;
      }
   }

   public Symbol lookup(String s) {
      IdSymbol sym = this.symbols.get(s);
      if (sym != null) {
         return sym;
      } else {
         return this.parent != null ? this.parent.lookup(s) : null;
      }
   }

   public int getIndexOf(String s) {
      int i = 0;

      for(Iterator var3 = this.symbols.keySet().iterator(); var3.hasNext(); ++i) {
         String candidate = (String)var3.next();
         if (candidate.equals(s)) {
            break;
         }
      }

      return i * 4;
   }

   public Scope getParent() {
      return this.parent;
   }

   public ArrayList<IdSymbol> getFormals() {
      return new ArrayList(this.symbols.values());
   }
}
