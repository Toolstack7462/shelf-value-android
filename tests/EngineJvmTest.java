package com.shelfvalue.beta;

public class EngineJvmTest {
 static int checks=0;
 static void eq(Object a,Object b){checks++;if(!a.equals(b))throw new AssertionError("expected "+a+" but got "+b);}
 static void invalid(Runnable action){checks++;try{action.run();throw new AssertionError("expected validation error");}catch(IllegalArgumentException expected){}}
 static ComparisonEngine.Offer o(String p,String q,ComparisonEngine.Unit u,String n){return new ComparisonEngine.Offer(p,q,u,n);}
 public static void main(String[] x){
  ComparisonEngine.Result r=ComparisonEngine.compare(o("120","500",ComparisonEngine.Unit.G,"1"),o("150","500",ComparisonEngine.Unit.G,"1"));
  eq("A",r.winner);eq("240.00",r.unitPriceA);eq("300.00",r.unitPriceB);eq("60.00",r.savingsPerUnit);eq("20.0",r.savingsPercent);
  r=ComparisonEngine.compare(o("320","500",ComparisonEngine.Unit.G,"2"),o("500","1",ComparisonEngine.Unit.KG,"1"));
  eq("A",r.winner);eq("180.00",r.savingsPerUnit);eq("36.0",r.savingsPercent);
  eq("TIE",ComparisonEngine.compare(o("180","750",ComparisonEngine.Unit.ML,"1"),o("240","1",ComparisonEngine.Unit.L,"1")).winner);
  eq("TIE",ComparisonEngine.compare(o("0.30","3",ComparisonEngine.Unit.ITEM,"1"),o("0.10","1",ComparisonEngine.Unit.ITEM,"1")).winner);
  eq("100.0",ComparisonEngine.compare(o("0","1",ComparisonEngine.Unit.ITEM,"1"),o("2","1",ComparisonEngine.Unit.ITEM,"1")).savingsPercent);
  eq("B",ComparisonEngine.compare(o("999999999999","999999",ComparisonEngine.Unit.ITEM,"1"),o("999999999998","999999",ComparisonEngine.Unit.ITEM,"1")).winner);
  eq("A",ComparisonEngine.compare(o("1","3",ComparisonEngine.Unit.ITEM,"1"),o("0.333333333334","1",ComparisonEngine.Unit.ITEM,"1")).winner);
  eq("TIE",ComparisonEngine.compare(o("۱۲۰","۵۰۰",ComparisonEngine.Unit.G,"۱"),o("120","0٫5",ComparisonEngine.Unit.KG,"1")).winner);
  invalid(()->o("1","0",ComparisonEngine.Unit.G,"1"));invalid(()->o("1","-1",ComparisonEngine.Unit.G,"1"));invalid(()->o("-1","1",ComparisonEngine.Unit.G,"1"));invalid(()->o("abc","1",ComparisonEngine.Unit.G,"1"));invalid(()->o("1","1",ComparisonEngine.Unit.G,"0"));invalid(()->o("1","1",ComparisonEngine.Unit.G,"1.5"));invalid(()->o("1234567890123","1",ComparisonEngine.Unit.G,"1"));
  invalid(()->ComparisonEngine.compare(o("1","1",ComparisonEngine.Unit.G,"1"),o("1","1",ComparisonEngine.Unit.L,"1")));
  r=ComparisonEngine.compare(o("1","3",ComparisonEngine.Unit.ITEM,"1"),o("1","4",ComparisonEngine.Unit.ITEM,"1"));eq("B",r.winner);eq("0.08",r.savingsPerUnit);eq("25.0",r.savingsPercent);
  System.out.println("PASS: "+checks+" calculation checks");
 }
}
