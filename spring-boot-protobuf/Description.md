* ​	生成```java``` 文件的命令：

  ```tex
  protoc.exe --java_out=D:\work\demo\src\main\java --proto_path=D:\work\demo\src\main\java\com\example\demo\proto LoginBuilder.proto
  
  参数说明
  java_out:java文件夹，java root文件夹，生成的文件会放在编写的java_package包下。
  proto_path:proto文件夹，放proto文件的文件夹。
  protoc指令安装：https://github.com/protocolbuffers/protobuf
  ```

* ```maven```依赖

  ```xml
          <!-- https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java -->
          <dependency>
              <groupId>com.google.protobuf</groupId>
              <artifactId>protobuf-java</artifactId>
              <version>3.13.0</version>
          </dependency>
          <!-- https://mvnrepository.com/artifact/com.googlecode.protobuf-java-format/protobuf-java-format -->
          <dependency>
              <groupId>com.googlecode.protobuf-java-format</groupId>
              <artifactId>protobuf-java-format</artifactId>
              <version>1.4</version>
          </dependency>
  ```

  * 配置类：```ProtobufHttpMessageConverter```序列化，```RestTemplate``` 反序列化

    ```java
    @Configuration
    public class ProtobufConfig {
        /**
         * protobuf 序列化
         */
        @Bean
        ProtobufHttpMessageConverter protobufHttpMessageConverter() {
            return new ProtobufHttpMessageConverter();
        }
    
        /**
         * protobuf 反序列化
         */
        @Bean
        RestTemplate restTemplate(ProtobufHttpMessageConverter protobufHttpMessageConverter) {
            return new RestTemplate(Collections.singletonList(protobufHttpMessageConverter));
        }
    }
    ```

    

  * ```Controller``` 层写接口接收```LoginBuilder.LoginRequest``` 对象，必须使用```@RequestBody``` 注解。

  * ```PostMan``` 软件不方便测试此类接口，在```test``` 类中直接URl请求的方式测试更方便。

