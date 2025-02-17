dify-java-client
---

[![Java version](https://img.shields.io/badge/Java-21-blue)]()
<a href="https://central.sonatype.com/artifact/io.github.yuanbaobaoo/dify-java-client" target="_blank">
    [![maven-central](https://img.shields.io/badge/maven--central-0.0.1-green)]()
</a>

Dify Java 客户端，适用于Dify V1 系列API

### 快速开始
- maven 
```xml
<dependency>
    <groupId>io.github.yuanbaobaoo</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

#### 创建客户端
```java
IDifyClient client = DifyClientBuilder.create()
        .apiKey("app-xxxx")
        .baseUrl("http://localhost:4000/v1")
        .build();

// 调用公共API
String metaInfo = client.getAppMetaInfo();
// 调用自定义API
String result = client.requestJson("/messages", HttpMethod.GET, null, null);
```
除了IDifyClient外，还提供了DifyChatClient，DifyChatClient继承自IDifyClient，提供了会话相关的API：
```java
IDifyChatClient client = DifyClientBuilder.create()
        .apiKey("app-xxxx")
        .baseUrl("http://localhost:4000/v1")
        .buildChat();

// 创建消息
ParamMessage m = ParamMessage.builder().query("你是谁").user("abc-123").inputs(new HashMap<>() {{
    put("test", "value");
}}).build();

// 发送阻塞消息
DifyChatResult result = client.sendMessages(m);

// 发送流式消息
CompletableFuture<Void> future = client.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

### 支持的API
目前支持的API，可以参考 ```IDifyClient```、```IDifyChatClient```这两个接口，如果接口不满足的，可以调用requestJSON、requestMultipart进行调用

```java
// requestJson内处理了数据结构、鉴权的一些逻辑
String result = client.requestJson("/messages", HttpMethod.GET, null, null);
// requestMultipart内处理了文件上传所需要的一些逻辑
// 如果是单纯的文件上传，可以直接使用
DifyFileResult result = client.uploadFile( new File("pom.xml"), "abc-123");
```