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
    public void onDestroy() {
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
compile 'com.cangwang.core:modulebus:2.0.0'
```

Maven:
```xml
<dependency>
  <groupId>com.cangwang.core</groupId>
  <artifactId>modulebus</artifactId>
  <version>2.0.0</version>
  <type>pom</type>
</dependency>
```

*Important<br/>
need to set base module depend with ModuleBus and ,other communicated modules depend with base module.


*2016.12.12 update <br/>
(1)Use ArrayMap to change with HashMap.<br>
(2)Add startModuleActivity function.<br>
(3)Fix bugs and develop speeds in ModuleBus.<br>

*2017.2.27 update <br/>
(1) Add Module architecture inclue activity,fragment and view.<br>
(2) Add Application util ModuleImpl.<br>

*2017.3.3 update moduleBus 2.0.0<br/>
(1) update Module architecture inclue activity,fragment and view.<br>
