# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

## Moshi
## https://github.com/square/moshi/blob/master/moshi/src/main/resources/META-INF/proguard/moshi.pro
#
## JSR 305 annotations are for embedding nullability information.
#-dontwarn javax.annotation.**
#
#-keepclasseswithmembers class * {
#    @com.squareup.moshi.* <methods>;
#}
#
#-keep @com.squareup.moshi.JsonQualifier @interface *
#
## Enum field names are used by the integrated EnumJsonAdapter.
## values() is synthesized by the Kotlin compiler and is used by EnumJsonAdapter indirectly
## Annotate enums with @JsonClass(generateAdapter = false) to use them with Moshi.
#-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
#    <fields>;
#    **[] values();
#}
#
## Keep helper method to avoid R8 optimisation that would keep all Kotlin Metadata when unwanted
#-keepclassmembers class com.squareup.moshi.internal.Util {
#    private static java.lang.String getKotlinMetadataClassName();
#}
#
## Keep ToJson/FromJson-annotated methods
#-keepclassmembers class * {
#  @com.squareup.moshi.FromJson <methods>;
#  @com.squareup.moshi.ToJson <methods>;
#}
#
#
## Moshi
## https://stackoverflow.com/q/57034937/9636037
#
## The name of @JsonClass types is used to look up the generated adapter.
#-keepnames @com.squareup.moshi.JsonClass class *
#
## Retain generated JsonAdapters if annotated type is retained.
#-if @com.squareup.moshi.JsonClass class *
#-keep class <1>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*
#-keep class <1>_<2>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*
#-keep class <1>_<2>_<3>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*
#-keep class <1>_<2>_<3>_<4>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*$*
#-keep class <1>_<2>_<3>_<4>_<5>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*$*$*
#-keep class <1>_<2>_<3>_<4>_<5>_<6>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl
#
#-keepclassmembers class kotlin.Metadata {
#    public <methods>;
#}
