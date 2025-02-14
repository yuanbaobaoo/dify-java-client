dify-java-client
---

[![Java version](https://img.shields.io/badge/Java-21-blue)]()
[![release version](https://img.shields.io/badge/latest-0.0.1-green)]()


Dify Java 客户端，适用于Dify V1 系列API

### 快速开始
- maven  (未发布到Maven central，请自行下载执行 mvn install )
```xml
<dependency>
    <groupId>github.yuanbaobaoo</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>${latest}</version>
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