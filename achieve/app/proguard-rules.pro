# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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


#ָ�������ѹ������
-optimizationpasses 5

#��������ϴ�Сд
-dontusemixedcaseclassnames

#��ȥ���Էǹ����Ŀ���
-dontskipnonpubliclibraryclasses

 #�Ż�  ���Ż���������ļ�
-dontoptimize

 #ԤУ��
-dontpreverify

 #����ʱ�Ƿ��¼��־
-verbose

 # ����ʱ�����õ��㷨
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#����ע��
-keepattributes *Annotation*

# ������Щ�಻������
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#���������v4�����������������
-keep public class * extends android.support.v4.app.Fragment


#���Ծ���
-ignorewarning

##��¼���ɵ���־����,gradle buildʱ�ڱ���Ŀ��Ŀ¼���##
#apk �������� class ���ڲ��ṹ
-dump proguard/class_files.txt
#δ��������ͳ�Ա
-printseeds proguard/seeds.txt
#�г��� apk ��ɾ���Ĵ���
-printusage proguard/unused.txt
#����ǰ���ӳ��
-printmapping proguard/mapping.txt
########��¼���ɵ���־���ݣ�gradle buildʱ �ڱ���Ŀ��Ŀ¼���-end######

#���������v4����v7��
-dontwarn android.support.**

####���������Լ���Ŀ�Ĳ��ִ����Լ����õĵ�����jar��library-end####



#���� native ������������
-keepclasseswithmembernames class * {
    native <methods>;
}

#�����Զ���ؼ��಻������
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#�����Զ���ؼ��಻������
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#���� Parcelable ��������
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#���� Serializable ��������
-keepnames class * implements java.io.Serializable

#���� Serializable ������������enum ��Ҳ��������
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#����ö�� enum �಻������
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#��������Դ��
-keepclassmembers class **.R$* {
    public static <fields>;
}

#����������� �������������ص�
#-keepattributes Signature

#�Ƴ�Log���ӡ�����ȼ���־�Ĵ��룬����ʽ����ʱ�������Ϊ��logʹ�ã����������Ϊ��ֹlog��ӡ�Ĺ���ʹ�ã������һ��ʵ�ַ�����ͨ��BuildConfig.DEBUG�ı���������
#-assumenosideeffects class android.util.Log {
#    public static *** v(...);
#    public static *** i(...);
#    public static *** d(...);
#    public static *** w(...);
#    public static *** e(...);
#}

#############################################################################################
########################                 ����ͨ��           ##################################
#############################################################################################

#######################     ���õ�����ģ��Ļ���ѡ��         ###################################
#gson
#������õ�Gson�������ģ�ֱ����������⼸�о��ܳɹ���������Ȼ�ᱨ��
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }
# ���ʹ����Gson֮��Ĺ���Ҫʹ����������JavaBean�༴ʵ���಻��������
-keep class com.matrix.app.entity.json.** { *; }
-keep class com.matrix.appsdk.network.model.** { *; }
