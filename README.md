dify-java-client
---

<p style="text-align: left">
    <a href="https://openjdk.org/projects/jdk/17" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/Java-17-blue" /> 
    </a>
    <a href="https://central.sonatype.com/artifact/io.github.yuanbaobaoo/dify-java-client" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/maven--central-1.2.4-green" /> 
    </a>
</p>

Dify Java 客户端

中文 | [English](./README_EN.md)

### 快速开始
- 环境需求  
```code
Java : >= 17
Maven: >= 3
Dify Api: <= 1.x
```

- maven
```xml
<dependency>
    <groupId>io.github.yuanbaobaoo</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>1.2.4</version>
</dependency>
```

### 创建客户端
```java
/**
 * 支持 create()、chat()、flow()、completion()，其对应返回类型也是不一致的
 */
IDifyBaseClient client = DifyClientBuilder.create().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();
```

#### 1、IDifyBaseClient: 基础Client
封装了部分公共API 与 鉴权逻辑，提供简单易用的调用方法
```java
// 调用预设API
String metaInfo = client.getAppMetaInfo();
// 调用自定义API
String result = client.requestJson(DifyRoute.buildGet("/messages"));
// 上传文件
DifyFileResult result = client.uploadFile(new File("pom.xml"), "abc-123");
```

#### 2、IDifyChatClient: 适用于 ChatBot、Agent、ChatFlow 类型应用
```IDifyChatClient``` 继承自 ```IDifyBaseClient```，提供了会话相关的API：
```java
IDifyChatClient chatClient = DifyClientBuilder.chat().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();

// 创建消息
ParamMessage m = ParamMessage.builder().query("你是谁").user("abc-123").inputs(new HashMap<>() {{
    put("test", "value");
    put("file1", ParamFile.builder()
            .type(ParamFile.FileType.audio)
            .transferMethod(ParamFile.TransferMethod.remote_url)
            .build()
    );
}}).build();

// 发送阻塞消息
DifyChatResult result = chatClient.sendMessages(m);

// 发送流式消息
CompletableFuture<Void> future = client.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

#### 3、IDifyFlowClient: 适用于 WorkFlow 类型应用
```IDifyFlowClient``` 继承自 ```IDifyBaseClient```，提供了工作流相关的API：
```java
IDifyFlowClient flowClient = DifyClientBuilder.flow().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();

// 创建消息
ParamMessage m = ParamMessage.builder().user("abc-123").inputs(new HashMap<>() {{
    put("name", "元宝宝");
    put("text", "Java为什么叫Java？");
}}).build();

// 阻塞式运行工作流
DifyChatResult result = flowClient.runBlocking(m);

// 流式运行工作流
CompletableFuture<Void> future = client.runStreaming(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

#### 4、IDifyCompletion: 适用于 Completion 类型应用
```IDifyCompletion``` 继承自 ```IDifyBaseClient```，提供了文本生成相关的API：
```java
IDifyCompletion completion = DifyClientBuilder.completion().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();

// 创建消息
ParamMessage m = ParamMessage.builder().query("Java为什么叫Java").user("abc-123").build();

// 阻塞式运行
DifyChatResult result = completion.sendMessages(m);

// 流式运行
CompletableFuture<Void> future = completion.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

### 创建外部知识库
当前项目并未对知识库API做实现，只声明了相关参数对象和接口   
```java
public interface IDifyKnowledgeService {
    /**
     * 知识检索
     * @param apiKey Dify传递的API KEY
     * @param args KnowledgeArgs
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
- 2、实现接口```io.github.yuanbaobaoo.dify.service.IDifyKnowledgeService```
```java
class KnowledgeService implements IDifyKnowledgeService {
    @Override
    public KnowledgeResult retrieval(String apiKey, KnowledgeArgs args) {
        // TODO 你的本地知识库检索逻辑
    }
}
```

### 异常处理
默认情况下，当正常的请求返回了 http status >= 400 时，都会抛出一个异常对象 ```DifyException```。
该对象接收了Dify返回的 ```status```、```code```、```message```、```params``` 这几个属性。
当然你也可以通过 ```getOriginal()``` 方法获取原始返回内容。

### 支持的API
目前支持的API，可以参考上述三个接口文件。目前这个项目在持续更新中，如果接口不满足的，可以调用```requestJSON```、```requestMultipart```进行请求。
>声明一个DifyRoute对象: ```DifyRoute route = new DifyRoute("/info", HttpMethod.GET);```

| 内置API                              | Dify Api                             | Method | 描述              |
|------------------------------------|--------------------------------------|--------|-----------------|
| IDifyBaseClient.getAppInfo         | /info                                | GET    | 获取应用基本信息        |
| IDifyBaseClient.getAppParameters   | /parameters                          | GET    | 获取应用参数          |
| IDifyBaseClient.getAppMetaInfo     | /meta                                | GET    | 获取应用Meta信息      |
| IDifyBaseClient.uploadFile         | /files/upload                        | POST   | 上传文件            |
| IDifyBaseClient.feedbacks          | /messages/:message_id/feedbacks      | POST   | 消息反馈            |
| IDifyBaseClient.audioToText        | /audio-to-text                       | POST   | 语音转文字           |
| IDifyChatClient.sendMessages       | /chat-messages                       | POST   | 发送对话消息（阻塞）      |
| IDifyChatClient.sendMessagesAsync  | /chat-messages                       | POST   | 发送对话消息（流式）      |
| IDifyChatClient.stopResponse       | /chat-messages/:task_id/stop         | POST   | 停止响应            |
| IDifyChatClient.suggestedList      | /messages/{message_id}/suggested     | GET    | 下一轮问题列表         |
| IDifyChatClient.history            | /messages                            | GET    | 获取会话历史消息        |
| IDifyChatClient.conversations      | /conversations                       | GET    | 获取会话列表          |
| IDifyChatClient.deleteConversation | /conversations/:conversation_id      | DELETE | 删除会话            |
| IDifyChatClient.renameConversation | /conversations/:conversation_id/name | POST   | 会话重命名           |
| IDifyFlowClient.runStreaming       | /workflows/run                       | POST   | 执行 workflow（流式） |
| IDifyFlowClient.runBlocking        | /workflows/run                       | POST   | 执行 workflow（阻塞） |
| IDifyFlowClient.getWorkFlowStatus  | /workflows/run/:workflow_id          | GET    | 获取 workflow执行情况 |
| IDifyFlowClient.getWorkFlowLog     | /workflows/logs                      | GET    | 获取 workflow 日志  |
| IDifyFlowClient.stopWorkFlow       | /workflows/tasks/:task_id/stop       | POST   | 停止响应workflow    |
| IDifyCompletion.sendMessages       | /completion-messages                 | POST   | 发送文本生成消息（阻塞）    |
| IDifyCompletion.sendMessagesAsync  | /completion-messages                 | POST   | 发送文本生成消息（流式）    |
| IDifyCompletion.stopGenerate       | /completion-messages/:task_id/stop   | POST   | 停止响应completion  |