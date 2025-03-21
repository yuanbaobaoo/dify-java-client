dify-java-client
---

<p style="text-align: left">
    <a href="https://openjdk.org/projects/jdk/17" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/Java-17-blue" /> 
    </a>
    <a href="https://central.sonatype.com/artifact/io.github.yuanbaobaoo/dify-java-client" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/maven--central-1.3.1-green" /> 
    </a>
</p>

The Simple and Easy-to-Use Dify Java Client

[中文](./README.md) | English

### Install
- env requirements
```code
Java : >= 17
Dify Version: <= 1.x
```

- maven
```xml
<dependency>
    <groupId>io.github.yuanbaobaoo</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>1.3.1</version>
</dependency>
```

- gradle
```gradle
implementation group: 'io.github.yuanbaobaoo', name: 'dify-java-client', version: '1.3.0'
```

## Quick Start
```DifyClientBuilder```: Used to create various client instances
```java
/**
 * Support base()、chat()、flow()、completion()、dataset()，which have inconsistent return types
 */
IDifyBaseClient client = DifyClientBuilder.base().apiKey("app-xxxx").baseUrl("http://localhost:4000").build();
```

## Chat Type Client
Chat type clients refers to applications applicable to ChatBot, Agent, ChatFlow, and Completion. 
It provides session related APIs and supports streaming return of sessions. It mainly includes the following:
- [```IDifyBaseClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/app/IDifyBaseClient.java)
- [```IDifyChatClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/app/IDifyChatClient.java)
- [```IDifyFlowClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/app/IDifyFlowClient.java)
- [```IDifyCompletion```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/app/IDifyCompletion.java)

### 1、IDifyBaseClient
base client, encapsulates some public APIs and authentication logic, and provides simple and easy-to-use calling methods
```java
IDifyBaseClient client = DifyClientBuilder.base().apiKey("app-xxxx").baseUrl("http://localhost:4000").build();

// Call preset API
String metaInfo = client.getAppMetaInfo();
// Upload file
DifyFileResult result = client.uploadFile(new File("pom.xml"), "abc-123");
```

### 2、IDifyChatClient
scope = ChatBot、Agent、ChatFlow, extends ```IDifyBaseClient```, provides conversation APIs
```java
IDifyChatClient chatClient = DifyClientBuilder.chat().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();

// create message
ParamMessage m = ParamMessage.builder().query("Who are you").user("abc-123").inputs(new HashMap<>() {{
    put("test", "value");
    put("file1", ParamFile.builder()
            .type(ParamFile.FileType.audio)
            .transferMethod(ParamFile.TransferMethod.remote_url)
            .build()
    );
}}).build();

// send block message
DifyChatResult result = chatClient.sendMessages(m);

// send streaming message
CompletableFuture<Void> future = client.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

### 3、IDifyFlowClient
scope = WorkFlow, extends ```IDifyBaseClient```, provides workflow APIs
```java
IDifyFlowClient flowClient = DifyClientBuilder.flow().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();

// create message
ParamMessage m = ParamMessage.builder().user("abc-123").inputs(new HashMap<>() {{
    put("name", "元宝宝");
    put("text", "Java为什么叫Java？");
}}).build();

// run workflow for blocking
DifyChatResult result = flowClient.runBlocking(m);

// run workflow for streaming
CompletableFuture<Void> future = client.runStreaming(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

### 4、IDifyCompletion
scope = Completion, extends ```IDifyBaseClient```, provides completion APIs
```java
IDifyCompletion completion = DifyClientBuilder.completion().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();

// create message
ParamMessage m = ParamMessage.builder().query("Java为什么叫Java").user("abc-123").build();

// run with blocking
DifyChatResult result = completion.sendMessages(m);

// run with streaming
CompletableFuture<Void> future = completion.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

## Dataset(Knowledge) Client
The current project provides the definition of internal knowledge base client and external knowledge base related types, 
of which the external knowledge base has not been specifically implemented.

### Dify Dataset: IDifyDatasetClient
For specific API definitions, please refer to [```io.github.yuanbaobaoo.dify.client.dataset.IDifyDatasetClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/dataset/IDatasetClient.java)

#### Reference
```java
IDatasetClient client = DifyClientBuilder.dataset().apiKey("dataset-xxxx").baseUrl("http://localhost:4000").build();

// create params
ParamDataset dataset = ParamDataset.builder()
        .name("your knowledge name")
        .build();

// request create
client.create(dataset);
```

#### Hero Class
In the project, for the tool class of the knowledge base, in addition to providing related methods based on ```IDatasetClient```, 
it also provides Hero class to support the operation of the knowledge base.Which way to use depends on your needs.

- Case 1: add document
```java
// dify config
DifyConfig config = DifyConfig.builder().server("http://localhost:4000").apiKey("dataset-xxxx").build();

// create params
ParamDocument document = ParamDocument.builder()
        .name("测试文档")
        .text("你好啊你好吧")
        .indexingTechnique(DatasetConsts.IndexingTechnique.high_quality)
        .processRule(ProcessRule.builder().mode(ProcessRule.Mode.automatic).build())
        .build();
```
Mode1、use IDatasetClient
```java
IDatasetClient client = DifyClientBuilder.dataset().config(config).build();
client.insertDocByText("Dataset Id", document);
```
Mode2、use DifyClientBuilder to build the Hero object
```java
DatasetHero dataset = DifyClientBuilder.dataset().config(config).of("Dataset Id");
dataset.insertTxt(document);
```
Mod3、use Hero Class
```java
DatasetHero dataset = DatasetHero.of("Dataset Id", config);
dataset.insertTxt(document);
```

- Case 2: update document
```java
// dify config
DifyConfig config = DifyConfig.builder().server("http://localhost:4000").apiKey("dataset-xxxx").build();

// create params
ParamDocument document = ParamDocument.builder()
        .name("测试.txt2")
        .text("又疑瑶台近，飞上青云端")
        .build();
```
Mode1、use IDatasetClient
```java
IDatasetClient client = DifyClientBuilder.dataset().config(config).build();
client.updateDocByText("Dataset ID", "Document ID", dataset);
```
Mode2、use DifyClientBuilder to build the Hero object
```java
DocumentHero documentHero = DifyClientBuilder.dataset().config(config).ofDocument("Dataset ID", "Document ID");
documentHero.updateByText(document);
```
Mod3、use Hero Class
```java
DocumentHero documentHero = DocumentHero.of("Dataset ID", "Document ID", config);
documentHero.updateByText(document);
```

### Dify External Dataset: IKnowledgeService
The current project dose not implement the knowledge API, and only declare parameter objects and interfaces
```java
public interface IKnowledgeService {
    /**
     * retrieval
     * @param apiKey API KEY
     * @param args Knowledge Args
     */
    KnowledgeResult retrieval(String apiKey, KnowledgeArgs args);
}
```
If you have a need, you can implement the interface based on the declared parameters and interfaces, as follows is a reference code:
- 1、create an endpoint
```java
/**
 * retrieval api
 * @param args KnowledgeArgs
 */
@PostMapping("/retrieval")
public KnowledgeResult retrieval(@RequestBody(required = false) KnowledgeArgs args, HttpServletRequest request) {
    return knowledgeService.retrieval(request.getHeader("Authorization"), args);
}
```
- 2、implement the interface```io.github.yuanbaobaoo.dify.dataset.IKnowledgeService```
```java
class KnowledgeService implements IKnowledgeService {
    @Override
    public KnowledgeResult retrieval(String apiKey, KnowledgeArgs args) {
        // TODO Your local knowledge retrieval logic
    }
}
```

### Exception Type
- **DifyException**
>By default, when the normal request returns http status>=400, an exception object ```DiffyException``` will be thrown.
This object receives the '```status```, ```code```, ```message```, ```params```.
Of course, you can also get the original return content through the ```getOriginal()``` method.

- **DifyClientException**
>Except for ```DifyException```, all other types of exceptions will be wrapped as non inspected exceptions ```DifyClientException``` and thrown