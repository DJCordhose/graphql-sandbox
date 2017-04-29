/**
 * Copyright 2016 Yurii Rashkovskii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 */

// COPIED FROM ORIGINAL SOURCE TO FIX A INCOMPATIBILITY BUG
// BETWEEN ANNOTATIONS AND GRAPHQL-JAVA:
// https://github.com/graphql-java/graphql-java-annotations/issues/77
package graphql.annotations;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLObjectType;

import java.lang.reflect.*;
import java.util.*;

class MethodDataFetcher implements DataFetcher {
    private final Method method;
    private final TypeFunction typeFunction;

    public MethodDataFetcher(Method method) {
        this(method, new DefaultTypeFunction());
    }

    public MethodDataFetcher(Method method, TypeFunction typeFunction) {
        this.method = method;
        this.typeFunction = typeFunction;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) {
        try {
            Object obj;

            if (Modifier.isStatic(method.getModifiers())) {
                obj = null;
            } else if (method.getAnnotation(GraphQLInvokeDetached.class) == null) {
                obj = environment.getSource();
                if (obj == null) {
                    return null;
                }
            } else {
                obj = method.getDeclaringClass().newInstance();
            }
            return method.invoke(obj, invocationArgs(environment));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] invocationArgs(DataFetchingEnvironment environment) throws Exception {
        List result = new ArrayList();
        Iterator envArgs = environment.getArguments().values().iterator();
        for (Parameter p : method.getParameters()) {
            Class<?> paramType = p.getType();
            if (DataFetchingEnvironment.class.isAssignableFrom(paramType)) {
                result.add(environment);
                continue;
            }
            graphql.schema.GraphQLType graphQLType = typeFunction.apply(paramType, p.getAnnotatedType());
            if (graphQLType instanceof GraphQLObjectType) {
                Constructor<?> constructor = paramType.getConstructor(HashMap.class);
                result.add(constructor.newInstance(envArgs.next()));

            } else {
                result.add(envArgs.next());
            }
        }
        return result.toArray();
    }
}
