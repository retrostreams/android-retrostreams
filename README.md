[![Maven Central](https://img.shields.io/maven-central/v/net.sourceforge.streamsupport/android-retrostreams.svg)](http://mvnrepository.com/artifact/net.sourceforge.streamsupport/android-retrostreams)
[![javadoc.io](https://javadoc.io/badge2/net.sourceforge.streamsupport/android-retrostreams/javadoc.svg)](https://javadoc.io/doc/net.sourceforge.streamsupport/android-retrostreams)

# android-retrostreams

![](art/streamsupport-sf.png)

android-retrostreams is a fork of the [streamsupport library](https://github.com/stefan-zobel/streamsupport)
targeted at Android developers who want to take advantage of Android Studio 3.x D8 / desugar toolchain's
capability to use interface default & static methods across Jar file boundaries.

Utilizing this feature of desugar, the streamsupport API can be even more aligned with the original
Java 8 / 9 API exposed in the java.util.function and java.util.stream packages. That allows for Android
app's code to be much more in accordance with the standard Java 8 / 9 usage than it is now possible with the
original streamsupport API (which is bound by the restrictions of supporting Java 6).

With respect to static / default interface methods the android-retrostreams public API should now be
identical to the Java 9 API wherever this is possible (i.e. for all interfaces that have first been
introduced in Java 8).

Supplemental helper classes, public static methods and so on that served as a replacement for the
default / static interfaces methods in the original streamsupport API are now mostly gone.
E.g., no `j8.u.s.RefStreams` class anymore - all these methods are now in the `j9.u.s.Stream` interface.

The retrostreams API lives in the packages `java9.util.*` and `java9.lang` respectively. So, it's not possible
to simply import the `java.util.stream` package in your code - you'd rather have to use `java9.util.stream` instead.
While that is fine as long as you have full control over your source code there is the other common scenario of using
a binary 3rd party dependency that has been compiled against the standard Java 8 `java.util.stream` API. In the latter
case bytecode rewriting via [ProGuard](https://github.com/Guardsquare/proguard) might be an option.  ProGuard supports
most Java 8 language features and the latest release can also replace the standard Java 8 stream API by the
[streamsupport](https://github.com/stefan-zobel/streamsupport) backport (cf. the Proguard [documentation](https://www.guardsquare.com/en/products/proguard/manual/gradleplugin), especially the section titled "Java 8 stream API support"),
i.e., in this case, switching to the older [streamsupport](https://github.com/stefan-zobel/streamsupport) backport instead
of using android-retrostreams might be the more promising approach.

The [online Javadoc](https://retrostreams.github.io/android-retrostreams/apidocs/index.html) gives a
picture of the API changes.

The current stable release of retrostreams is `android-retrostreams-1.7.3`.

Please give feedback [here](https://github.com/retrostreams/android-retrostreams/issues) if you experience
any problems.


### build.gradle:

```gradle
dependencies {
    implementation 'net.sourceforge.streamsupport:android-retrostreams:1.7.3'
}
```


### All-in-One JAR:
Contains android-retrostreams core + retroatomic + retroflow + retrofuture

```gradle
dependencies {
    implementation 'net.sourceforge.streamsupport:android-retrostreams_all:1.7.3.2'
}
```


### Example usage

```java
import java.util.List;
import java9.util.stream.Stream;
import java9.util.stream.StreamSupport;
import static java9.util.stream.Collectors.toList;

List<Integer> list = Stream.of(1, 2, 3, 4).collect(toList());

List<Integer> incremented = StreamSupport.stream(list)
        .map(i -> i + 1)
        .collect(toList());
```

### Sibling projects

You might also have a use for one of `retrostreams'` sibling projects:

* [android-retrofuture](https://github.com/retrostreams/android-retrofuture)
* [android-retroflow](https://github.com/retrostreams/android-retroflow)
* [android-retroatomic](https://github.com/retrostreams/android-retroatomic)


## LICENSE

GNU General Public License, version 2, [with the Classpath Exception](https://github.com/retrostreams/android-retrostreams/blob/master/GPL_ClasspathException)  (and [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/) for JSR-166 derived code)
