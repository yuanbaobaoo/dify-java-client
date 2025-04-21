dify-java-client
---

<p style="text-align: left">
    <a href="https://openjdk.org/projects/jdk/17" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/Java-17-blue" /> 
    </a>
    <a href="https://central.sonatype.com/artifact/io.github.yuanbaobaoo/dify-java-client" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/maven--central-1.4.0-green" /> 
    </a>
</p>

简单易用的 Dify Java客户端，支持在任意Java项目中使用 [Dify](https://dify.ai/) 的工作流、对话助手、Agent、Dataset、文字转语音、语音转文字等开放能力。

中文 | [English](./README_EN.md)

## 安装依赖
- 环境需求
```code
Java : >= 17
Dify Version: <= 1.x
```

- maven
```xml
<dependency>
    <groupId>io.github.yuanbaobaoo</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>1.4.0</version>
</dependency>
```

- gradle
```gradle
implementation group: 'io.github.yuanbaobaoo', name: 'dify-java-client', version: '1.4.0'
```

## 快速开始
使用 ```DifyClientBuilder``` 创建各类客户端实例
```java
/**
 * 创建一个对话类型客户端对象，支持 base()、chat()、flow()、completion() 其对应返回类型也是不一致的
 */
IAppChatClient appClient = DifyClientBuilder.app().chat().apiKey("app-xxx").baseUrl("https://api.dify.ai/v1").build();

/**
 * 创建一个知识库类型客户端对象
 */
IDatasetClient datasetClient = DifyClientBuilder.dataset().apiKey("app-xxx").baseUrl("https://api.dify.ai/v1").build();

/**
 * 创建一个WebConsole类型客户端对象，用于模拟Dify控制台操作（试验特性）
 */
IWebConsoleClient webClient = DifyClientBuilder.web("${server}", "${userName}", "${password}").connect();
```

## 创建 App 类型的 Client
在代码中，使用 ```DifyClientBuilder.app()``` 即可创建App类型的客户端对象。
App客户端是指适用于 ChatBot、Agent、ChatFlow、Completion 类型的应用，提供了会话相关的API，支持会话的流式返回。主要包含如下：
- [```IAppBaseClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/app/IAppBaseClient.java)
- [```IAppChatClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/app/IAppChatClient.java)
- [```IAppFlowClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/app/IAppFlowClient.java)
- [```IAppCompletion```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/app/IAppCompletion.java)

### 1、IAppBaseClient
基础Client，提供Dify公共API，具体可查阅 ```IAppBaseClient```
```java
IAppBaseClient client = DifyClientBuilder.app().base().apiKey("app-xxxx").baseUrl("https://api.dify.ai/v1").build();

// 调用接口
String metaInfo = client.getAppMetaInfo();
// 上传文件
DifyFileResult result = client.uploadFile(new File("pom.xml"), "abc-123");
```

### 2、IAppChatClient
适用于 ChatBot、Agent、ChatFlow 类型应用，继承自 ```IAppBaseClient```，提供了会话相关的API：
```java
IAppChatClient client = DifyClientBuilder.app().chat().apiKey("app-xxxx").baseUrl("https://api.dify.ai/v1").build();

// 创建消息
ParamMessage m = ParamMessage.builder().query("你是谁").user("abc-123").inputs(Map.of(
        "test", "value",
        "file1", ParamFile.builder()
                .type(ParamFile.FileType.audio)
                .transferMethod(ParamFile.TransferMethod.remote_url)
                .build()
)).build();

// 发送阻塞消息
DifyChatResult result = chatClient.sendMessages(m);

// 发送流式消息
CompletableFuture<Void> future = client.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

### 3、IAppFlowClient
适用于 WorkFlow 类型应用，继承自 ```IAppBaseClient```，提供了工作流相关的API：
```java
IAppFlowClient flowClient = DifyClientBuilder.app().flow().apiKey("app-xxxx").baseUrl("https://api.dify.ai/v1").build();

// 创建消息
ParamMessage m = ParamMessage.builder().user("abc-123").inputs(Map.of(
        "name", "元宝宝",
        "text", "Java为什么叫Java？"
)).build();

// 阻塞式运行工作流
DifyChatResult result = flowClient.runBlocking(m);

// 流式运行工作流
CompletableFuture<Void> future = client.runStreaming(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

### 4、IAppCompletion
适用于 Completion 类型应用，继承自 ```IAppBaseClient```，提供了文本生成相关的API：
```java
IAppCompletion completion = DifyClientBuilder.completion().flow().apiKey("app-xxxx").baseUrl("https://api.dify.ai/v1").build();

// 创建消息
ParamMessage m = ParamMessage.builder().query("Java为什么叫Java").user("abc-123").build();

// 阻塞式运行
DifyChatResult result = completion.sendMessages(m);

// 流式运行
CompletableFuture<Void> future = completion.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

## 创建 知识库 类型的 Client
当前项目提供了内部知识库Client 与 外部知识库相关类型定义，其中外部知识库并没有做具体实现。

### Dify内置知识库: IDifyDatasetClient
具体API定义，请查阅 [```io.github.yuanbaobaoo.dify.dataset.IDifyDatasetClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/dataset/IDatasetClient.java)

#### 参考案例
```java
IDatasetClient client = DifyClientBuilder.dataset().apiKey("dataset-xxxx").baseUrl("https://api.dify.ai/v1").build();

// 声明参数
ParamDataset dataset = ParamDataset.builder()
        .name("你的知识库名称")
        .build();

// 创建空知识库
client.create(dataset);
```

#### Hero类
项目中，针对知识库的工具类，除了提供了基于 ```IDatasetClient``` 的相关方法外，还提供了Hero类对知识库进行操作支持。
具体使用哪种方式，取决于你的需求。

- 案例一：新增文档
```java
// dify 配置
DifyConfig config = DifyConfig.builder().server("https://api.dify.ai/v1").apiKey("dataset-xxxx").build();

// 声明参数
ParamDocument document = ParamDocument.builder()
        .name("测试文档")
        .text("你好啊你好吧")
        .indexingTechnique(DatasetConsts.IndexingTechnique.high_quality)
        .processRule(ProcessRule.builder().mode(ProcessRule.Mode.automatic).build())
        .build();
```
方式1、直接使用 IDatasetClient操作API
```java
IDatasetClient client = DifyClientBuilder.dataset().config(config).build();
client.insertDocByText("知识库ID", document);
```
方式2、使用 DifyClientBuilder 创建一个 Hero对象
```java
DatasetHero dataset = DifyClientBuilder.dataset().config(config).of("知识库ID");
dataset.insertTxt(document);
```
方式3、直接创建Hero对象
```java
DatasetHero dataset = DatasetHero.of("知识库ID", config);
dataset.insertTxt(document);
```

- 案例2：更新文档
```java
// dify 配置
DifyConfig config = DifyConfig.builder().server("https://api.dify.ai/v1").apiKey("dataset-xxxx").build();

// 声明参数
ParamDocument document = ParamDocument.builder()
        .name("测试.txt2")
        .text("又疑瑶台近，飞上青云端")
        .build();
```
方式1、直接使用 IDatasetClient操作API
```java
IDatasetClient client = DifyClientBuilder.dataset().config(config).build();
client.updateDocByText("知识库ID", "文档ID", dataset);
```
方式2、使用 DifyClientBuilder 创建一个 Hero对象
```java
DocumentHero documentHero = DifyClientBuilder.dataset().config(config).ofDocument("知识库ID", "文档ID");
documentHero.updateByText(document);
```
方式3、直接创建Hero对象
```java
DocumentHero documentHero = DocumentHero.of("知识库ID", "文档ID", config);
documentHero.updateByText(document);
```

### Dify外部知识库: IKnowledgeService
当前项目并未对知识库API做实现，只声明了相关参数对象和接口
```java
public interface IKnowledgeService {
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
- 2、实现接口```io.github.yuanbaobaoo.dify.dataset.IKnowledgeService```
```java
class KnowledgeService implements IKnowledgeService {
    @Override
    public KnowledgeResult retrieval(String apiKey, KnowledgeArgs args) {
        // TODO 你的本地知识库检索逻辑
    }
}
```

## 创建 WebConsole Client
用于模拟登录到 Dify Web控制台，并请求控制台的接口。
- 参数
```java
 WebConfig config = WebConfig.builder()
            .server("https://api.dify.ai/")
            .userName("username")
            .password("password")
            .build();
```
- 登录
```java
IWebConsoleClient client = DifyClientBuilder.web(config).connect();
```
- 请求
```java
/**
 * get app list
 */
DifyPage<JSONObject> res = client.queryApps(1, 30, null);

/**
 * request custom api
 */
String res = client.httpClient().requestJson();
```

## 请求自定义接口
任意的Client对象，都拥有一个httpClient()方法，
该方法会返回一个已经注入了 api key 和 server url 或者 login token属性的 SimpleHttpClient 对象，你可以使用该对象进行自定义接口请求。
```java
SimpleHttpClient http = client.httpClient();
```

## 异常处理
- **DifyException**
>默认情况下，当正常的请求返回了 http status >= 400 时，都会抛出一个异常对象 ```DifyException```。
该对象接收了Dify返回的 ```status```、```code```、```message```、```params``` 这几个属性。
当然你也可以通过 ```getOriginal()``` 方法获取原始返回内容。

- **DifyClientException**
>除了 ```DifyException``` 之外，其他所有类型的异常都会被包装成非受检异常 ```DifyClientException``` 抛出