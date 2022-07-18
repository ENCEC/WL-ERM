package com.gillion.rulenumber;

import com.gillion.ec.rule.number.cache.RuleNumberQueues;
import com.gillion.ec.rule.number.cache.RuleNumberQueuesBuilder;
import com.gillion.ec.rule.number.utils.RuleNumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wengms
 * @date 2021/1/11 3:10 下午
 * @email wengms@gillion.com.cn
 */
@RestController
@RequestMapping("/rule-number/generate")
@Slf4j
public class RuleNumberGeneratorController {
    private RuleNumberQueues ruleNumberQueues = RuleNumberQueuesBuilder
            .with("ORDER_NO")
            .perGeneratorCount(100)
            .local()
            .build();

    private RuleNumberQueues remoteRuleNumberQueues = RuleNumberQueuesBuilder
            .with("REMOTE_CACHE_ORDER_NO")
            .perGeneratorCount(10)
            .remote()
            .build();

    @GetMapping("/single")
    public String singleGenerateNumber(){
        Map<String,Object> parameters = new HashMap<String,Object>(){{
            put("companyCode","Gillion");
        }};
        return RuleNumberUtils.generate("ORDER_NO",parameters);
    }


    @GetMapping("/batch")
    public List<String> batchGenerateNumber(){
        Map<String,Object> parameters = new HashMap<String,Object>(){{
            put("companyCode","ALI");
        }};
        return RuleNumberUtils.generate("ORDER_NO",10,parameters);
    }

    @GetMapping("/local-cache")
    public String generate(){
        return ruleNumberQueues.poll("Gillion");
    }

    @GetMapping("/remote-cache")
    public String remoteGenerate(){
        String result = remoteRuleNumberQueues.poll("Gillion");
        log.info("生成编号为:{}",result);
        return result;
    }


}
