/*
 * Copyright 2016 bdiasse.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.petalmd.armor.filter.level;

import java.io.IOException;
import org.apache.lucene.search.Query;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.index.query.QueryParseContext;
import org.elasticsearch.index.query.QueryParser;
import org.elasticsearch.index.query.QueryParsingException;

/**
 *
 * @author bdiasse
 */
public class ArmorWrapperQueryParser implements QueryParser{
    
    public static final String NAME = "armorWrapper";
    
    @Inject
    public ArmorWrapperQueryParser(){
    }
    
    @Override 
    public String[] names() {
        return new String[]{NAME};
    }
    
    
    @Override
    public Query parse(QueryParseContext parseContext) throws IOException, QueryParsingException {
        XContentParser parser = parseContext.parser();

        XContentParser.Token token = parser.nextToken();
        if (token != XContentParser.Token.FIELD_NAME) {
            throw new QueryParsingException(parseContext, "[armorWrapper] query malformed");
        }
        String fieldName = parser.currentName();
        if (!fieldName.equals("query")) {
            throw new QueryParsingException(parseContext, "[armorWrapper] query malformed");
        }
        parser.nextToken();

//        String querySource = parser.text();
        Query query = parseContext.parseInnerQuery();
        if (parser.nextToken() != XContentParser.Token.END_OBJECT) {
            throw new QueryParsingException(parseContext, "[armorWrapper] query malformed");
        }
        return query;
    }
    
}
