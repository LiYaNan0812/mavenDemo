package com.lyn.stream;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        //1、Stream.of()方法创建
        Stream<String> stream1 = Stream.of("A","B","C");
        stream1.forEach(System.out::println);
        System.out.println("====================");
        //2、Arrays.stream()基于数组
        Stream<String> stream2 = Arrays.stream(new String[]{"A2","B2","C2"});
        stream2.forEach(System.out::println);
        System.out.println("====================");
        //3、基于集合
        Stream<String> stream3 = List.of("A3", "B3", "C3").stream();
        stream3.forEach(System.out::println);
        System.out.println("====================");
        //4、基于supplier
        Stream<Integer> stream4 = Stream.generate(new Supplier<Integer>() {
            int n = 0;

            @Override
            public Integer get() {
                return n++;
            }
        });
        stream4.limit(10).forEach(System.out::println);
        System.out.println("====================");
        //5、其他方式 如正则表达式的Patter对象的splitAsStream方法....
        Pattern pattern = Pattern.compile("\\s+");
        Stream<String> stream5 = pattern.splitAsStream("The quick brown fox jumps over the lazy dog");
        stream5.forEach(System.out::println
        );
        System.out.println("====================");
        //6、为避免拆箱装箱而创建的基本类型的流，IntStream、LongStream、DoubleStream
        IntStream intStream = List.of("1", "2", "3").stream().mapToInt(Integer::parseInt);
        intStream.forEach(System.out::println);


    }
}
