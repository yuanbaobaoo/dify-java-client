package io.github.yuanbaobaoo.dify.dataset.types;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProcessRule {
    /**
     * 清洗、分段模式
     */
    private Mode mode;

    /**
     * 自定义规则（自动模式下，该字段为空）
     * pre_processing_rules (array[object]) 预处理规则
     *      id (string) 预处理规则的唯一标识符。枚举：
     *          remove_extra_spaces 替换连续空格、换行符、制表符
     *          remove_urls_emails 删除 URL、电子邮件地址
     *      enabled (bool) 是否选中该规则，不传入文档 ID 时代表默认值
     * segmentation (object) 分段规则
     *      separator 自定义分段标识符，目前仅允许设置一个分隔符。默认为 \n
     *      max_tokens 最大长度（token）默认为 1000
     * parent_mode 父分段的召回模式  full-doc 全文召回 / paragraph 段落召回
     * subchunk_segmentation (object) 子分段规则
     *      separator 分段标识符，目前仅允许设置一个分隔符。默认为 ***
     *      max_tokens 最大长度 (token) 需要校验小于父级的长度
     *      chunk_overlap 分段重叠指的是在对数据进行分段时，段与段之间存在一定的重叠部分（选填）
     */
    private Object rules;

    /**
     * 清洗、分段模式
     */
    public enum Mode {
        // 自动
        automatic ,
        // 自定义
        custom
    }

}
