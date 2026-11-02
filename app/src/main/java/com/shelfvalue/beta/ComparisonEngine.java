package com.shelfvalue.beta;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Exact cross multiplication decides the winner; rounding happens only for display. */
public final class ComparisonEngine {
    private ComparisonEngine() {}
    public enum Unit {
        G("mass", "1000", "kg"), KG("mass", "1", "kg"),
        ML("volume", "1000", "L"), L("volume", "1", "L"), ITEM("count", "1", "item");
        final String dimension, per;
        final BigDecimal displayMultiplier;
        Unit(String dimension, String multiplier, String per) {
            this.dimension=dimension; this.displayMultiplier=new BigDecimal(multiplier); this.per=per;
        }
    }
    public static final class Offer {
        final BigDecimal price, normalizedQuantity;
        final Unit unit;
        public Offer(String price, String quantity, Unit unit, String packs) {
            if(unit==null) throw new IllegalArgumentException("UNIT");
            this.price=parseDecimal(price, true);
            BigDecimal q=parseDecimal(quantity, false);
            String count=normalize(packs);
            if(!count.matches("[0-9]{1,6}") || new BigDecimal(count).signum()==0)
                throw new IllegalArgumentException("PACKS");
            this.unit=unit;
            // All quantities are expressed in the display unit: kg, L or item.
            this.normalizedQuantity=q.multiply(new BigDecimal(count)).divide(unit.displayMultiplier);
        }
    }
    public static final class Result {
        public final String winner,unitPriceA,unitPriceB,savingsPerUnit,savingsPercent,per;
        Result(String winner,String a,String b,String diff,String percent,String per) {
            this.winner=winner;this.unitPriceA=a;this.unitPriceB=b;
            this.savingsPerUnit=diff;this.savingsPercent=percent;this.per=per;
        }
    }
    private static String normalize(String raw) {
        if(raw==null) throw new IllegalArgumentException("INVALID");
        StringBuilder b=new StringBuilder();
        String s=raw.trim();
        for(int i=0;i<s.length();i++) {
            char ch=s.charAt(i);int digit=Character.digit(ch,10);
            if(digit>=0) b.append((char)('0'+digit));
            else if(ch=='.' || ch=='\u066B') b.append('.');
            else throw new IllegalArgumentException("INVALID");
        }
        return b.toString();
    }
    private static BigDecimal parseDecimal(String raw,boolean zeroAllowed) {
        String s=normalize(raw);
        if(!s.matches("[0-9]{1,12}(\\.[0-9]{1,12})?")) throw new IllegalArgumentException("INVALID");
        BigDecimal n=new BigDecimal(s);
        if(n.signum()<0 || (!zeroAllowed && n.signum()==0)) throw new IllegalArgumentException("INVALID");
        return n;
    }
    private static String ratio(BigDecimal n,BigDecimal d,int scale) {
        return n.divide(d,scale,RoundingMode.HALF_UP).toPlainString();
    }
    public static Result compare(Offer a,Offer b) {
        if(a==null || b==null || !a.unit.dimension.equals(b.unit.dimension))
            throw new IllegalArgumentException("INCOMPATIBLE");
        BigDecimal left=a.price.multiply(b.normalizedQuantity);
        BigDecimal right=b.price.multiply(a.normalizedQuantity);
        int order=left.compareTo(right);
        String ap=ratio(a.price,a.normalizedQuantity,2),bp=ratio(b.price,b.normalizedQuantity,2);
        if(order==0) return new Result("TIE",ap,bp,"0.00","0.0",a.unit.per);
        BigDecimal diff=left.subtract(right).abs();
        BigDecimal denominator=a.normalizedQuantity.multiply(b.normalizedQuantity);
        BigDecimal expensive=order>0?left:right;
        return new Result(order<0?"A":"B",ap,bp,ratio(diff,denominator,2),
                ratio(diff.multiply(BigDecimal.valueOf(100)),expensive,1),a.unit.per);
    }
}
