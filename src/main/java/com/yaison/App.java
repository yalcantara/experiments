package com.yaison;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    
    
    
    public static void main( String[] args )throws Exception
    {
        performTest();
        //bagOfWords();
    }
    
    private static void bagOfWords(){
        Generator g = new Generator();
        List<Contact> list = g.generate(1_000_000);
        System.out.println("Generated");
    
        WordDeComposer d = new WordDeComposer();
    
        BagOfWords b = new BagOfWords();
        for(Contact c:list){
            var arr = d.decompose(c.getFirstName(), c.getLastName(), c.getEmail(), c.getAddress());
            b.put(arr);
        }
    
        System.out.println("Bag of words created");
        b.print(500);
        System.out.println("Size: " + b.size());
    }
    
    private static void performTest(){
        var count = 100000;
        var g = new Generator();
    
        var list = g.generate(count);
    
        var searchTerm = "amp";
        PerformanceTask t1 = new Java8StreamPerformanceTask(list, searchTerm);
        PerformanceTask t2 = new DataSearchPerformanceTask(list, searchTerm);
    
        System.out.println("Java 8 Streams");
        performTest(t1);
    
        System.out.println("Data Search");
        performTest(t2);
    }
    
    private static void performTest(PerformanceTask task){
        
        for(int i =0; i < 3; i++){
            long start = System.nanoTime();
            task.init();
            for(int j = 0; j < 1; j++){
                task.execute();
            }
            long end = System.nanoTime();
            long took = TimeUnit.NANOSECONDS.toMillis(end - start);
            System.out.printf("Took: %,10d\n", took);
        }
        System.out.println();
    }
    
    private static void integratedTest()throws Exception{
        var count = 100000;
        var g = new Generator();
    
        var list = g.generate(count);
        //List<Contact> list = list1();
    
    
        for(int i =0; i < 10 && i < list.size(); i++){
            System.out.println(list.get(i));
        }
    
        var wd = new WordDeComposer(2);
        var d = new Decomposer<Contact>() {
            @Override
            public String[] decomposeObject(Contact value) {
                return wd.decompose(value.getFirstName());
            }
        
            @Override
            public String[] decomposeWord(String value) {
                return wd.decompose(value);
            }
        };
    
        var ds = new DataSearcher<Contact>(d);
        ds.compile(list);
    
    
        String word;
    
        System.out.println();
        System.out.print("Search: ");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            word = br.readLine();
        }
    
    
        System.out.println();
        var ans = ds.find(word);
        System.out.println();
        for(Contact c:ans){
            System.out.println(c);
        }
    }
    
    private static List<Contact> list1(){
        List<Contact> l = new ArrayList<>();
        l.add(new Contact("Linda"));
        l.add(new Contact("Zachary"));
        
        return l;
        
    }
    
    private static void test1(){
        for(int i =1; i <= 6; i++){
            int count = (int)Math.pow(10, i);
            Generator g = new Generator();
        
            var list = g.generate(count);
        
            var d = new WordDeComposer();
        
            var tree = new TreeSet<String>();
        
            for(Contact c:list){
                String fname = c.getFirstName();
                String lname = c.getLastName();
                String addr = c.getAddress();
                String email = c.getEmail();
            
                decompose(tree, d, fname);
                decompose(tree, d, lname);
                decompose(tree, d, addr);
                decompose(tree, d, email);
            }
        
            System.out.printf("%,10d: %,10d\n", count, tree.size());
        }
    }
    
    
    private static void decompose(Collection col, WordDeComposer d, String word){
    
        String[] words = d.decompose(word);
    
    
        add(col, words);
        
    }
    
    
    private static <T> void add(Collection<T> col, T[] arr){
        for(int i =0; i < arr.length; i++){
            T c = arr[i];
            col.add(c);
        }
    }
}
