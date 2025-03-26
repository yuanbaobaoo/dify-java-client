package io.github.yuanbaobaoo.dify;

import com.alibaba.fastjson2.JSON;
import io.github.yuanbaobaoo.dify.dataset.IDatasetClient;
import io.github.yuanbaobaoo.dify.dataset.entity.*;
import io.github.yuanbaobaoo.dify.dataset.heros.DatasetHero;
import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDataset;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDocument;
import io.github.yuanbaobaoo.dify.dataset.types.*;
import io.github.yuanbaobaoo.dify.types.DifyException;
import io.github.yuanbaobaoo.dify.types.DifyPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatasetClientTest {
    IDatasetClient client = DifyAppClientBuilder.dataset()
            .baseUrl("http://localhost:4000")
            .apiKey("dataset-")
            .build();

    @Test
    public void testCreate() {
        try {
            ParamDataset dataset = ParamDataset.builder()
                    .name("test6")
                    .build();

            DatasetHero set = client.create(dataset);
            System.out.println("ok: " + JSON.toJSONString(set));

            ParamDocument document = ParamDocument.builder()
                    .name("测试文档")
                    .text("你好啊你好吧")
                    .indexingTechnique(DatasetConsts.IndexingTechnique.high_quality)
                    .processRule(ProcessRule.builder().mode(ProcessRule.Mode.automatic).build())
                    .build();

            DocumentResult doc = set.insertText(document);
            System.out.println("ok: " + JSON.toJSONString(doc));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testList() {
        try {
            DifyPage<Dataset> sets = client.list(1, 20);
            System.out.println("ok: " + JSON.toJSONString(sets));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() {
        try {
            client.delete("f779a91f-1f89-4581-bc26-95a2d50239d6");
        } catch (DifyException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateByText() {
        try {
            ParamDocument document = ParamDocument.builder()
                    .name("测试文档")
                    .text("你好啊你好吧")
                    .indexingTechnique(DatasetConsts.IndexingTechnique.economy)
                    .processRule(ProcessRule.builder().mode(ProcessRule.Mode.automatic).build())
                    .build();

            DatasetHero set = client.ofDataset("7a07305e-2663-4ae5-82aa-4e6297aaa946");
            DocumentResult doc = set.insertText(document);

            System.out.println("ok: " + JSON.toJSONString(doc));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBatchIndexStatus() {
        try {

            DatasetHero set = client.ofDataset("7a07305e-2663-4ae5-82aa-4e6297aaa946");
            List<BatchStatus> bs = set.queryBatchStatus("20250318080900526712");

            System.out.println("ok: " + JSON.toJSONString(bs));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateByFile() {
        try {
            ParamDocument document = ParamDocument.builder()
                    .indexingTechnique(DatasetConsts.IndexingTechnique.economy)
                    .processRule(ProcessRule.builder().mode(ProcessRule.Mode.automatic).build())
                    .build();

            DatasetHero set = client.ofDataset("7a07305e-2663-4ae5-82aa-4e6297aaa946");
            DocumentResult doc = set.insertFile(new File("C:\\Users\\Hodge\\Desktop\\测试.txt"), document);

            System.out.println("ok: " + JSON.toJSONString(doc));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocList() {
        try {
            DatasetHero set = client.ofDataset("7a07305e-2663-4ae5-82aa-4e6297aaa946");
            DifyPage<Document> docs = set.documents();
            System.out.println("ok: " + JSON.toJSONString(docs));

            DifyPage<Document> docs2 = set.documents(1, 1, null);
            System.out.println("ok: " + JSON.toJSONString(docs2));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRetrieve() {
        try {
            DatasetHero set = client.ofDataset("c74c5f4b-77c6-4572-a16f-576073f03a1a");
            RetrieveResult res = set.retrieve("1");

            System.out.println("ok: " + JSON.toJSONString(res));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocUpdateByText() {
        try {
            DocumentHero document = client.ofDocument(
                    "7a07305e-2663-4ae5-82aa-4e6297aaa946",
                    "2fd22db4-dea9-48d4-89c2-44bf67a2f313"
            );

            DocumentResult res = document.updateByText(ParamDocument.builder()
                    .name("测试.txt2")
                    .text("又疑瑶台近，飞上青云端")
                    .build()
            );

            System.out.println("ok: " + JSON.toJSONString(res));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocUpdateByFile() {
        try {
            DocumentHero document = client.ofDocument(
                    "7a07305e-2663-4ae5-82aa-4e6297aaa946",
                    "2fd22db4-dea9-48d4-89c2-44bf67a2f313"
            );

            DocumentResult res = document.updateByFile(
                    new File("C:\\Users\\Hodge\\Desktop\\测试.txt"),
                    ProcessRule.builder().mode(ProcessRule.Mode.automatic).build()
            );

            System.out.println("ok: " + JSON.toJSONString(res));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocDelete() {
        try {
            DocumentHero document = client.ofDocument(
                    "7a07305e-2663-4ae5-82aa-4e6297aaa946",
                    "2fd22db4-dea9-48d4-89c2-44bf67a2f313"
            );

            Boolean res = document.delete();

            System.out.println("ok: " + JSON.toJSONString(res));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocSegList() {
        try {
            DocumentHero document = client.ofDocument(
                    "7a07305e-2663-4ae5-82aa-4e6297aaa946",
                    "3094bc50-6356-406f-a3ae-c2541eb406cd"
            );

            SegmentResult res = document.querySegments();
            System.out.println("ok: " + JSON.toJSONString(res));

            SegmentResult res1 = document.querySegments("Copy");
            System.out.println("ok: " + JSON.toJSONString(res1));

            SegmentResult res2 = document.querySegments(null, "completed");
            System.out.println("ok: " + JSON.toJSONString(res2));

        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocSegAdd() {
        try {
            DocumentHero document = client.ofDocument(
                    "7a07305e-2663-4ae5-82aa-4e6297aaa946",
                    "3094bc50-6356-406f-a3ae-c2541eb406cd"
            );

            Segment segment = Segment.builder().content("你好").keywords(new ArrayList<>() {{
                add("你");
                add("好");
                add("吗");
            }}).build();

            SegmentResult res = document.insertSegment(List.of(segment));
            System.out.println("ok: " + JSON.toJSONString(res));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocSegDel() {
        try {
            DocumentHero document = client.ofDocument(
                    "7a07305e-2663-4ae5-82aa-4e6297aaa946",
                    "3094bc50-6356-406f-a3ae-c2541eb406cd"
            );

            Boolean res = document.deleteSegment("84ab19cb-8be5-4f50-a699-3c40e9839169");
            System.out.println("ok: " + JSON.toJSONString(res));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocSegUpdate() {
        try {
            DocumentHero document = client.ofDocument(
                    "7a07305e-2663-4ae5-82aa-4e6297aaa946",
                    "3094bc50-6356-406f-a3ae-c2541eb406cd"
            );

            SegmentResult res = document.updateSegment("5f765e3f-633e-49c2-a862-8d2eecb6dbca", "你好吧", false);
            System.out.println("ok: " + JSON.toJSONString(res));

            SegmentResult res1 = document.updateSegment(
                    Segment.builder()
                            .id("5f765e3f-633e-49c2-a862-8d2eecb6dbca")
                            .content("我不好")
                            .build(),
                    false
            );
            System.out.println("ok: " + JSON.toJSONString(res1));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocFileInfo() {
        try {
            DocumentHero document = client.ofDocument(
                    "7a07305e-2663-4ae5-82aa-4e6297aaa946",
                    "3094bc50-6356-406f-a3ae-c2541eb406cd"
            );

            DocFileInfo info = document.queryFileInfo();
            System.out.println("ok: " + JSON.toJSONString(info));
        } catch (DifyException e) {
            System.out.println("error dify: " + e.getOriginal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
