package com.jxnu.elasticsearch.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shoumiao_yao
 * @date 2016-09-20
 */
public class SearchHelper {
    private Logger logger = LoggerFactory.getLogger(SearchHelper.class);
    private String[] indexs;
    private String[] types;
    private SearchRequest request = new SearchRequest();
    private List<QueryBuilder> mustQueryS = new ArrayList<QueryBuilder>();
    private List<QueryBuilder> mustNotQueryS = new ArrayList<QueryBuilder>();
    private List<QueryBuilder> filterQueryS = new ArrayList<QueryBuilder>();
    private List<QueryBuilder> shouldQueryS = new ArrayList<QueryBuilder>();

    public SearchHelper(String[] indexs, String[] types) {
        this.indexs = indexs;
        this.types = types;
    }

    public SearchHelper must(List<QueryBuilder> querys) {
        if (CollectionUtils.isEmpty(querys)) return this;
        mustQueryS.addAll(querys);
        return this;
    }

    public SearchHelper mustNot(List<QueryBuilder> querys) {
        if (CollectionUtils.isEmpty(querys)) return this;
        mustNotQueryS.addAll(querys);
        return this;
    }

    public SearchHelper filter(List<QueryBuilder> querys) {
        if (CollectionUtils.isEmpty(querys)) return this;
        filterQueryS.addAll(querys);
        return this;
    }

    public SearchHelper should(List<QueryBuilder> querys) {
        if (CollectionUtils.isEmpty(querys)) return this;
        shouldQueryS.addAll(querys);
        return this;
    }

    /**
     * 构建SearchRequest对象
     *
     * @return
     */
    public SearchRequest build() {
        if (!ArrayUtils.isEmpty(this.indexs)) {
            this.request.indices(this.indexs);
        }
        if (!ArrayUtils.isEmpty(this.types)) {
            this.request.types(this.types);
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (QueryBuilder builder : mustQueryS) {
            boolQueryBuilder.must(builder);
        }
        for (QueryBuilder builder : mustNotQueryS) {
            boolQueryBuilder.mustNot(builder);
        }
        for (QueryBuilder builder : filterQueryS) {
            boolQueryBuilder.filter(builder);
        }
        for (QueryBuilder builder : shouldQueryS) {
            boolQueryBuilder.should(builder);
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        logger.info("indexs:{},types:{},query build:{}", indexs, types, boolQueryBuilder);
        this.request.source(sourceBuilder);
        return this.request;
    }


    /**
     * 域相等
     *
     * @param field
     * @param value
     */
    public SearchHelper eq(String field, Object value) {
        if (StringUtils.isEmpty(field) || value == null) return this;
        TermQueryBuilder builder = QueryBuilders.termQuery(field, value);
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * in
     *
     * @param field
     * @param value
     */
    public SearchHelper in(String field, Object[] value) {
        if (StringUtils.isEmpty(field) || ArrayUtils.isEmpty(value)) return this;
        TermsQueryBuilder builder = QueryBuilders.termsQuery(field, value);
        this.mustQueryS.add(builder);
        return this;
    }


    /**
     * 域存在
     *
     * @param field
     */
    public SearchHelper exist(String field) {
        if (StringUtils.isEmpty(field)) return this;
        ExistsQueryBuilder builder = QueryBuilders.existsQuery(field);
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 批量id查找
     *
     * @param ids
     */
    public SearchHelper ids(String... ids) {
        if (ArrayUtils.isEmpty(ids)) return this;
        IdsQueryBuilder builder = QueryBuilders.idsQuery().addIds(ids);
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 大于 小于
     *
     * @param field
     * @param from
     * @param end
     */
    public SearchHelper gtlt(String field, Object from, Object end) {
        if (StringUtils.isEmpty(field)) return this;
        if (from == null && end == null) return this;
        RangeQueryBuilder builder = QueryBuilders.rangeQuery(field);
        if (from != null) {
            builder.gt(from);
        }
        if (from != null) {
            builder.lt(end);
        }
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 大于  小于等于
     *
     * @param field
     * @param from
     * @param end
     */
    public SearchHelper gtlte(String field, Object from, Object end) {
        if (StringUtils.isEmpty(field)) return this;
        if (from == null && end == null) return this;
        RangeQueryBuilder builder = QueryBuilders.rangeQuery(field);
        if (from != null) {
            builder.gt(from);
        }
        if (end != null) {
            builder.lte(end);
        }
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 大于等于 小于
     *
     * @param field
     * @param from
     * @param end
     */
    public SearchHelper gtelt(String field, Object from, Object end) {
        if (StringUtils.isEmpty(field)) return this;
        if (from == null && end == null) return this;
        RangeQueryBuilder builder = QueryBuilders.rangeQuery(field);
        if (from != null) {
            builder.gte(from);
        }
        if (end != null) {
            builder.lt(end);
        }
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 大于等于 小于等于
     *
     * @param field
     * @param from
     * @param end
     */
    public SearchHelper gtelte(String field, Object from, Object end) {
        if (StringUtils.isEmpty(field)) return this;
        if (from == null && end == null) return this;
        RangeQueryBuilder builder = QueryBuilders.rangeQuery(field);
        if (from != null) {
            builder.gte(from);
        }
        if (end != null) {
            builder.lte(end);
        }
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 前缀 prefix
     *
     * @param field
     * @param prefix
     */
    public SearchHelper prefix(String field, String prefix) {
        if (StringUtils.isEmpty(field) || StringUtils.isEmpty(prefix)) return this;
        PrefixQueryBuilder builder = QueryBuilders.prefixQuery(field, prefix);
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 通配符 wildcard
     *
     * @param field
     * @param wildcard
     */
    public SearchHelper wildcard(String field, String wildcard) {
        if (StringUtils.isEmpty(field) || StringUtils.isEmpty(wildcard)) return this;
        WildcardQueryBuilder builder = QueryBuilders.wildcardQuery(field, wildcard);
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 模糊 fuzzy
     *
     * @param field
     * @param fuzzy
     */
    public SearchHelper fuzzy(String field, String fuzzy) {
        if (StringUtils.isEmpty(field) || StringUtils.isEmpty(fuzzy)) return this;
        FuzzyQueryBuilder builder = QueryBuilders.fuzzyQuery(field, fuzzy);
        this.mustQueryS.add(builder);
        return this;
    }

    /**
     * 模糊 regesp
     *
     * @param field
     * @param regexp
     */
    public SearchHelper regexp(String field, String regexp) {
        if (StringUtils.isEmpty(field) || StringUtils.isEmpty(regexp)) return this;
        RegexpQueryBuilder builder = QueryBuilders.regexpQuery(field, regexp);
        this.mustQueryS.add(builder);
        return this;
    }


    /**
     * 参数设置
     */
    public static class Build {
        private String[] indexs;
        private String[] types;

        public Build indexs(String... indexs) {
            this.indexs = indexs;
            return this;
        }

        public Build types(String... types) {
            this.types = types;
            return this;
        }

        public SearchHelper build() {
            return new SearchHelper(this.indexs, this.types);
        }
    }
}
