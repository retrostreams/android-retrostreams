# Add any project specific keep options here:

#-dontobfuscate
#-dontshrink

-keep class java9.** { *; }
-keep interface java9.** { *; }
-keep enum  java9.** { *; }

-dontwarn java9.**
-dontwarn org.openjdk.**

-dontnote java.util.Arrays*
-dontnote java.util.HashMap*
-dontnote java.util.concurrent.LinkedBlockingQueue*
-dontnote java.util.concurrent.LinkedBlockingDeque*

