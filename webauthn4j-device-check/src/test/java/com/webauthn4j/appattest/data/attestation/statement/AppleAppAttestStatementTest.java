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

package com.webauthn4j.appattest.data.attestation.statement;

import com.webauthn4j.data.attestation.statement.AttestationCertificatePath;
import com.webauthn4j.test.TestDataUtil;
import com.webauthn4j.validator.RegistrationObject;
import com.webauthn4j.validator.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppleAppAttestStatementTest {

    @Test
    void validate_test() {
        new AppleAppAttestStatement(new AttestationCertificatePath(), new byte[32]).validate();
        assertAll(
                () -> {
                    AppleAppAttestStatement appleAppAttestStatement = new AppleAppAttestStatement(null, new byte[32]);
                    assertThrows(ConstraintViolationException.class, appleAppAttestStatement::validate);
                },
                () -> {
                    AppleAppAttestStatement appleAppAttestStatement = new AppleAppAttestStatement(new AttestationCertificatePath(), null);
                    assertThrows(ConstraintViolationException.class, appleAppAttestStatement::validate);
                }
        );
    }

    @Test
    void equals_hashCode_test() {
        RegistrationObject registrationObjectA = TestDataUtil.createRegistrationObjectWithAppleAppAttestAttestation();
        AppleAppAttestStatement instanceA = (AppleAppAttestStatement) registrationObjectA.getAttestationObject().getAttestationStatement();
        RegistrationObject registrationObjectB = TestDataUtil.createRegistrationObjectWithAppleAppAttestAttestation();
        AppleAppAttestStatement instanceB = (AppleAppAttestStatement) registrationObjectB.getAttestationObject().getAttestationStatement();

        assertAll(
                () -> assertThat(instanceA).isEqualTo(instanceB),
                () -> assertThat(instanceA).hasSameHashCodeAs(instanceB)
        );
    }
}
