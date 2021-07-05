/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.search.aggregations.bucket.multiterms;

import org.elasticsearch.common.xcontent.ObjectParser;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.search.aggregations.ParsedMultiBucketAggregation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ParsedMultiTerms extends ParsedMultiBucketAggregation<ParsedMultiTerms.ParsedBucket> {
    private static final ObjectParser<ParsedMultiTerms, Void> PARSER =
        new ObjectParser<>(ParsedMultiTerms.class.getSimpleName(), true, ParsedMultiTerms::new);

    static {
        declareMultiBucketAggregationFields(PARSER, parser -> ParsedBucket.fromXContent(parser),
            parser -> null
        );
    }

    @Override
    public boolean isFragment() {
        return super.isFragment();
    }

    @Override
    public String getType() {
        return MultiTermsAggregationBuilder.NAME;
    }

    public static ParsedMultiTerms fromXContent(XContentParser parser, String name) throws IOException {
        ParsedMultiTerms aggregation = PARSER.parse(parser, null);
        aggregation.setName(name);
        return aggregation;
    }

    @Override
    public List<? extends Bucket> getBuckets() {
        return buckets;
    }

    public static class ParsedBucket extends ParsedMultiBucketAggregation.ParsedBucket {
        private Map<String, String> keys;

        @Override
        public Map<String, String> getKey() {
            return keys;
        }

        static ParsedMultiTerms.ParsedBucket fromXContent(XContentParser parser) throws IOException {
            return parseXContent(parser, false, ParsedBucket::new, (p, bucket) -> bucket.keys = p.mapStrings());
        }
    }

}
