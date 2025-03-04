dify-java-client
---

<p style="text-align: left">
    <a href="https://openjdk.org/projects/jdk/17" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/Java-17-blue" /> 
    </a>
    <a href="https://central.sonatype.com/artifact/io.github.yuanbaobaoo/dify-java-client" target="_blank">
        <img alt="maven-central" src="https://img.shields.io/badge/maven--central-1.0.0-green" /> 
    </a>
</p>

Dify Java Client

[中文](./README.md) | English

### Quick Start
- env requirements
```code
Java : >= 17
Maven: >= 3
Dify Api: <= 1.0.0
```

- maven
```xml
<dependency>
    <groupId>io.github.yuanbaobaoo</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### Create Client
```java
/**
 * Support methods: build()、buildChat()、buildFlow(), which have inconsistent return types
 */
IDifyClient client = DifyClientBuilder.create().apiKey("app-xxxx").baseUrl("http://localhost:4000/v1").build();
```

##### 1、IDifyClient: Base Client
Encapsulates some public APIs and authentication logic, and provides simple and easy-to-use calling methods
```java
// Call preset API
String metaInfo = client.getAppMetaInfo();
// Request custom API
String result = client.requestJson(DifyRoute.buildGet("/messages"));
// Upload file
DifyFileResult result = client.uploadFile(new File("pom.xml"), "abc-123");
```

##### 2、IDifyChatClient: scope = ChatBot、Agent、ChatFlow
```IDifyChatClient``` extends ```IDifyClient```, provides conversation APIs
```java
IDifyChatClient client = DifyClientBuilder.create()
        .apiKey("app-xxxx")
        .baseUrl("http://localhost:4000/v1")
        .buildChat();

// create message
ParamMessage m = ParamMessage.builder().query("你是谁").user("abc-123").inputs(new HashMap<>() {{
    put("test", "value");
}}).build();

// send block message
DifyChatResult result = client.sendMessages(m);

// send streaming message
CompletableFuture<Void> future = client.sendMessagesAsync(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

##### 3、IDifyWorkChatClient: scope = WorkFlow
```IDifyWorkChatClient``` extends ```IDifyClient```, provides workflow APIs
```java
IDifyWorkChatClient client = DifyClientBuilder.create()
        .apiKey("app-xxxx")
        .baseUrl("http://localhost:4000/v1")
        .buildWorkFlow();

// create message
ParamMessage m = ParamMessage.builder().query("测试方法有哪些").user("abc-123").inputs(new HashMap<>() {{
    put("name", "元宝宝");
}}).build();

// run workflow for blocking
DifyChatResult result = client.runBlocking(m);

// run workflow for streaming
CompletableFuture<Void> future = client.runStreaming(m, (r) -> {
    System.out.println("ok: " + r.getPayload().toJSONString());
});
```

#### Create External Knowledge
The current project dose not implement the knowledge API, and only declare parameter objects and interfaces
```java
public interface IDifyKonwledgeService {
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
- 2、implement the interface```io.github.yuanbaobaoo.dify.service.IKnowledgeService```
```java
class KnowledgeService implements IKnowledgeService {
    @Override
    public KnowledgeResult retrieval(String apiKey, KnowledgeArgs args) {
        // TODO Your local knowledge retrieval logic
    }
}
```

### Support API
The currently supported API, you can refer to the above three interface files. 
The project is currently being updated, and if the interface is not satisfied, 
you can call ```requestJSON```、```requestMultipart```to request.

| Method                             | Dify Api                             | Method | 描述                                     |
|------------------------------------|--------------------------------------|--------|----------------------------------------|
| IDifyClient.getAppInfo             | /info                                | GET    | Get Application Basic Information      |
| IDifyClient.getAppParameters       | /parameters                          | GET    | Get Application Parameters Information |
| IDifyClient.getAppMetaInfo         | /meta                                | GET    | Get Application Meta Information       |
| IDifyClient.uploadFile             | /files/upload                        | POST   | File Upload                            |
| IDifyChatClient.sendMessages       | /chat-messages                       | POST   | Send Chat Message(blocking)            |
| IDifyChatClient.sendMessagesAsync  | /chat-messages                       | POST   | Send Chat Message(streaming)           |
| IDifyChatClient.stopResponse       | /chat-messages/:task_id/stop         | POST   | Stop Generate                          |
| IDifyChatClient.suggestedList      | /messages/{message_id}/suggested     | GET    | Next Suggested Questions               |
| IDifyChatClient.history            | /messages                            | GET    | Get Conversation History Messages      |
| IDifyChatClient.conversations      | /conversations                       | GET    | Get Conversations                      |
| IDifyChatClient.deleteConversation | /conversations/:conversation_id      | DELETE | Delete Conversation                    |
| IDifyChatClient.renameConversation | /conversations/:conversation_id/name | POST   | Conversation Rename                    |
| IDifyChatClient.audioToText        | /audio-to-text                       | POST   | Speech to Text                         |
| IDifyWorkFlowClient.runStreaming   | /workflows/run                       | POST   | Execute Workflow (streaming)           |
| IDifyWorkFlowClient.runBlocking    | /workflows/run                       | POST   | Execute Workflow (blocking)            |

[//]: # (| IDifyWorkFlowClient.getWorkFlowStatus | /workflows/logs                      | GET    | Get Workflow Logs                        |)
[//]: # (| IDifyWorkFlowClient.stopWorkFlow      | /workflows/tasks/:task_id/stop       | POST   | Stop Generate                           |)