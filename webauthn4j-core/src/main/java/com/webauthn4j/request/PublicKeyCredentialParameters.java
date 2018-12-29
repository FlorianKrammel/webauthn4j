/*
 * Copyright 2002-2018 the original author or authors.
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

package com.webauthn4j.request;

import com.webauthn4j.response.attestation.statement.COSEAlgorithmIdentifier;

public class PublicKeyCredentialParameters {

    private PublicKeyCredentialType type;
    private COSEAlgorithmIdentifier alg;

    public PublicKeyCredentialParameters(PublicKeyCredentialType type, COSEAlgorithmIdentifier alg) {
        this.type = type;
        this.alg = alg;
    }

    public PublicKeyCredentialType getType() {
        return type;
    }

    public COSEAlgorithmIdentifier getAlg() {
        return alg;
    }


}
