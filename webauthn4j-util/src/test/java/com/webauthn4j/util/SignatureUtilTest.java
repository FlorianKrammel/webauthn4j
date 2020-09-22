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

package com.webauthn4j.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SignatureUtilTest {

    @SuppressWarnings("deprecation")
    @Test
    void getRS256_test(){
        assertThat(SignatureUtil.getRS256().getAlgorithm()).isEqualTo("SHA256withRSA");
    }

    @SuppressWarnings("deprecation")
    @Test
    void getES256_test(){
        assertThat(SignatureUtil.getES256().getAlgorithm()).isEqualTo("SHA256withECDSA");
    }

    @Test
    void createSignature_test() {
        SignatureUtil.createSignature("SHA256withRSA");
    }

    @Test
    void createSignature_test_with_null() {
        Throwable t = assertThrows(IllegalArgumentException.class,
                () -> SignatureUtil.createSignature(null)
        );
        assertThat(t).hasMessage("algorithm is required; it must not be null");
    }

    @Test
    void createSignature_test_with_illegal_argument() {
        Throwable t = assertThrows(IllegalArgumentException.class,
                () -> SignatureUtil.createSignature("dummyAlg")
        );
        assertThat(t).hasMessageContaining("dummyAlg Signature not available");
    }
}
