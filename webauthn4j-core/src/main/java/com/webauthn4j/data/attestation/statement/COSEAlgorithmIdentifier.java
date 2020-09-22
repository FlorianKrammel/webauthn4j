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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.webauthn4j.data.SignatureAlgorithm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class COSEAlgorithmIdentifier implements Serializable {

    public static final COSEAlgorithmIdentifier RS1;
    public static final COSEAlgorithmIdentifier RS256;
    public static final COSEAlgorithmIdentifier RS384;
    public static final COSEAlgorithmIdentifier RS512;
    public static final COSEAlgorithmIdentifier ES256;
    public static final COSEAlgorithmIdentifier ES384;
    public static final COSEAlgorithmIdentifier ES512;

    private static Map<COSEAlgorithmIdentifier, COSEKeyType> keyTypeMap = new HashMap<>();
    private static Map<COSEAlgorithmIdentifier, SignatureAlgorithm> algorithmMap = new HashMap<>();
    private static Map<SignatureAlgorithm, COSEAlgorithmIdentifier> reverseAlgorithmMap = new HashMap<>();

    static {
        RS1 = new COSEAlgorithmIdentifier(-65535);
        RS256 = new COSEAlgorithmIdentifier(-257);
        RS384 = new COSEAlgorithmIdentifier(-258);
        RS512 = new COSEAlgorithmIdentifier(-259);
        ES256 = new COSEAlgorithmIdentifier(-7);
        ES384 = new COSEAlgorithmIdentifier(-35);
        ES512 = new COSEAlgorithmIdentifier(-36);

        keyTypeMap.put(COSEAlgorithmIdentifier.ES256, COSEKeyType.EC2);
        keyTypeMap.put(COSEAlgorithmIdentifier.ES384, COSEKeyType.EC2);
        keyTypeMap.put(COSEAlgorithmIdentifier.ES512, COSEKeyType.EC2);
        keyTypeMap.put(COSEAlgorithmIdentifier.RS1,   COSEKeyType.RSA);
        keyTypeMap.put(COSEAlgorithmIdentifier.RS256, COSEKeyType.RSA);
        keyTypeMap.put(COSEAlgorithmIdentifier.RS384, COSEKeyType.RSA);
        keyTypeMap.put(COSEAlgorithmIdentifier.RS512, COSEKeyType.RSA);

        algorithmMap.put(COSEAlgorithmIdentifier.ES256, SignatureAlgorithm.ES256);
        algorithmMap.put(COSEAlgorithmIdentifier.ES384, SignatureAlgorithm.ES384);
        algorithmMap.put(COSEAlgorithmIdentifier.ES512, SignatureAlgorithm.ES512);
        algorithmMap.put(COSEAlgorithmIdentifier.RS1,   SignatureAlgorithm.RS1);
        algorithmMap.put(COSEAlgorithmIdentifier.RS256, SignatureAlgorithm.RS256);
        algorithmMap.put(COSEAlgorithmIdentifier.RS384, SignatureAlgorithm.RS384);
        algorithmMap.put(COSEAlgorithmIdentifier.RS512, SignatureAlgorithm.RS512);

        for (Map.Entry<COSEAlgorithmIdentifier, SignatureAlgorithm> entry: algorithmMap.entrySet()) {
            reverseAlgorithmMap.put(entry.getValue(), entry.getKey());
        }
    }

    private final long value;

    COSEAlgorithmIdentifier(long value) {
        this.value = value;
    }

    // COSEAlgorithmIdentifier doesn't accept jcaName and messageDigestJcaName from caller for the time being
    public static COSEAlgorithmIdentifier create(long value) {
        return new COSEAlgorithmIdentifier(value);
    }

    public static COSEAlgorithmIdentifier create(SignatureAlgorithm signatureAlgorithm) {
        COSEAlgorithmIdentifier coseAlgorithmIdentifier = reverseAlgorithmMap.get(signatureAlgorithm);
        if(coseAlgorithmIdentifier == null){
            throw new IllegalArgumentException(String.format("SignatureAlgorithm %s is not supported.", signatureAlgorithm.getJcaName()));
        }
        return coseAlgorithmIdentifier;
    }

    @JsonCreator
    private static COSEAlgorithmIdentifier deserialize(long value) {
        return create(value);
    }

    @JsonValue
    public long getValue() {
        return value;
    }

    @JsonIgnore
    public COSEKeyType getKeyType(){
        COSEKeyType coseKeyType = keyTypeMap.get(this);
        if(coseKeyType == null){
            throw new IllegalArgumentException(String.format("COSEAlgorithmIdentifier %d is unknown.", this.getValue()));
        }
        return coseKeyType;
    }

    public SignatureAlgorithm toSignatureAlgorithm(){
        SignatureAlgorithm signatureAlgorithm = algorithmMap.get(this);
        if(signatureAlgorithm == null){
            throw new IllegalArgumentException(String.format("COSEAlgorithmIdentifier %d is unknown.", this.getValue()));
        }
        return signatureAlgorithm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        COSEAlgorithmIdentifier that = (COSEAlgorithmIdentifier) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
