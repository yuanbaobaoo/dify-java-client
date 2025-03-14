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

Dify Java Client

[中文](./README.md) | English

### Quick Start
- env requirements
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

### Create Client
```java
/**
 * Support methods: create()、chat()、flow()、completion(), which have inconsistent return types
 */
IDifyBaseClient client = DifyClientBuilder.create().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();
```

#### 1、IDifyBaseClient: Base Client
Encapsulates some public APIs and authentication logic, and provides simple and easy-to-use calling methods
```java
// Call preset API
String metaInfo = client.getAppMetaInfo();
// Request custom API
String result = client.requestJson(DifyRoute.buildGet("/messages"));
// Upload file
DifyFileResult result = client.uploadFile(new File("pom.xml"), "abc-123");
```

#### 2、IDifyChatClient: scope = ChatBot、Agent、ChatFlow
```IDifyChatClient``` extends ```IDifyBaseClient```, provides conversation APIs
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

#### 3、IDifyFlowClient: scope = WorkFlow
```IDifyFlowClient``` extends ```IDifyBaseClient```, provides workflow APIs
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

#### 4、IDifyCompletion: scope = Completion
```IDifyCompletion``` extends ```IDifyBaseClient```, provides completion APIs
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

### Create External Knowledge
The current project dose not implement the knowledge API, and only declare parameter objects and interfaces
```java
public interface IDifyKnowledgeService {
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
- 2、implement the interface```io.github.yuanbaobaoo.dify.service.IDifyKnowledgeService```
```java
class KnowledgeService implements IDifyKnowledgeService {
    @Override
    public KnowledgeResult retrieval(String apiKey, KnowledgeArgs args) {
        // TODO Your local knowledge retrieval logic
    }
}
```

### Exception Type
By default, when the normal request returns http status>=400, an exception object ```DiffyException``` will be thrown.
This object receives the '```status```, ```code```, ```message```, ```params```.
Of course, you can also get the original return content through the ```getOriginal()``` method.

### Support API
The currently supported API, you can refer to the above three interface files. 
The project is currently being updated, and if the interface is not satisfied, 
you can call ```requestJSON```、```requestMultipart```to request.
>Declare an DifyRoute object: ```DifyRoute route = new DifyRoute("/info", HttpMethod.GET);```

| Method                             | Dify Api                             | Method | 描述                                     |
|------------------------------------|--------------------------------------|--------|----------------------------------------|
| IDifyBaseClient.getAppInfo         | /info                                | GET    | Get Application Basic Information      |
| IDifyBaseClient.getAppParameters   | /parameters                          | GET    | Get Application Parameters Information |
| IDifyBaseClient.getAppMetaInfo     | /meta                                | GET    | Get Application Meta Information       |
| IDifyBaseClient.uploadFile         | /files/upload                        | POST   | File Upload                            |
| IDifyBaseClient.feedbacks          | /messages/:message_id/feedbacks      | POST   | Message Feedback                       |
| IDifyBaseClient.audioToText        | /audio-to-text                       | POST   | Speech to Text                         |
| IDifyChatClient.sendMessages       | /chat-messages                       | POST   | Send Chat Message(blocking)            |
| IDifyChatClient.sendMessagesAsync  | /chat-messages                       | POST   | Send Chat Message(streaming)           |
| IDifyChatClient.stopResponse       | /chat-messages/:task_id/stop         | POST   | Stop Generate                          |
| IDifyChatClient.suggestedList      | /messages/{message_id}/suggested     | GET    | Next Suggested Questions               |
| IDifyChatClient.history            | /messages                            | GET    | Get Conversation History Messages      |
| IDifyChatClient.conversations      | /conversations                       | GET    | Get Conversations                      |
| IDifyChatClient.deleteConversation | /conversations/:conversation_id      | DELETE | Delete Conversation                    |
| IDifyChatClient.renameConversation | /conversations/:conversation_id/name | POST   | Conversation Rename                    |
| IDifyFlowClient.runStreaming       | /workflows/run                       | POST   | Execute Workflow (streaming)           |
| IDifyFlowClient.runBlocking        | /workflows/run                       | POST   | Execute Workflow (blocking)            |
| IDifyFlowClient.getWorkFlowStatus  | /workflows/run/:workflow_id          | GET    | Get Workflow Status                    |
| IDifyFlowClient.getWorkFlowLog     | /workflows/logs                      | GET    | Get Workflow Logs                      |
| IDifyFlowClient.stopWorkFlow       | /workflows/tasks/:task_id/stop       | POST   | Stop Generate                          |
| IDifyCompletion.sendMessages       | /completion-messages                 | POST   | Send Completion Message(blocking)      |
| IDifyCompletion.sendMessagesAsync  | /completion-messages                 | POST   | Send Completion Message(streaming)     |
| IDifyCompletion.stopGenerate       | /completion-messages/:task_id/stop   | POST   | Stop Generate                          |