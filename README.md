# ModuleBus
Module Bus is use for diffent modules communication.<br>

ModuleBus in 3 steps
-------------------
1. Define events:

    ```java  
    public static class MessageClient extend IBaseClient { /* Additional fields if needed */ }
    ```

2. Prepare ModuleEvent and function:
    Declare and annotate your subscribing method.<br/>  

    ```java
    @ModuleEvent(coreClientClass = MessageClient.Class)  
    public void fun(Object...args) {/* Do something */};
    ```
    Register and unregister your ModuleBus. For example on Android, activities and fragments should usually register according to their life cycle:

   ```java
    @Override
    public void onStart() {
        super.onStart();
        ModuleBus.getInstance().register(this);
    }
 
    @Override
    public void onStop() {
        super.onStop();
        ModuleBus.getInstance().unregister(this);
    }
    ```

3. Post function:

   ```java
    ModuleBus.getInstance().post(MessageClient.class,"fun",Object...args);
    ```



Add ModuleBus to your project
----------------------------

Gradle:
```gradle
compile 'com.cangwang.core:modulebus:1.0'
```

Maven:
```xml
<dependency>
  <groupId>com.cangwang.core</groupId>
  <artifactId>modulebus</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
```

*Important<br/>
need to set base module depend with ModuleBus and ,other communicated modules depend with base module.
