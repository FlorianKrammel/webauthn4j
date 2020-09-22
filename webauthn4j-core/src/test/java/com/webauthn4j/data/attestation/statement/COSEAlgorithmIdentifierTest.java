/*
 * Copyright 2018 the original author or authors.
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

package com.webauthn4j.data.attestation.statement;

import com.webauthn4j.converter.exception.DataConversionException;
import com.webauthn4j.converter.util.JsonConverter;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class COSEAlgorithmIdentifierTest {

    private final ObjectConverter objectConverter = new ObjectConverter();
    private final JsonConverter jsonConverter = objectConverter.getJsonConverter();

    @Test
    void create_test() {
        assertAll(
                () -> assertThat(COSEAlgorithmIdentifier.create(-257)).isEqualTo(COSEAlgorithmIdentifier.RS256),
                () -> assertThat(COSEAlgorithmIdentifier.create(-258)).isEqualTo(COSEAlgorithmIdentifier.RS384),
                () -> assertThat(COSEAlgorithmIdentifier.create(-259)).isEqualTo(COSEAlgorithmIdentifier.RS512),
                () -> assertThat(COSEAlgorithmIdentifier.create(-7)).isEqualTo(COSEAlgorithmIdentifier.ES256),
                () -> assertThat(COSEAlgorithmIdentifier.create(-35)).isEqualTo(COSEAlgorithmIdentifier.ES384),
                () -> assertThat(COSEAlgorithmIdentifier.create(-36)).isEqualTo(COSEAlgorithmIdentifier.ES512),
                () -> assertThat(COSEAlgorithmIdentifier.create(-1)).isEqualTo(COSEAlgorithmIdentifier.create(-1))
        );
    }

    @Test
    void create_with_SignatureAlgorithm_test() {
        assertAll(
                () -> assertThat(COSEAlgorithmIdentifier.create(SignatureAlgorithm.RS1)).isEqualTo(COSEAlgorithmIdentifier.RS1),
                () -> assertThat(COSEAlgorithmIdentifier.create(SignatureAlgorithm.RS256)).isEqualTo(COSEAlgorithmIdentifier.RS256),
                () -> assertThat(COSEAlgorithmIdentifier.create(SignatureAlgorithm.RS384)).isEqualTo(COSEAlgorithmIdentifier.RS384),
                () -> assertThat(COSEAlgorithmIdentifier.create(SignatureAlgorithm.RS512)).isEqualTo(COSEAlgorithmIdentifier.RS512),
                () -> assertThat(COSEAlgorithmIdentifier.create(SignatureAlgorithm.ES256)).isEqualTo(COSEAlgorithmIdentifier.ES256),
                () -> assertThat(COSEAlgorithmIdentifier.create(SignatureAlgorithm.ES384)).isEqualTo(COSEAlgorithmIdentifier.ES384),
                () -> assertThat(COSEAlgorithmIdentifier.create(SignatureAlgorithm.ES512)).isEqualTo(COSEAlgorithmIdentifier.ES512)
        );
    }

    @Test
    void getValue_test() {
        assertThat(COSEAlgorithmIdentifier.RS256.getValue()).isEqualTo(-257);
    }


    @Test
    void invalid_data_toSignatureAlgorithm_test() {
        COSEAlgorithmIdentifier coseAlgorithmIdentifier = COSEAlgorithmIdentifier.create(-16);
        assertThatThrownBy(coseAlgorithmIdentifier::toSignatureAlgorithm).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deserialize_test() {
        TestDTO dto = jsonConverter.readValue("{\"cose_alg_id\":-257}", TestDTO.class);
        assertThat(dto.cose_alg_id).isEqualTo(COSEAlgorithmIdentifier.RS256);
    }

    @Test
    void deserialize_test_with_non_predefined_value() {
        assertDoesNotThrow(
                () -> jsonConverter.readValue("{\"cose_alg_id\":0}", TestDTO.class)
        );
    }

    @Test
    void deserialize_test_with_invalid_value() {
        assertThatThrownBy(
                () -> jsonConverter.readValue("{\"cose_alg_id\": \"\"}", TestDTO.class)
        ).isInstanceOf(DataConversionException.class);
    }

    @Test
    void deserialize_test_with_null() {
        TestDTO data = jsonConverter.readValue("{\"cose_alg_id\":null}", TestDTO.class);
        assertThat(data.cose_alg_id).isNull();
    }

    static class TestDTO {
        @SuppressWarnings("WeakerAccess")
        public COSEAlgorithmIdentifier cose_alg_id;
    }
}
