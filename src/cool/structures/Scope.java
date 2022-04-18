package cool.structures;

import cool.structures.symbols.FunctionSymbol;
import cool.structures.symbols.IdSymbol;

public interface Scope {
   boolean add(Symbol var1);

   Symbol lookup(String var1);

   Scope getParent();

   void addFuncToClass(String var1, FunctionSymbol var2);

   void updateSymbol(Symbol s);

   Symbol lookupNoParent(String name);

   String getName();
}
