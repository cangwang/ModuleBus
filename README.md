# ModuleBus_Ex
ModuleBus_Ex is use for diffent modules display in one Activity or Fragment.<br>
One function one module ,to make module independence.<br>

like this


Gradle:
```gradle
compile 'com.cangwang.core:modulebus:3.0.0'
```

Maven:
```xml
<dependency>
  <groupId>com.cangwang.core</groupId>
  <artifactId>modulebus</artifactId>
  <version>3.0.0</version>
  <type>pom</type>
</dependency>
```

*Important<br/>
1.need to set base module depend with ModuleBus and ,other communicated modules depend with base module.<br/>
2.need to import rxjava2 and rxandroid,to make loading high efficiency.<br/>

```gradle
compile 'io.reactivex.rxjava2:rxjava:2.+'
compile 'io.reactivex.rxjava2:rxandroid:+'
```


*2017.6.15 update <br/>
make modules can layout free in sreen.<br/>
make modules load high efficiency with rxjava and rxandroid.<br/>
