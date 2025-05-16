dify-java-client
---

<p style="text-align: left">
    <a href="https://github.com/yuanbaobaoo/dify-java-client" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/github-🤪-green" /> 
    </a>
    &nbsp;
    <a href="https://openjdk.org/projects/jdk/17" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/Java-17-blue" /> 
    </a>
    &nbsp;
    <a href="https://central.sonatype.com/artifact/io.github.yuanbaobaoo/dify-java-client" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/maven--central-1.4.4-green" /> 
    </a>
</p>

The Simple and Easy-to-Use Dify Java Client, Support the use of [Dify](https://dify.ai/) Workflow, Assistant, Agent, Dataset, text to speech, voice to text, etc...

[中文](./README.md) | English

### Installation
- Requirements
```code
Java : >= 17
Dify Version: <= 1.x
```

- Maven
```xml
<dependency>
    <groupId>io.github.yuanbaobaoo</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>1.4.4</version>
</dependency>
```

- Gradle
```gradle
implementation group: 'io.github.yuanbaobaoo', name: 'dify-java-client', version: '1.4.4'
```

## Quick Start
Create client instances using ```DifyClientBuilder```
```java
/**
 * Create a chat-type client for ChatBot/Agent/ChatFlow/Completion apps
 */
IAppChatClient appClient = DifyClientBuilder.app().chat().apiKey("app-xxx").baseUrl("https://api.dify.ai/v1").build();

/**
 * Create a dataset client
 */
IDatasetClient datasetClient = DifyClientBuilder.dataset().apiKey("app-xxx").baseUrl("https://api.dify.ai/v1").build();

/**
 * Create a WebConsole client (experimental)
 */
IWebConsoleClient webClient = DifyClientBuilder.web("${server}", "${userName}", "${password}").connect();
```

## App Clients
Use ```DifyClientBuilder.app()``` to create app-type clients for ChatBot, Agent, ChatFlow, and Completion applications.
It provides session related APIs and supports streaming return of sessions. It mainly includes the following:
- [```IAppBaseClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/app/IAppBaseClient.java)
- [```IAppChatClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/app/IAppChatClient.java)
- [```IAppFlowClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/app/IAppFlowClient.java)
- [```IAppCompletion```](https://github.com/yuanbaobaoo/dify-java-client/blob/master/src/main/java/io/github/yuanbaobaoo/dify/app/IAppCompletion.java)

### 1、IAppBaseClient
Base client providing common Dify APIs:
```java
IAppBaseClient client = DifyClientBuilder.app().base().apiKey("app-xxxx").baseUrl("https://api.dify.ai/v1").build();

// Call preset API
String metaInfo = client.getAppMetaInfo();
// Upload file
DifyFileResult result = client.uploadFile(new File("pom.xml"), "abc-123");
```

### 2、IAppChatClient
For ChatBot/Agent/ChatFlow apps (extends IAppBaseClient):
```java
IAppChatClient client = DifyClientBuilder.app().chat().apiKey("app-xxxx").baseUrl("https://api.dify.ai/v1").build();

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

### 3、IAppFlowClient
For WorkFlow apps (extends IAppBaseClient):
```java
IAppFlowClient flowClient = DifyClientBuilder.app().flow().apiKey("app-xxxx").baseUrl("https://api.dify.ai/v1").build();

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

### 4、IAppCompletion
scope = Completion, extends ```IDifyBaseClient```, provides completion APIs
```java
IAppCompletion completion = DifyClientBuilder.completion().flow().apiKey("app-xxxx").baseUrl("https://api.dify.ai/v1").build();

// create message
ParamMessage m = ParamMessage.builder().query("Java为什么叫Java").user("abc-123").build();

// run with blocking
DifyChatResult result = completion.sendMessages(m);

// run with streaming
CompletableFuture<Void> future = completion.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

## Dataset Clients
The current project provides the definition of internal knowledge base client and external knowledge base related types, 
of which the external knowledge base has not been specifically implemented.

### Dify Dataset: IDifyDatasetClient
For specific API definitions, please refer to [```io.github.yuanbaobaoo.dify.client.dataset.IDifyDatasetClient```](https://github.com/yuanbaobaoo/dify-java-client/blob/feature/knowledge-api/src/main/java/io/github/yuanbaobaoo/dify/dataset/IDatasetClient.java)

#### Examples
```java
IDatasetClient client = DifyClientBuilder.dataset().apiKey("dataset-xxxx").baseUrl("https://api.dify.ai/v1").build();

// create params
ParamDataset dataset = ParamDataset.builder()
        .name("your knowledge name")
        .build();

// request create
client.create(dataset);
```

#### Hero Classes
In the project, for the tool class of the knowledge base, in addition to providing related methods based on ```IDatasetClient```, 
it also provides Hero class to support the operation of the knowledge base.Which way to use depends on your needs.

- Case 1: add document
```java
// dify config
DifyConfig config = DifyConfig.builder().server("https://api.dify.ai/v1").apiKey("dataset-xxxx").build();

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
Mode3、use Hero Class
```java
DatasetHero dataset = DatasetHero.of("Dataset Id", config);
dataset.insertTxt(document);
```

- Case 2: update document
```java
// dify config
DifyConfig config = DifyConfig.builder().server("https://api.dify.ai/v1").apiKey("dataset-xxxx").build();

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

### External Dataset: IKnowledgeService
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

## WebConsole Client
- params
```java
 WebConfig config = WebConfig.builder()
            .server("https://api.dify.ai/")
            .userName("username")
            .password("password")
            .build();
```
- login
```java
IWebConsoleClient client = DifyClientBuilder.web(config).connect();
```
- request
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

## Custom Requests
Any Client object has an httpClient () method,
This method will return a SimpleFHIR object that has been injected with API key and server URL or login token attributes. 
You can use this object to make custom interface requests.
```java
SimpleHttpClient http = client.httpClient();
```

## Exception Type
- **DifyException**
>By default, when the normal request returns http status>=400, an exception object ```DiffyException``` will be thrown.
This object receives the '```status```, ```code```, ```message```, ```params```.
Of course, you can also get the original return content through the ```getOriginal()``` method.

- **DifyClientException**
>Except for ```DifyException```, all other types of exceptions will be wrapped as non inspected exceptions ```DifyClientException``` and thrown