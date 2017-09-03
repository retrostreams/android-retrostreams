# android-retrostreams

![](art/streamsupport-sf.png)

android-retrostreams is a fork of the [streamsupport library](https://sourceforge.net/projects/streamsupport/)
targeted at Android developers who want to take advantage of Android Studio 3.0 desugar toolchain's
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

The [online Javadoc](https://retrostreams.github.io/android-retrostreams/apidocs/index.html) gives a
picture of the API changes.

As the desugar toolchain in Android Studio 3 beta 4 appears pretty mature now, an official stable release
of `android-retrostreams` may not be far away.

Please give feedback [here](https://github.com/retrostreams/android-retrostreams/issues) if you experience
any problems.


## LICENSE

GNU General Public License, version 2, with the Classpath Exception
