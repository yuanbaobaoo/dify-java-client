dify-java-client
---

<p style="text-align: left">
    <a href="https://openjdk.org/projects/jdk/17" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/Java-17-blue" /> 
    </a>
    <a href="https://central.sonatype.com/artifact/io.github.yuanbaobaoo/dify-java-client" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/maven--central-0.15.3-green" /> 
    </a>
</p>

Dify Java 客户端

### 快速开始
- 环境需求  
```code
Java : >= 17
Maven: >= 3
```

- maven
```xml
<dependency>
    <groupId>io.github.yuanbaobaoo</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>0.15.x.1</version>
</dependency>
```

#### 创建客户端
```java
/**
 * 支持 build()、buildChat()、buildFlow() 方法，其对应返回类型也是不一致的
 */
IDifyClient client = DifyClientBuilder.create().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();
```

##### 1、IDifyClient: 基础Client
封装了部分公共API 与 鉴权逻辑，提供简单易用的调用方法
```java
// 调用预设API
String metaInfo = client.getAppMetaInfo();
// 调用自定义API
String result = client.requestJson(DifyRoute.buildGet("/messages"));
// 上传文件
DifyFileResult result = client.uploadFile(new File("pom.xml"), "abc-123");
```

##### 2、IDifyChatClient: 适用于 ChatBot、Agent、ChatFlow 类型应用
```IDifyChatClient``` 继承自 ```IDifyClient```，提供了会话相关的API：
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

##### 3、IDifyWorkChatClient: 适用于 WorkFlow 类型应用
```IDifyWorkChatClient``` 继承自 ```IDifyClient```，提供了工作流相关的API：
```java
IDifyWorkChatClient client = DifyClientBuilder.create()
        .apiKey("app-xxxx")
        .baseUrl("http://localhost:4000/v1")
        .buildWorkFlow();

// 创建消息
ParamMessage m = ParamMessage.builder().query("测试方法有哪些").user("abc-123").inputs(new HashMap<>() {{
    put("name", "元宝宝");
}}).build();

// 阻塞式运行工作流
DifyChatResult result = client.runBlocking(m);

// 流式运行工作流
CompletableFuture<Void> future = client.runStreaming(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

#### 创建外部知识库
当前项目并未对知识库API做实现，只声明的相关参数对象和接口   
```java
public interface IDifyKonwledgeService {
    /**
     * 知识检索
     * @param apiKey Dify传递的API KEY
     * @param args KownledgeArgs
     */
    KnowledgeResult retrieval(String apiKey, KnowledgeArgs args);

}
```
如果你有需求，可以基于声明的参数与接口进行实现即可，以下为参考代码“:
- 1、声明一个endpoint
```java
/**
 * 检索本地知识库内容
 * @param args KnowledgeArgs
 */
@PostMapping("/retrieval")
public KnowledgeResult retrieval(@RequestBody(required = false) KnowledgeArgs args, HttpServletRequest request) {
    return knowledgeService.retrieval(request.getHeader("Authorization"), args);
}
```
- 2、实现接口```io.github.yuanbaobaoo.dify.service.IKnowledgeService```
```java
class KnowledgeService implements IKnowledgeService {
    @Override
    public KnowledgeResult retrieval(String apiKey, KnowledgeArgs args) {
        // TODO 你的本地知识库检索逻辑
    }
}
```

### 支持的API
目前支持的API，可以参考上述三个接口文件。目前这个项目在持续更新中，如果接口不满足的，可以调用```requestJSON```、```requestMultipart```进行请求。
