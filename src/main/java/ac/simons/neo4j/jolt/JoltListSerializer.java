/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ac.simons.neo4j.jolt;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

final class JoltListSerializer extends StdSerializer<List<?>> {

	JoltListSerializer() {
		super(TypeFactory.defaultInstance().constructCollectionLikeType(List.class, Object.class));
	}

	@Override
	public void serialize(List<?> value, JsonGenerator generator, SerializerProvider provider) throws IOException {

		generator.writeStartObject();
		generator.writeFieldName(Sigil.LIST.getValue());
		generator.writeStartArray(value);

		for (var entry : value) {
			var elementSerializer = provider.findValueSerializer(entry.getClass());
			elementSerializer.serialize(entry, generator, provider);
		}

		generator.writeEndArray();
		generator.writeEndObject();

	}

}