dify-java-client
---

<p style="text-align: left">
    <a href="https://openjdk.org/projects/jdk/17" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/Java-17-blue" /> 
    </a>
    <a href="https://central.sonatype.com/artifact/io.github.yuanbaobaoo/dify-java-client" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/maven--central-1.3.0-green" /> 
    </a>
</p>

简单易用的 Dify Java客户端

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
    <version>1.3.0</version>
</dependency>
```

- gradle
```gradle
implementation group: 'io.github.yuanbaobaoo', name: 'dify-java-client', version: '1.3.0'
```

## 快速开始 
```DifyClientBuilder```： 用于创建各类客户端实例
```java
/**
 * 支持 base()、chat()、flow()、completion()、dataset()，其对应返回类型也是不一致的
 */
IDifyBaseClient client = DifyClientBuilder.base().apiKey("app-xxxx").baseUrl("http://localhost:4000").build();
```

## 对话类型 Client
app客户端是指适用于 ChatBot、Agent、ChatFlow、Completion 类型的应用，提供了会话相关的API，支持会话的流式返回。主要包含如下：
- [```IDifyBaseClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/app/IDifyBaseClient.java)
- [```IDifyChatClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/app/IDifyChatClient.java)
- [```IDifyFlowClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/app/IDifyFlowClient.java)
- [```IDifyCompletion```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/app/IDifyCompletion.java)

### 1、IDifyBaseClient
基础Client，提供Dify公共API，具体可查阅 ```IDifyBaseClient```
```java
IDifyBaseClient client = DifyClientBuilder.base().apiKey("app-xxxx").baseUrl("http://localhost:4000").build();

// 调用接口
String metaInfo = client.getAppMetaInfo();
// 上传文件
DifyFileResult result = client.uploadFile(new File("pom.xml"), "abc-123");
```

### 2、IDifyChatClient
适用于 ChatBot、Agent、ChatFlow 类型应用，继承自 ```IDifyBaseClient```，提供了会话相关的API：
```java
IDifyChatClient chatClient = DifyClientBuilder.chat().apiKey("app-xxxx").baseUrl("http://localhost:4000").build();

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

### 3、IDifyFlowClient
适用于 WorkFlow 类型应用，继承自 ```IDifyBaseClient```，提供了工作流相关的API：
```java
IDifyFlowClient flowClient = DifyClientBuilder.flow().apiKey("app-xxxx").baseUrl("http://localhost:4000").build();

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

### 4、IDifyCompletion
适用于 Completion 类型应用，继承自 ```IDifyBaseClient```，提供了文本生成相关的API：
```java
IDifyCompletion completion = DifyClientBuilder.completion().apiKey("app-xxxx").baseUrl("http://localhost:4000").build();

// 创建消息
ParamMessage m = ParamMessage.builder().query("Java为什么叫Java").user("abc-123").build();

// 阻塞式运行
DifyChatResult result = completion.sendMessages(m);

// 流式运行
CompletableFuture<Void> future = completion.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

## 知识库 Client
当前项目提供了内部知识库Client 与 外部知识库相关类型定义，其中外部知识库并没有做具体实现。

### Dify内置知识库: IDifyDatasetClient
具体API定义，请查阅 [```io.github.yuanbaobaoo.dify.client.dataset.IDifyDatasetClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/dataset/IDatasetClient.java)

#### 参考案例
```java
IDatasetClient client = DifyClientBuilder.dataset().apiKey("dataset-xxxx").baseUrl("http://localhost:4000").build();

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
DifyConfig config = DifyConfig.builder().server("http://localhost:4000").apiKey("dataset-xxxx").build();

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
DifyConfig config = DifyConfig.builder().server("http://localhost:4000").apiKey("dataset-xxxx").build();

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

### 异常处理
默认情况下，当正常的请求返回了 http status >= 400 时，都会抛出一个异常对象 ```DifyException```。
该对象接收了Dify返回的 ```status```、```code```、```message```、```params``` 这几个属性。
当然你也可以通过 ```getOriginal()``` 方法获取原始返回内容。