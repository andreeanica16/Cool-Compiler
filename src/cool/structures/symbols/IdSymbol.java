package cool.structures.symbols;

import cool.structures.Symbol;

public class IdSymbol extends Symbol {
   protected TypeSymbol type;
   private Object value;
   private int offset = 0;

   public void setOffset(int offset) {
      this.offset = offset;
   }

   public int getOffset() {
      return this.offset;
   }

   public IdSymbol(String name) {
      super(name);
   }

   public IdSymbol(String name, Object value) {
      super(name);
      this.value = value;
   }

   public void setValue(Object val) {
      this.value = val;
   }

   public Object getValue() {
      return this.value;
   }

   public void setType(TypeSymbol type) {
      this.type = type;
   }

   public TypeSymbol getType() {
      return this.type;
   }
}
