package com.lj.webflux;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @WxApplication
// @SpringBootApplication(scanBasePackageClasses = { Swagger2Configuration.class, UserController.class })
public class App_ {

    static interface Fun {
        default void a() {
			
//			InputStream;
			
//			InputStreamReader;
			
//			BufferedReader;
			
//			Objects;
			
		}

        void b();

    }

    static void println(int i) {
        System.err.println();
    }

    public static void main(String[] args) {
        // Fun fun = () -> {
        //
        // };

        // List<Integer> is = List.of(1, 2, 3, 5, 8, 6);
        // is = is.stream().filter(i -> i != 5).sorted((i1, i2) -> i2 - i1).collect(Collectors.toList());
        // is.forEach(System.err::println);
        //
        // Stream.generate(Math::random)
        //
        // .map(String::valueOf).map(s -> s.charAt(s.length()))
        //
        // .forEach(System.err::println);
        //
        // Stream.of("1", '2').forEach(System.out::println);
        // ;

        // List<String> sdata = new ArrayList<>();
        // for (int i = 0; i < 10; i++)
        // sdata.add(String.valueOf(Math.random()));

        Stream<String> stream = Stream.generate(Math::random).limit(10).map(String::valueOf);

        // Stream<Stream<Character>> map =
        // sdata.stream().map(App_::characterStream)
        // .forEach(System.err::println);
        // .forEach(s -> s.forEach(System.err::println));
        Stream<Character> flatMap = stream.flatMap(App_::characterStream);

        stream.flatMap(new Function<String, Stream<Character>>() {
            @Override
            public Stream<Character> apply(String t) {
                return App_.characterStream(t);
            }

        });
        flatMap.forEach(System.err::println);

    }

    static Stream<Character> characterStream(String s) {

        List<Character> lc = new ArrayList<>();
        for (char c : s.toCharArray())
            lc.add(c);

        return lc.stream();
    }
}
