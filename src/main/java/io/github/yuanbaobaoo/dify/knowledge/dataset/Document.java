package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class Document {
    private String id;
    private Integer position;
    private String dataSourceType;
    private JSONObject dataSourceInfo;
    private JSONObject dataSourceDetailDict;
    private String datasetProcessRuleId;
    private String name;
    private String createdFrom;
    private String createdBy;
    private Integer createdAt;
    private String tokens;
    private String indexingStatus;
    private String error;
    private Boolean enabled;
    private String disabledAt;
    private String disabledBy;
    private Boolean archived;
    private String displayStatus;
    private Long wordCount;
    private Long hitCount;
    private DatasetConsts.DocForm docForm;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Api apiClient;

    /**
     * Constructor
     */
    public Document() {

    }

    /**
     * Constructor
     *
     * @param id     dataset id
     * @param client DatasetClient
     */
    public Document(String id, DifyHttpClient client) {
        this.id = id;
        this.apiClient = new Api(client);
    }

    /**
     * get api client
     * @returnx
     */
    public Api api() {
        return apiClient;
    }

    /**
     * DifyHttpClient
     * @param client DifyHttpClient
     */
    public void resetDifyClient(DifyHttpClient client) {
        this.apiClient = new Api(client);;
    }

    /**
     * Document Api Util
     */
    public class Api {
        private final DifyHttpClient client;

        /**
         * constructor
         * @param client DifyHttpClient
         */
        public Api(DifyHttpClient client) {
            this.client = client;
        }

        /**
         * 通过文本更新文档
         */
        public void updateByText() {

        }

        /**
         * 通过文件更新文档
         */
        public void updateByFile() {

        }

        /**
         * 删除文档
         */
        public void delete() {

        }

        /**
         * 获取文档嵌入状态（进度）
         *
         * @param batch 上传文档的批次号
         */
        public void getIndexingStatus(List<String> batch) {

        }

        public void getSegments() {

        }

        public void addSegments() {

        }

        public void delSegments() {

        }


        public void setSegments() {

        }

        public void selectUploadFiles() {

        }
    }
}
